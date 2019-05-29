package cat.copernic.meteocleta;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;

public class activitySignUp extends AppCompatActivity {
    String TAG = "Meteocleta_signup";
    private static final String TAG2 = "SignUp";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button btnSignupRegistro;
    EditText etName, etPassword, etSurname, etEmail;
    Spinner etRol;
    TextView etDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignupRegistro = findViewById(R.id.btn_change);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etEnterPassword);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEnterEmail);

        etRol = findViewById(R.id.etRol);
        etDate = findViewById(R.id.etDate);

        mAuth = FirebaseAuth.getInstance();

        ArrayList<String> roles = new ArrayList<>();
        roles.add("Student");
        roles.add("Employee");
        etRol.setPrompt("Choose your role");
        ArrayAdapter adp = new ArrayAdapter(activitySignUp.this, android.R.layout.simple_spinner_dropdown_item, roles);
        etRol.setAdapter(adp);


        etRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rol = (String) etRol.getAdapter().getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        activitySignUp.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                Log.d(TAG2, "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                etDate.setText(date);
            }
        };
        btnSignupRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty() || etSurname.getText().toString().isEmpty()
                        || etPassword.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()
                        || etRol.getSelectedItem().toString().isEmpty() || etDate.getText().toString().isEmpty()) {
                    Toast.makeText(activitySignUp.this, "Missing fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    crearUsuario(etEmail.getText().toString(), etPassword.getText().toString());

                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    void crearUsuario(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            añadirDatos();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activitySignUp.this, "Failed! try again",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) { //estos valores se podrían usar en el programa
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }

    private void añadirDatos() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("usuarios/" + mAuth.getUid() + "/datos/");

        myRef.child("name").setValue(etName.getText().toString());
        myRef.child("surname").setValue(etSurname.getText().toString());
        myRef.child("role").setValue(etRol.getSelectedItem().toString());
        myRef.child("birthday").setValue(etDate.getText().toString());
        Toast.makeText(getApplicationContext(), "Successful sign up!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
}
