package inote.com.br.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 15251365 on 15/02/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "iNote.db";
    private static final int VERSAO=1;

    public DataBaseHelper (Context context){
        super(context,NOME_BANCO,null,VERSAO);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tblNotas(" +
                "_id  INTEGER PRIMARY KEY," +
                "titulo TEXT," +
                "anotacao TEXT," +
                "data DATE);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
