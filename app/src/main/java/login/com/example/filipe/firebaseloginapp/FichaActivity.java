package login.com.example.filipe.firebaseloginapp;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FichaActivity extends Activity {

    private SQLiteDatabase bancoDados;
    private ListView listaTarefas;
    private TextView email;

    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = (TextView) findViewById(R.id.email);
        setDataToView(user);

        try {
            //lista
            listaTarefas = (ListView) findViewById(R.id.listaTreinoId);

            //Banco dados
            bancoDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

            //tabela tarefas
           // bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas(id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR, tarefa VARCHAR ) ");
            //bancoDados.execSQL("DROP TABLE tarefas");

            //recuperar tarefas
            recuperarTarefas(user);


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        email.setText("Email: " + user.getEmail());


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

            //Padrões de projeto:
            //Padrões Estruturais
            //Adapter - A ação desse padrão converte a interface de uma classe em outra, esperada pelo objeto cliente.
            // Através dessa conversão, permite que classes com incompatibilidade de interfaces, consigam serem adaptadas
            // para que outros objetos possam trabalhar juntos.
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
}
