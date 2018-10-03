package login.com.example.filipe.firebaseloginapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

public class MenuActivity extends Activity {
    private ImageView treino;
    private ImageView ficha;
    private ImageView perfil;
    private ImageView imc;
    private ImageView rss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        perfil = findViewById(R.id.imageView4);
        imc = findViewById(R.id.imageView6);
        treino = findViewById(R.id.imageView5);
        ficha = findViewById(R.id.imageView);
        rss = findViewById(R.id.imageView2);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,MainActivity.class));
            }
        });

        imc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,IMCActivity.class));
            }
        });

        treino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,TreinoActivity.class));
            }
        });

        ficha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,FichaActivity.class));
            }
        });

        rss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,RssActivity.class));
            }
        });
    }
}
