package br.com.minhapedida;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import br.com.minhapedida.control.AdicionarProdutoControl;

public class AdicionarProdutoActivity extends AppCompatActivity {

    private Spinner produtosSpinner;
    private NumberPicker quantidade;
    private AdicionarProdutoControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produto);
        initParams();
    }

    public void initParams() {
        produtosSpinner = findViewById(R.id.spiner);
        quantidade = findViewById(R.id.quantidade);
        control = new AdicionarProdutoControl(this);
    }

    public Spinner getProdutosSpinner() {
        return produtosSpinner;
    }

    public void setProdutosSpinner(Spinner produtosSpinner) {
        this.produtosSpinner = produtosSpinner;
    }

    public NumberPicker getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(NumberPicker quantidade) {
        this.quantidade = quantidade;
    }

    public void enviar(View view) {
        control.addProduto();
    }
}
