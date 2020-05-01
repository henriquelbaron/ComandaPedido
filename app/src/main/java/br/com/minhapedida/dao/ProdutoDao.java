package br.com.minhapedida.dao;

import android.content.Context;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import br.com.minhapedida.dao.helpers.DaoHelper;
import br.com.minhapedida.modal.domain.Produto;

public class ProdutoDao extends DaoHelper<Produto> {
    public ProdutoDao(Context c) {
        super(c, Produto.class);
    }

    public List<Produto> queryForAll() throws SQLException {
        return getDao().queryForAll();
    }

    public List<Produto> queryAllActive() throws SQLException {
        QueryBuilder<Produto, Integer> queryBuilder =
                getDao().queryBuilder();
        queryBuilder.where().eq(Produto.ATIVO_FIELD_NAME, true);
        PreparedQuery<Produto> preparedQuery = queryBuilder.prepare();
        return getDao().query(preparedQuery);
    }
}
