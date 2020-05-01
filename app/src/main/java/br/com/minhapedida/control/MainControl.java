package br.com.minhapedida.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.List;

import br.com.minhapedida.activitys.AdicionarProdutoActivity;
import br.com.minhapedida.activitys.MainActivity;
import br.com.minhapedida.dao.PedidoDao;
import br.com.minhapedida.dao.ProdutoDao;
import br.com.minhapedida.modal.domain.Pedido;

public class MainControl {

    private MainActivity activity;
    private ArrayAdapter<Pedido> pedidoArrayAdapter;
    private List<Pedido> pedidos;
    private Pedido pedido;
    private Double total;

    private PedidoDao pedidoDao;
    private ProdutoDao produtoDao;

    public MainControl(MainActivity activity) {
        this.activity = activity;
        pedidoDao = new PedidoDao(this.activity);
        produtoDao = new ProdutoDao(this.activity);
        confListView();
    }

    public void confListView() {
        try {
            pedidos = pedidoDao.queryForAll();
            for (Pedido p : pedidos) {
                produtoDao.getDao().refresh(p.getProduto());
            }
            refeshTotal();
            pedidoArrayAdapter = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    pedidos
            );
            activity.getListView().setAdapter(pedidoArrayAdapter);
            addCliqueCurtoListView();
            addCliqueLongoListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCliqueLongoListView() {
        activity.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pedido = pedidoArrayAdapter.getItem(position);
                confirmarExclusaoAction(pedido);
                return true;
            }
        });
    }

    private void confirmarExclusaoAction(final Pedido e) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluir Pedido");
        alerta.setMessage("Deseja realmente excluir o pedido " + e.getProduto().getNome() + "?");
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pedido = null;
            }
        });
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletaPedido(e);
            }
        });
        alerta.show();
    }

    private void deletaPedido(final Pedido p) {
        try {
            pedidoDao.getDao().delete(p);
            pedidos.remove(p);
            refeshTotal();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        pedidoArrayAdapter.remove(p);
        pedido = null;
    }

    private void addCliqueCurtoListView() {
        activity.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pedido = pedidoArrayAdapter.getItem(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setMessage("Deseja alterar a quantidade de " + pedido + "?");
                alerta.setIcon(android.R.drawable.ic_menu_edit);
                alerta.setNegativeButton("-1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (pedido.getQuantidade() > 0) {
                            pedido.setQuantidade(pedido.getQuantidade() - 1);
                        }
                        edit(pedido);
                    }
                });
                alerta.setPositiveButton("+1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pedido.setQuantidade(pedido.getQuantidade() + 1);
                        edit(pedido);
                    }
                });
                alerta.setNeutralButton("Fechar", null);
                alerta.show();
            }
        });
    }

    private void edit(Pedido p) {
        try {
            pedidoDao.getDao().update(p);
            refeshTotal();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        pedidoArrayAdapter.notifyDataSetChanged();
        pedido = null;
    }


    public void callAdicionarProdutoActivity() {
        Intent intent = new Intent(this.activity, AdicionarProdutoActivity.class);
        this.activity.startActivity(intent);
    }

    private void refeshTotal() {
        total = 0.0;
        for (Pedido p : pedidos) {
            total += p.getSubTotal();
        }
        activity.getTotal().setText("Total: R$ " + String.format("%.2f", total));
    }

    public void clearAll() {
        for (Pedido p : pedidos) {
            deletaPedido(p);
        }
    }

}
