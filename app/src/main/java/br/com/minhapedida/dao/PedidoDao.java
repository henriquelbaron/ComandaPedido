package br.com.minhapedida.dao;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.minhapedida.dao.helpers.DaoHelper;
import br.com.minhapedida.modal.domain.Pedido;

public class PedidoDao extends DaoHelper<Pedido> {
    public PedidoDao(Context c) {
        super(c, Pedido.class);
    }

    public List<Pedido> queryForAll() throws  SQLException{
        try{
            return getDao().queryForAll();
        }catch (NullPointerException e){
            return new ArrayList<Pedido>();
        }
    }
}
