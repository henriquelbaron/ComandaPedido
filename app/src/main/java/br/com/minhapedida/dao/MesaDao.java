package br.com.minhapedida.dao;

import android.content.Context;

import br.com.minhapedida.dao.helpers.DaoHelper;
import br.com.minhapedida.modal.Mesa;

public class MesaDao extends DaoHelper<Mesa> {
    public MesaDao(Context c) {
        super(c, Mesa.class);
    }
}
