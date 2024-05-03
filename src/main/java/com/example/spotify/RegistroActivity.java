package com.example.spotify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends MainActivity {
    Button  btnRegistro;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        final EditText etId = findViewById(R.id.etId);
        final EditText etName = findViewById(R.id.etName);
        final EditText etLastName = findViewById(R.id.etLastName);
        final EditText etPhone = findViewById(R.id.etPhone);
        final EditText etAge = findViewById(R.id.etAge);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = etId.getText().toString().trim();
                final String name = etName.getText().toString().trim();
                final String lastName = etLastName.getText().toString().trim();
                final String phone = etPhone.getText().toString().trim();
                final String age = etAge.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);
                final String role = radioButton.getText().toString();

                // Registrar al usuario en Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registro en Firebase Authentication exitoso
                                    Toast.makeText(RegistroActivity.this, "Registro exitoso.", Toast.LENGTH_SHORT).show();

                                    // Obtener el UID del usuario registrado
                                    String uid = mAuth.getCurrentUser().getUid();

                                    // Guardar el usuario en Firebase Realtime Database
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UsersAdmins");
                                    user user = new user(id, name, lastName, phone, age, email, role);
                                    databaseReference.child(uid).setValue(user);

                                    // Navegar a la actividad de inicio de sesión
                                    Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish(); // Opcional: cerrar la actividad actual si no quieres que el usuario vuelva atrás
                                } else {
                                    // Error en el registro en Firebase Authentication
                                    Toast.makeText(RegistroActivity.this, "Error en el registro : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}