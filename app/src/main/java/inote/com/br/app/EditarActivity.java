package inote.com.br.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {
    Context context;
    Integer idNotas;
    EditText edit_title,edit_anotacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context =this;

        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_anotacao = (EditText) findViewById(R.id.edit_anotacao);


        configuracaoBotaoFlutuante();

        Intent intent = getIntent();
        if(intent != null){
            idNotas = intent.getIntExtra("idNotas",0);
            buscarAnotacaoNobanco(idNotas);

        }


    }

    private void buscarAnotacaoNobanco(Integer idNotas) {
        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tblNotas WHERE _id=?",new String[]{idNotas.toString()}
        );

        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();
            String tituloNotas = cursor.getString(1);
            String anotacaoNotas = cursor.getString(2);
            edit_title.setText(tituloNotas);
            edit_anotacao.setText(anotacaoNotas);
        }
        cursor.close();
    }

    private void salvarEdicao() {
        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();

        ContentValues valores  = new ContentValues();
        valores.put("titulo",edit_title.getText().toString());
        valores.put("anotacao",edit_anotacao.getText().toString());

        db.update("tblNotas",valores,"_id=?",new String[] {idNotas.toString()});

        Toast.makeText(this,"editado com sucesssssss!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,AnotacoesActivity.class);

        //enviar id denovo para a tela de detalhes atraves do intent.putExtra
        intent.putExtra("idNotas",idNotas);
        startActivity(intent);


    }
    private void configuracaoBotaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_salvar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarEdicao();
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
