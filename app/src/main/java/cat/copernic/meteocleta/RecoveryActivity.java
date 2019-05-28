package cat.copernic.meteocleta;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecoveryActivity extends AppCompatActivity {

    EditText EmailReset;
    Button Send;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        EmailReset = findViewById(R.id.et_EmailReset);
        Send = findViewById(R.id.btn_Reset);


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(EmailReset.getText().toString());
                /*mAuth.sendPasswordResetEmail(Send.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Fail to send reset password email or missing email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/
            }
        });
    }
    private void resetPassword(final String email) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Reset email instructions sent to " + email, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), email + " does not exist", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
