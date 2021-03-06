package br.com.minhapedida.modal.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "produto")
public class Produto implements Serializable {
    public static final String ATIVO_FIELD_NAME = "ativo";
    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, unique = true)
    private String nome;

    @DatabaseField(canBeNull = false)
    private Double valor;

    @DatabaseField(canBeNull = false, columnName = ATIVO_FIELD_NAME)
    private Boolean ativo;

    public Produto() {
    }

    public Produto(Integer id, String nome, Double valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.ativo = true;
    }

    public Produto(Integer id, String nome, Double valor, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.ativo = ativo;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return nome + " - " + String.format("%.2f", valor);
    }
}
