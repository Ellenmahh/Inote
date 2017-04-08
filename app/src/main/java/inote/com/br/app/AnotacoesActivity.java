package inote.com.br.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AnotacoesActivity extends AppCompatActivity {
    Context context ;
    TextView txt_mostrar_notas,txt_titulo;
    Integer idNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        txt_mostrar_notas = (TextView) findViewById(R.id.txt_mostrar_notas);
        txt_titulo = (TextView) findViewById(R.id.txt_titulo);

        configuracaoBotaoFlutuante();

        Intent intent = getIntent();
        if(intent != null){
            idNotas = intent.getIntExtra("idNotas",0);
            buscarAnotacaoNobanco(idNotas);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notas,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_excluir:
                excluirNota();
            break;
            case R.id.menu_editar:
                //clicou no editar
                Intent intent = new Intent(this,EditarActivity.class);
                // precisar pegar o _id para editar oq o user clicou
                intent.putExtra("idNotas", idNotas);
                startActivity(intent );
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void excluirNota() {
        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();
        db.delete("tblNotas","_id=?", new String[]{idNotas.toString()});
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    private void buscarAnotacaoNobanco(Integer idNotas) {
        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tblNotas WHERE _id=?",new String[]{idNotas.toString()}
        );

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            String titulo = cursor.getString(1);
            String anotacao = cursor.getString(2);

            txt_titulo.setText(titulo);
            txt_mostrar_notas.setText(anotacao);

        }
        cursor.close();
    }
    private void configuracaoBotaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.delete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               confirmarExcluir();
            }
        });
    }

    private void confirmarExcluir() {
        new AlertDialog.Builder(this).setTitle( "EXCLUIR").setIcon(android.R.drawable.ic_delete)
                .setMessage("Tem certeza que deseja excluir?").setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluirNota();
            }
        }).setNegativeButton("Não",null).show();
    }

}
