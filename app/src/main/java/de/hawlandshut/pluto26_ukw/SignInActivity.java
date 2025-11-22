package de.hawlandshut.pluto26_ukw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
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
        Toast.makeText(getApplicationContext(), "You pressed CreateAccount", Toast.LENGTH_LONG).show();
    }

    private void doResetPassword() {
        Toast.makeText(getApplicationContext(), "You pressed ResetPassword", Toast.LENGTH_LONG).show();

    }

    private void doSignIn() {
        Toast.makeText(getApplicationContext(), "You pressed SignIn", Toast.LENGTH_LONG).show();
    }
}