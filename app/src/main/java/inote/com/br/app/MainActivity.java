package inote.com.br.app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context;
    ListView lst_title_note,listaData;

    List<String> listaDeTitulo = new ArrayList<>();
    List<Integer> listaDeIds = new ArrayList<>();
    List<String> listaDate = new ArrayList<>();
    ArrayAdapter <String> adapter ;
    SearchView.OnQueryTextListener listennerBuscar = new SearchView.OnQueryTextListener(){

        // executado quando termina a busca
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
        // executada a cada letra digitada
        @Override
        public boolean onQueryTextChange(String newText) {
            Log.d("onQueryText", newText);
            // fiiltragem adapter
            adapter.getFilter().filter(newText);

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        lst_title_note = (ListView) findViewById(R.id.lst_title_note);
        listaData = (ListView) findViewById(R.id.listaData);

        configuracaoBotaoFlutuante();
        preencherAdapter();
        buscarTituloNoBanco();
        configurarCliqueDalista();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // carrega o arquivo de menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_busca, menu);

        //pega o componente
        SearchView searchView = (SearchView) menu.findItem(R.id.busca)
                .getActionView();
        //Define um texto de ajuda:
        searchView.setQueryHint("Busca..");
        //exemplos de utilização
        searchView.setOnQueryTextListener(listennerBuscar);
        return true;
    }
    private void configurarCliqueDalista() {
        lst_title_note.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer idNotas = listaDeIds.get(position);
                Intent intent = new Intent(context,AnotacoesActivity.class);
                intent.putExtra("idNotas", idNotas);
                startActivity(intent);
            }
        });
    }


    private void buscarTituloNoBanco() {
        SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();
        String comandoSQL = "SELECT * FROM tblNotas";
        Cursor cursor = db.rawQuery(comandoSQL,null);

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();

            for(int i = 0; i < cursor.getCount(); i++) {
                listaDeIds.add(cursor.getInt(0));
                listaDeTitulo.add(cursor.getString(1));
                listaDate.add(cursor.getString(3));
                cursor.moveToNext();


            }
        }
        cursor.close();


    }

    private void preencherAdapter() {
        adapter =
                new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listaDeTitulo);
        lst_title_note.setAdapter(adapter);
        ArrayAdapter<String> AdapterData =
                new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listaDate);
        listaData.setAdapter(AdapterData);


    }

    private void configuracaoBotaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,addNotaActivity.class);
                startActivity(intent);
            }
        });
    }



}
