package br.com.minhapedida.dao;

import android.content.Context;

import br.com.minhapedida.dao.helpers.DaoHelper;
import br.com.minhapedida.modal.Produto;

public class ProdutoDao extends DaoHelper<Produto> {
    public ProdutoDao(Context c) {
        super(c, Produto.class);
    }
}
