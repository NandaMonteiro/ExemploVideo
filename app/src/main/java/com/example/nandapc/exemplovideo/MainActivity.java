package com.example.nandapc.exemplovideo;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    EditText editNome, editTipo, editContto;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = (EditText) findViewById(R.id.editNome);
        editContto = (EditText) findViewById(R.id.editContato);
        editTipo = (EditText) findViewById(R.id.editTipo);

        db = openOrCreateDatabase("ContatosDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS contatos (Nome VARCHAR, Contato VARCHAR, Tipo VARCHAR);");
    }

    public void buttonAdicionar(View view){
        if (editNome.getText().toString().trim().length() == 0 || editContto.getText().toString().trim().length()== 0 || editTipo.getText().toString().trim().length()==0){
            showMessage("Erro.", " Preencha corretamente os valores!!");
            return;
        }
        db.execSQL("INSERT INTO contatos VALUES('" + editNome.getText()+ "','" + editContto.getText()+ "','" + editTipo.getText()+"');");
        showMessage("OK", "Dados Adicionados");
        clearText();
    }

    public void buttonDeletar(View view){
        if (editNome.getText().toString().trim().length() == 0){
            showMessage("Erro", "Entre com o nome!");
        }
        Cursor cursor = db.rawQuery("SELECT * FROM contatos WHERE '" + editNome.getText() + "'", null);
        if (cursor.moveToFirst()){
            db.execSQL("DELETE FROM contatos WHERE '" + editNome.getText() + "'");
            showMessage("OK", "Dados Deletados");
        }
        else {
            showMessage("Erro", "Inválido");
        }
        clearText();

    }

    public void buttonSalvarEdicoes(View view){
        if (editNome.getText().toString().trim().length() == 0){
            showMessage("Erro", "Entre com o nome!");
        }
        Cursor cursor = db.rawQuery("SELECT * FROM contatos WHERE '" + editNome.getText() + "'", null);
        if (cursor.moveToFirst()){
            db.execSQL("UPDATE contatos SET Nome='"+ editNome.getText() +"', Contato='"+editContto.getText() +"', Tipo='"+editTipo.getText()+"'WHERE Nome='"+editNome.getText()+"'");
            showMessage("OK", "Dados Editados ");
        }
        else {
            showMessage("Erro", "Faça uma busca primeiro");
        }
        clearText();

    }

    public void buttonBuscarContato(View view){
        if (editNome.getText().toString().trim().length() == 0){
            showMessage("Erro", "Entre com o nome!");
        }
        Cursor cursor = db.rawQuery("SELECT * FROM  contato WHERE Nome='"+editNome.getText()+"'",null);
        if (cursor.moveToFirst()){
            editNome.setText(cursor.getString(0));
            editContto.setText(cursor.getString(1));
            editTipo.setText(cursor.getString(2));
        }
        else {
            showMessage("Erro", "Nome Inválido");
            clearText();
        }
    }

    public void buttonListarContatos(View view){
        Cursor cursor = db.rawQuery("SELECT * FROM contatos", null);
        if(cursor.getCount() == 0){
            showMessage("Erro", "Nada Encontrado");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            buffer.append("Nome: "+ cursor.getString(0)+ "\n");
        }
        showMessage("Detalhes dos Dados", buffer.toString());
    }

    public void showMessage(String title, String mensagem){
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(mensagem);
        builder.show();
    }

    public void clearText(){
        editNome.setText("");
        editContto.setText("");
        editTipo.setText("");
    }

//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.);
//    }
}
