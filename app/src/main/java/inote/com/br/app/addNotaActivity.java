package inote.com.br.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class addNotaActivity extends AppCompatActivity {
    Context context;
    EditText edit_title,edit_anotacao;
    TextView txt_data_hora;
    Button btn_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_anotacao = (EditText) findViewById(R.id.edit_anotacao);

        configuracaoBotaoFlutuante();

        //configuracaoBotaoSalvar();


    }

   // private void configuracaoBotaoSalvar() {
     //   btn_salvar = (Button) findViewById(R.id.btn_salvar);
       // btn_salvar.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //   inserirDados();
            //}
        //});
    //}
   private String getDateTime() {
       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       Date date = new Date();
       return dateFormat.format(date);
   }

    private void inserirDados() {
        SQLiteDatabase db = new DataBaseHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("titulo",edit_title.getText().toString());
        contentValues.put("anotacao",edit_anotacao.getText().toString());
        contentValues.put("data",getDateTime());

        db.insert("tblNotas",null,contentValues);


        Toast.makeText(context,"Salvo com Sucesso",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context,MainActivity.class);
        startActivity(intent);
    }
    private void configuracaoBotaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_salvar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirDados();
            }
        });
    }


}
