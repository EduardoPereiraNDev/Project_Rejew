package com.example.projeto_rejew;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormSignUp extends AppCompatActivity {
    private EditText senha;
    private ImageView olhoVisibilidade;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup_leitor);


        senha = findViewById(R.id.senha);
        olhoVisibilidade = findViewById(R.id.olhoIcon);


        olhoVisibilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {

                    senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    olhoVisibilidade.setImageResource(R.drawable.olhoon);
                } else {

                    senha.setInputType(InputType.TYPE_CLASS_TEXT);
                    olhoVisibilidade.setImageResource(R.drawable.olhooff);
                }

                senha.setSelection(senha.getText().length());

                isPasswordVisible = !isPasswordVisible;
            }
        });


        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void passarLogin(View v) {
        Intent intent = new Intent(FormSignUp.this, FormLogin.class);
        startActivity(intent);
    }
    public void cadastrar(View v) {
        Intent intent = new Intent(FormSignUp.this, Home.class);
        startActivity(intent);
    }
    public void voltarTipoConta(View v){
        Intent intent = new Intent(FormSignUp.this, TipoContaSignUp.class);
        startActivity(intent);
    }
}