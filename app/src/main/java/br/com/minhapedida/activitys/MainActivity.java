package br.com.minhapedida.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.minhapedida.R;
import br.com.minhapedida.control.MainControl;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView total;
    private MainControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initParams();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        control.confListView();
    }

    public void initParams() {
        listView = findViewById(R.id.listProdutos);
        total = findViewById(R.id.total);
        control = new MainControl(this);
    }

    public void limparListaAction(View view) {
        control.clearAll();
    }

    public void addAction(View view) {
        control.callAdicionarProdutoActivity();
    }


    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public TextView getTotal() {
        return total;
    }

    public void setTotal(TextView total) {
        this.total = total;
    }
}
