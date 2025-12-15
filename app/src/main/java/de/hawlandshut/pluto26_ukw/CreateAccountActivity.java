package de.hawlandshut.pluto26_ukw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity
        implements View.OnClickListener{

    FirebaseAuth mAuth;

    private EditText mEditTextEMail;
    private EditText mEditTextPassword1;
    private EditText mEditTextPassword2;
    private Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mEditTextEMail =  findViewById(R.id.createAccountEmail);
        mEditTextPassword1 =  findViewById(R.id.createAccountPassword1);
        mEditTextPassword2 =  findViewById(R.id.createAccountPassword2);
        mButtonCreateAccount = findViewById( R.id.createAccountButtonCreateAccount);

        mButtonCreateAccount.setOnClickListener( this );

        // TODO: Presets for testing - remove for prod
        mEditTextEMail.setText("xxx@gmail.com");
        mEditTextPassword1.setText("123456");
        mEditTextPassword2.setText("123456");

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createAccountButtonCreateAccount){
            doCreateAccount();
        }
    }

    private void doCreateAccount() {
        String email = mEditTextEMail.getText().toString();
        String password = mEditTextPassword1.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "User created",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Failure: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}