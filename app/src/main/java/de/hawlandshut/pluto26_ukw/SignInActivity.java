package de.hawlandshut.pluto26_ukw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "xxx SignInActivity";

    FirebaseAuth mAuth;

    // 3.a Define Variables for UI Views
    EditText mEditTextEmail;
    EditText mEditTextPassword;
    Button mButtonSignIn;
    Button mButtonResetPassword;
    Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3.b Init variables for UI-Views
        mEditTextEmail = findViewById(R.id.signInEditTextEmail);
        mEditTextPassword = findViewById( R.id.signInEditTextPassword);
        mButtonSignIn = findViewById( R.id.signInButtonSignIn);
        mButtonResetPassword = findViewById( R.id.signInButtonResetPassword);
        mButtonCreateAccount = findViewById( R.id.signInButtonCreateAccount);

        // 3.c Listener registrieren
        mButtonSignIn.setOnClickListener( this );
        mButtonResetPassword.setOnClickListener( this );
        mButtonCreateAccount.setOnClickListener( this );

        // TODO: Only for testing remove later
        mEditTextEmail.setText("fhgreipl@gmail.com");
        mEditTextPassword.setText("123456");

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            finish();
        }
    }

    // 3.c Listener implementieren
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signInButtonSignIn) {
            doSignIn();
        }

        if (view.getId() == R.id.signInButtonResetPassword){
            doResetPassword();
        }

        if (view.getId() == R.id.signInButtonCreateAccount){
            doGotoCreateAccount();
        }
    }



    private void doGotoCreateAccount() {
        Intent intent = new Intent(getApplication(), CreateAccountActivity.class);
        startActivity(  intent );
    }

    private void doResetPassword() {
        String email = mEditTextEmail.getText().toString();
        // Validierungen
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "We sent you a link to your e-mail account.",
                                            Toast.LENGTH_LONG)
                                    .show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                            "Could not send mail. Correct e-mail?.",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    private void doSignIn() {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();
        // Validierungen
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),
                                            "User signed in",
                                            Toast.LENGTH_LONG)
                                    .show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),
                                            "User signIn failed." + task.getException().getMessage(),
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                        // ...
                    }
                });
    }
}