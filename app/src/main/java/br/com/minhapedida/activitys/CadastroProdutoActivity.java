package br.com.minhapedida.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

import br.com.minhapedida.R;
import br.com.minhapedida.components.MoneyTextWatcher;
import br.com.minhapedida.control.CadastroProdutosControl;
import br.com.minhapedida.exeptions.InputInvalidExeption;
import br.com.minhapedida.exeptions.ValorOutRangeExeption;

public class CadastroProdutoActivity extends AppCompatActivity {

    private EditText nome, valor;
    private Switch ativo;
    private ListView lvProdutos;
    private CadastroProdutosControl control;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        initParams();
    }

    public void initParams() {
        valor = findViewById(R.id.valor);
        nome = findViewById(R.id.nome);
        ativo = findViewById(R.id.ativo);
        lvProdutos = findViewById(R.id.listProdutos);
        layout = findViewById(R.id.layout_tela_cadastro_produto);
        control = new CadastroProdutosControl(this);
    }

    public void salvarAction(View view) {
        try {
            control.salvar();
            Toast.makeText(this, R.string.salvo_sucesso, Toast.LENGTH_SHORT).show();
        } catch (ValorOutRangeExeption valorOutRangeExeption) {
            Toast.makeText(this, R.string.salvar_valor_invalido, Toast.LENGTH_SHORT).show();
            valorOutRangeExeption.printStackTrace();
        } catch (SQLException e) {
            Toast.makeText(this, R.string.salvar_erro_persistir, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (InputInvalidExeption inputInvalidExeption) {
            Toast.makeText(this, R.string.salvar_nome_invalido, Toast.LENGTH_SHORT).show();
            inputInvalidExeption.printStackTrace();
        }
    }

    public EditText getNome() {
        return nome;
    }

    public void setNome(EditText nome) {
        this.nome = nome;
    }

    public EditText getValor() {
        return valor;
    }

    public void setValor(EditText valor) {
        this.valor = valor;
    }

    public Switch getAtivo() {
        return ativo;
    }

    public void setAtivo(Switch ativo) {
        this.ativo = ativo;
    }

    public ListView getLvProdutos() {
        return lvProdutos;
    }

    public void setLvProdutos(ListView lvProdutos) {
        this.lvProdutos = lvProdutos;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }

    public void voltarAction(View view) {
        this.finish();
    }

    public void hidenKeyBoard(View view) {
        control.hideSoftKeyboard();
    }
}
