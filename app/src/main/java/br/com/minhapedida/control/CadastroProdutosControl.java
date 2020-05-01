package br.com.minhapedida.control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import br.com.minhapedida.activitys.CadastroProdutoActivity;
import br.com.minhapedida.components.MoneyTextWatcher;
import br.com.minhapedida.dao.ProdutoDao;
import br.com.minhapedida.exeptions.InputInvalidExeption;
import br.com.minhapedida.exeptions.ValorOutRangeExeption;
import br.com.minhapedida.modal.ProdutoBO;
import br.com.minhapedida.modal.domain.Produto;

public class CadastroProdutosControl {
    private CadastroProdutoActivity activity;
    private ProdutoDao produtoDao;
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private List<Produto> produtos;
    private Produto produto;

    public CadastroProdutosControl(CadastroProdutoActivity activity) {
        this.activity = activity;
        produtoDao = new ProdutoDao(this.activity);
        confListView();
        activity.getValor().addTextChangedListener(new MoneyTextWatcher(activity.getValor()));
    }

    public void confListView() {
        try {
            produtos = produtoDao.queryForAll();
            produtoArrayAdapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_checked, produtos) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    CheckedTextView ctv = view.findViewById(android.R.id.text1);
                    ctv.setFocusable(false);
                    Produto p = produtos.get(position);
                    ctv.setText(p.toString());
                    activity.getLvProdutos().setItemChecked(position, p.isAtivo());
                    return view;
                }
            };
            activity.getLvProdutos().setAdapter(produtoArrayAdapter);
            activity.getLvProdutos().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            addCliqueCurtoListView();
            addCliqueLongoListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCheckedItems() {

    }

    private void addCliqueLongoListView() {
        activity.getLvProdutos().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produto = produtoArrayAdapter.getItem(position);
                confirmarExclusaoAction(produto);
                return true;
            }
        });
    }

    private void confirmarExclusaoAction(final Produto e) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Desativar Produto");
        alerta.setMessage("Deseja realmente desativar o produto " + e.getNome() + "?");
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                produto = null;
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

    private void deletaPedido(final Produto p) {
        p.setAtivo(false);
        try {
            edit(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        produtoArrayAdapter.remove(p);
        produto = null;
    }

    private void addCliqueCurtoListView() {
        activity.getLvProdutos().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produto = produtoArrayAdapter.getItem(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setMessage("Deseja editar " + produto.getNome() + "?");
                alerta.setIcon(android.R.drawable.ic_menu_edit);
                alerta.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popularFormAction(produto);
                    }
                });
                alerta.setPositiveButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produto = null;
                    }
                });
                alerta.show();
            }
        });
    }

    private void edit(Produto newProduto) throws SQLException {
        produto.setAtivo(newProduto.isAtivo());
        produto.setValor(newProduto.getValor());
        produto.setNome(newProduto.getNome());
        produtoArrayAdapter.notifyDataSetChanged();
        produtoDao.getDao().update(produto);
    }

    public void limparForm() {
        activity.getValor().setText("");
        activity.getNome().setText("");
    }

    public void popularFormAction(Produto e) {
        activity.getNome().setText(e.getNome());
        activity.getValor().setText(String.format("%.2f", e.getValor()));
        activity.getAtivo().setChecked(e.isAtivo());
    }

    public Produto getForm() throws InputInvalidExeption, ValorOutRangeExeption {
        ProdutoBO e = new ProdutoBO();
        e.setNome(activity.getNome().getText().toString());
        e.setValor(activity.getValor().getText().toString());
        e.getProduto().setAtivo(activity.getAtivo().isChecked());
        return e.getProduto();
    }

    public void cadastrar() throws InputInvalidExeption, ValorOutRangeExeption, SQLException {
        Produto estado = getForm();
        produtoDao.getDao().createIfNotExists(estado);
        produtoArrayAdapter.add(estado);
    }

    public void salvar() throws ValorOutRangeExeption, SQLException, InputInvalidExeption {
        if (produto == null || produto.getId() == null) {
            cadastrar();
        } else {
            edit(getForm());
        }
        hideSoftKeyboard();
        produto = null;
        limparForm();
    }

    public void hideSoftKeyboard() {
        InputMethodManager mImMan = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mImMan.hideSoftInputFromWindow(activity.getValor().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        mImMan.hideSoftInputFromWindow(activity.getNome().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
