package br.com.minhapedida.exeptions;

public class InputInvalidExeption extends Exception {
    public InputInvalidExeption() {

    }

    public InputInvalidExeption(String message) {
        super(message);
    }
}
