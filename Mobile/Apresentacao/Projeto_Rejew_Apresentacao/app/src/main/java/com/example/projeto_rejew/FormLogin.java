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

public class FormLogin extends AppCompatActivity {
    private EditText senha;
    private ImageView olhoVisibilidade;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


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

    public void passarHome(View v) {
        Intent intent = new Intent(FormLogin.this, Home.class);
        startActivity(intent);
    }

    public void entrar(View v) {
        Intent intent = new Intent(FormLogin.this, PaginaInicial.class);
        startActivity(intent);
    }

    public void passarCadastro(View v) {
        Intent intent = new Intent(FormLogin.this, TipoContaSignUp.class);
        startActivity(intent);
    }

}