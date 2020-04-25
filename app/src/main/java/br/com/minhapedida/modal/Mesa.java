package br.com.minhapedida.modal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

@DatabaseTable(tableName = "mesa")
public class Mesa implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String nome;

    @DatabaseField
    private Double total;

    @ForeignCollectionField(eager = true)
    private Collection<Pedido> pedidos;
    
    public String getTotal() {
        for (Pedido pedido : pedidos) {
            total += pedido.getSubTotal();
        }
        return "R$ " + String.format("%.2f", total);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Collection<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
