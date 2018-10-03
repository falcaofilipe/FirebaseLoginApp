package login.com.example.filipe.firebaseloginapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IMCActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
    }

    public void btnCalcularOnClick(View v){

        TextView lblResultado = (TextView)findViewById(R.id.lblResultado);
        EditText txtPeso = (EditText) findViewById(R.id.txtPeso);
        EditText txtAltura = (EditText) findViewById(R.id.txtAltura);

        int peso = Integer.parseInt(txtPeso.getText().toString());
        float altura = Float.parseFloat(txtAltura.getText().toString());

        float resultado = peso / (altura * altura);
        if(resultado < 18.5){
            //abaixo
            //lblResultado.setText("Abaixo do peso!");
            Toast.makeText(IMCActivity.this, "Abaixo do peso!", Toast.LENGTH_SHORT).show();
        }else if(resultado >= 25.0 && resultado<=29.9){
            //obeso
            //lblResultado.setText("Levemente acima do peso!");
            Toast.makeText(IMCActivity.this, "Levemente acima do peso!", Toast.LENGTH_SHORT).show();
        }else if(resultado > 29.9 && resultado <=34.9){
            //obeso
            //lblResultado.setText("Obesidade grau 1!");
            Toast.makeText(IMCActivity.this, "Obesidade grau 1!", Toast.LENGTH_SHORT).show();
        }else if(resultado > 35.0 && resultado <=39.9){
            //obeso
            //lblResultado.setText("Obesidade grau 2(SEVERA)!");
            Toast.makeText(IMCActivity.this, "Obesidade grau 2(SEVERA)!", Toast.LENGTH_SHORT).show();
        }else{
            //ok
            //lblResultado.setText("Peso ok!");
            Toast.makeText(IMCActivity.this, "Peso ok!", Toast.LENGTH_SHORT).show();
        }
    }
}
