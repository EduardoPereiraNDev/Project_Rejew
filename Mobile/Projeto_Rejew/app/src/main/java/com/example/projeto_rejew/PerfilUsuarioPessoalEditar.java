package com.example.projeto_rejew;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.projeto_rejew.api.RetrofitClient;
import com.example.projeto_rejew.api.UsuarioAPIController;
import com.example.projeto_rejew.entity.Livro;
import com.example.projeto_rejew.entity.Usuario;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class PerfilUsuarioPessoalEditar extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST_FUNDO= 2;
    private UsuarioAPIController usuarioAPIController;
    private String emailEntrada;
    private TextView emailUsuario;
    private EditText nomePerfil;
    private EditText nomeUsuario;
    private EditText senha;
    private EditText dataNascimento;
    private EditText recadoPerfil;
    private Uri imageUri;
    private Uri imageUriFundo;
    private ImageView imageView;
    private Button btnSelectImage;
    private ImageView imageViewFundo;
    private Button btnSelectImageFundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario_pessoal_editar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSelectImage.setOnClickListener(v -> openImagePicker());

        btnSelectImageFundo = findViewById(R.id.btn_select_image_fundo);
        btnSelectImageFundo.setOnClickListener(v -> openImagePickerFundo());

        EditText editTextDate = findViewById(R.id.dataNascimento);

        editTextDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, month1, dayOfMonth) -> {
                        String selectedDate = String.format("%02d-%02d-%04d", dayOfMonth, month1 + 1, year1);
                        editTextDate.setText(selectedDate);
                    },
                    year,
                    month,
                    day
            );
            datePickerDialog.show();
        });


        emailUsuario = findViewById(R.id.emailUsuario);

        nomePerfil = findViewById(R.id.nomePerfil);
        nomeUsuario = findViewById(R.id.nomeUsuario);
        senha = findViewById(R.id.senha);
        dataNascimento = findViewById(R.id.dataNascimento);
        recadoPerfil = findViewById(R.id.recadoPerfil);
        imageView = findViewById(R.id.imagemPerfil);
        imageViewFundo = findViewById(R.id.imagePerfilFundo);

        RetrofitClient retrofitClient = new RetrofitClient();
        usuarioAPIController = new UsuarioAPIController(retrofitClient);

        emailEntrada = recuperarEmailUsuario();

        carregarUsuario(emailEntrada);
        Log.d("TAG", "Dados:"+ emailEntrada );
    }

    private String recuperarEmailUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("usuarioDados", MODE_PRIVATE);
        return sharedPreferences.getString("emailEntrada", null);
    }



    private void carregarUsuario(String emailEntrada) {
        usuarioAPIController.buscarUsuario(emailEntrada, new UsuarioAPIController.UsuarioCallback() {

            @Override
            public void onSuccess(Usuario usuario) {
                if (usuario != null) {
                    emailUsuario.setText(usuario.getEmailEntrada());
                    nomePerfil.setText(usuario.getNomePerfil());
                    nomeUsuario.setText(usuario.getNomeUsuario());
                    senha.setText(usuario.getSenhaEntrada());
                    dataNascimento.setText(usuario.getDataNascimento());
                    recadoPerfil.setText(usuario.getRecadoPerfil());

                    usuarioAPIController.carregarImagemPerfil(usuario.getCaminhoImagem(), new UsuarioAPIController.UsuarioCallback() {
                        @Override
                        public void onSuccess(Usuario usuario) {
                        }

                        @Override
                        public void onSuccessBoolean(Boolean favoritado) {
                        }

                        @Override
                        public void onSuccessInt(Integer integer) {

                        }

                        @Override
                        public void onSuccessByte(byte[] bytes) {
                            Glide.with(PerfilUsuarioPessoalEditar.this)
                                    .load(bytes)
                                    .override(90, 90)
                                    .centerCrop()
                                    .into(imageView);
                        }

                        @Override
                        public void onSuccessList(List<Usuario> usuarios) {

                        }

                        @Override
                        public void onSuccessListL(List<Livro> livros) {

                        }

                        @Override
                        public void onSuccessResponse(ResponseBody body) {

                        }

                        @Override
                        public void onSuccessString(String string) {

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("UsuarioAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                            imageView.setImageResource(R.drawable.imagedefault);
                        }

                    });

                    usuarioAPIController.carregarImagemFundo(usuario.getCaminhoImagemFundo(), new UsuarioAPIController.UsuarioCallback() {
                        @Override
                        public void onSuccess(Usuario usuario) {
                        }

                        @Override
                        public void onSuccessBoolean(Boolean favoritado) {
                        }

                        @Override
                        public void onSuccessInt(Integer integer) {

                        }

                        @Override
                        public void onSuccessByte(byte[] bytes) {
                            Glide.with(PerfilUsuarioPessoalEditar.this)
                                    .load(bytes)
                                    .override(140, 100)
                                    .centerCrop()
                                    .into(imageViewFundo);
                        }

                        @Override
                        public void onSuccessList(List<Usuario> usuarios) {

                        }

                        @Override
                        public void onSuccessListL(List<Livro> livros) {
                        }

                        @Override
                        public void onSuccessResponse(ResponseBody body) {

                        }

                        @Override
                        public void onSuccessString(String string) {

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("UsuarioAdapter", "Falha ao carregar a imagem: " + t.getMessage());
                            imageViewFundo.setImageResource(R.drawable.imagedefault);
                        }

                    });

                }
            }

            @Override
            public void onSuccessBoolean(Boolean favoritado) {
            }

            @Override
            public void onSuccessInt(Integer integer) {

            }

            @Override
            public void onSuccessByte(byte[] bytes) {
            }

            @Override
            public void onSuccessList(List<Usuario> usuarios) {
            }

            @Override
            public void onSuccessListL(List<Livro> livros) {

            }

            @Override
            public void onSuccessResponse(ResponseBody body) {

            }

            @Override
            public void onSuccessString(String string) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void openImagePickerFundo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_FUNDO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            if (requestCode == PICK_IMAGE_REQUEST) {
                imageUri = selectedImageUri;
                imageView.setImageURI(imageUri);
            } else if (requestCode == PICK_IMAGE_REQUEST_FUNDO) {
                imageUriFundo = selectedImageUri;
                imageViewFundo.setImageURI(imageUriFundo);
            }
        }
    }

    public void atualizarUsuario(View view) {
        String emailBody = emailUsuario.getText().toString();
        String nomeBody = nomePerfil.getText().toString();
        String usuarioBody = nomeUsuario.getText().toString();
        String senhaBody = senha.getText().toString();
        String dataNascimentoBody = dataNascimento.getText().toString();
        String recadoBody = recadoPerfil.getText().toString();

        MultipartBody.Part imagemPerfilPart = null;
        if (imageUri != null) {
            File file = new File(getPathFromUri(imageUri));
            RequestBody requestFile = RequestBody.create(file, okhttp3.MediaType.parse(getContentResolver().getType(imageUri)));
            imagemPerfilPart = MultipartBody.Part.createFormData("imagemPerfil", file.getName(), requestFile);
        }


        MultipartBody.Part imagemFundoPart = null;
        if (imageUriFundo != null) {
            File fileFundo = new File(getPathFromUri(imageUriFundo));
            RequestBody requestFileFundo = RequestBody.create(fileFundo, okhttp3.MediaType.parse(getContentResolver().getType(imageUriFundo)));
            imagemFundoPart = MultipartBody.Part.createFormData("imagemFundo", fileFundo.getName(), requestFileFundo);
        }

        usuarioAPIController.atualizarUsuario(imagemPerfilPart, imagemFundoPart, emailBody, recadoBody, usuarioBody, nomeBody, senhaBody, dataNascimentoBody,
                new UsuarioAPIController.UsuarioCallback() {
                    @Override
                    public void onSuccess(Usuario usuario) {
                    }
                    @Override
                    public void onSuccessBoolean(Boolean favoritado) { }
                    @Override
                    public void onSuccessInt(Integer integer) { }
                    @Override
                    public void onSuccessByte(byte[] bytes) { }
                    @Override
                    public void onSuccessList(List<Usuario> usuarios) { }
                    @Override
                    public void onSuccessListL(List<Livro> livros) { }
                    @Override
                    public void onSuccessResponse(ResponseBody body) {
                        Intent intent = new Intent(PerfilUsuarioPessoalEditar.this, PerfilUsuarioPessoal.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onSuccessString(String string) { }
                    @Override
                    public void onFailure(Throwable t) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(PerfilUsuarioPessoalEditar.this);
                        alerta.setCancelable(false);
                        alerta.setTitle("Login");
                        alerta.setMessage("Falha ao realizar Login" + t.getMessage());
                        alerta.setNegativeButton("Voltar",null);
                        alerta.create().show();
                        Log.e("API_ERROR", "Falha ao atualizar usuário", t);
                    }
                });
    }

    private String getPathFromUri(Uri uri) {
        String path = null;

        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            String[] split = docId.split(":");
            String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }

        return path;
    }

    public void VoltarFinish(View view) {
        finish();
    }
}