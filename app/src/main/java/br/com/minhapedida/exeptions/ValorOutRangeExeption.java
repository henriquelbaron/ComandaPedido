package br.com.minhapedida.exeptions;

public class ValorOutRangeExeption extends Exception {
    public ValorOutRangeExeption(String message) {
        super(message);
    }

    public ValorOutRangeExeption() {
        super();
    }
}
