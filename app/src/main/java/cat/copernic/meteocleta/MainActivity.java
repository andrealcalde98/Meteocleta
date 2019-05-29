package cat.copernic.meteocleta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    String TAG = "Meteocleta_login";
    private FirebaseAuth mAuth;
    Button btnLogin, btnSignup;
    EditText etEmail, etPassword;
    TextView txtforgot;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        etEmail = findViewById(R.id.etEnterEmail);
        etPassword = findViewById(R.id.etPassword);
        txtforgot = findViewById(R.id.txt_forgot);
        dialog = new ProgressDialog(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();


        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), RecoveryActivity.class);
                    startActivity(intent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.toString().isEmpty() || etPassword.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,
                            "Missing fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    conectaUsuario(etEmail.getText().toString(), etPassword.getText().toString());
                }


            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), activitySignUp.class);
                startActivity(i);
            }
        });

        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void conectaUsuario(String email, String password) {
          mAuth.signInWithEmailAndPassword(email, password)
          .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Login successfull.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(getApplicationContext(), ClimaActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());


                            Toast.makeText(MainActivity.this, "Login failed.",
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
}
