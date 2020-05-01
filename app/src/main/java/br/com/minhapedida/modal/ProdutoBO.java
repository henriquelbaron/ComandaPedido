package br.com.minhapedida.modal;

import br.com.minhapedida.exeptions.InputInvalidExeption;
import br.com.minhapedida.exeptions.ValorOutRangeExeption;
import br.com.minhapedida.modal.domain.Produto;

public class ProdutoBO {

    private Produto produto;

    public ProdutoBO(Produto p) {
        this.produto = p;
    }

    public ProdutoBO() {
        this.produto = new Produto();
    }

    public static Double formatNumber(String valor) {
        valor = valor.replaceAll("[^0-9,]", "");
        valor = valor.replaceAll(",", "\\.");
        return Double.parseDouble(valor.trim());
    }

    public void setNome(String nome) throws InputInvalidExeption {
        if (isValidString(nome)) {
            produto.setNome(nome);
        } else {
            throw new InputInvalidExeption();
        }
    }

    public void setValor(String valor) throws InputInvalidExeption, ValorOutRangeExeption {
        if (isValidString(valor)) {
            setValor(formatNumber(valor));
        }
    }

    public void setValor(Double valor) throws ValorOutRangeExeption {
        if (9999.9 > 0.0 ? valor >= 0.0 && valor <= 9999.9 : valor >= 9999.9 && valor <= 0.0) {
            produto.setValor(valor);
        } else {
            throw new ValorOutRangeExeption();
        }
    }

    private boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty() && str.length() < 50;
    }

    public Produto getProduto() {
        return produto;
    }
}
