package br.com.minhapedida.control;

import android.content.Intent;
import android.widget.ArrayAdapter;

import java.sql.SQLException;

import br.com.minhapedida.activitys.AdicionarProdutoActivity;
import br.com.minhapedida.activitys.CadastroProdutoActivity;
import br.com.minhapedida.dao.PedidoDao;
import br.com.minhapedida.dao.ProdutoDao;
import br.com.minhapedida.modal.domain.Pedido;
import br.com.minhapedida.modal.domain.Produto;

public class AdicionarProdutoControl {

    private AdicionarProdutoActivity activity;
    private ArrayAdapter<Produto> adapterProduto;

    private ProdutoDao produtoDao;
    private PedidoDao pedidoDao;

    public AdicionarProdutoControl(AdicionarProdutoActivity activity) {
        this.activity = activity;
        produtoDao = new ProdutoDao(this.activity);
        pedidoDao = new PedidoDao(this.activity);
        configSpinner();
        configNumberPicker();
    }

    private void configNumberPicker() {
        activity.getQuantidade().setMinValue(1);
        activity.getQuantidade().setMaxValue(99);
        activity.getQuantidade().setWrapSelectorWheel(true);
    }

    public void configSpinner() {
        try {
            produtoDao.getDao().createIfNotExists(new Produto(1, "Refrigerante", 3.00));
            produtoDao.getDao().createIfNotExists(new Produto(2, "Cerveja", 5.00));
            produtoDao.getDao().createIfNotExists(new Produto(3, "Batata Frita", 10.00));
            produtoDao.getDao().createIfNotExists(new Produto(4, "Água", 2.50));
            produtoDao.getDao().createIfNotExists(new Produto(5, "Pastel", 3.50));
            produtoDao.getDao().createIfNotExists(new Produto(6, "Petiscos", 6.00));
            adapterProduto = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    produtoDao.queryAllActive()
            );
            activity.getProdutosSpinner().setAdapter(adapterProduto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduto() {
        int quantidade = activity.getQuantidade().getValue();
        Produto p = (Produto) activity.getProdutosSpinner().getSelectedItem();
        try {
            pedidoDao.getDao().create(new Pedido(quantidade, p));
            activity.finish();
        } catch (SQLException e) {

        }
    }

    public void callCadastroProdutoActivity() {
        Intent intent = new Intent(this.activity, CadastroProdutoActivity.class);
        this.activity.startActivity(intent);
    }
}
