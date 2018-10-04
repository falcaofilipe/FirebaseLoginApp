package login.com.example.filipe.firebaseloginapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class TreinoActivity extends Activity {

    private EditText textoTarefa;
    private Button botaoAdicionar;
    private ListView listaTarefas;

    private SQLiteDatabase bancoDados;

    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Padrão de projeto:
        //Padrões de criação
        //Singleton - Usado quando desejado, que uma classe tenha apenas uma instância na aplicação.

       try {

        //Recuperar componentes
        textoTarefa = (EditText) findViewById(R.id.textoId);
        botaoAdicionar = (Button) findViewById(R.id.botaoAdicionarId);

        //lista
        listaTarefas = (ListView) findViewById(R.id.listViewId);

        //Banco dados
        bancoDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

        //tabela tarefas
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas(id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR, tarefa VARCHAR ) ");
        //bancoDados.execSQL("DROP TABLE tarefas");
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoDigitado = textoTarefa.getText().toString();
                salvarTarefa(user, textoDigitado);

            }
        });

            /*listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    removerTarefa( ids.get( position ) );
                }
            });*/

        listaTarefas.setLongClickable(true);
        listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removerTarefa(user ,ids.get( position ) );
                return true;
            }
        });

        //recuperar tarefas
        recuperarTarefas(user);


    }catch(Exception e){
        e.printStackTrace();
    }

}

    private void salvarTarefa(FirebaseUser user, String texto){

        try{

            if( texto.equals("") ){
                Toast.makeText(TreinoActivity.this, "Digite um exercício", Toast.LENGTH_SHORT).show();
            }else{
                bancoDados.execSQL("INSERT INTO tarefas (tarefa,email) VALUES('" + texto + "','"+user.getEmail()+"')");
                Toast.makeText(TreinoActivity.this, "Exercício salvo com sucesso!", Toast.LENGTH_SHORT).show();
                recuperarTarefas(user);
                textoTarefa.setText("");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void recuperarTarefas(FirebaseUser user){
        try{

            //Recuperar as tarefas
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas WHERE email ='"+user.getEmail()+"' ORDER BY id DESC", null);

            //recuperar os ids das colunas
            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceColunaTarefa = cursor.getColumnIndex("tarefa");
            int indiceColunaEmail = cursor.getColumnIndex("email");

            //Criar adaptador
            itens = new ArrayList<String>();
            ids = new ArrayList<Integer>();
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_2,
                    android.R.id.text2,
                    itens){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text2);
                    text.setTextColor(Color.BLACK);
                    return view;

                }
            };
            listaTarefas.setAdapter( itensAdaptador );

            //listar as tarefas
            cursor.moveToFirst();
            while ( cursor != null ){

                Log.i("Resultado - ", "Id Tarefa: " + cursor.getString( indiceColunaId ) + " Tarefa: " + cursor.getString( indiceColunaTarefa ) + " Email: " + cursor.getString( indiceColunaEmail ) );
                itens.add(cursor.getString(indiceColunaTarefa));
                ids.add( Integer.parseInt(cursor.getString(indiceColunaId)) );

                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removerTarefa(FirebaseUser user, Integer id){
        try{

            bancoDados.execSQL("DELETE FROM tarefas WHERE id="+id);
            recuperarTarefas(user);
            Toast.makeText(TreinoActivity.this, "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
