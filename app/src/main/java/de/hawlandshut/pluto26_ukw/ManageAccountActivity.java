package de.hawlandshut.pluto26_ukw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManageAccountActivity extends AppCompatActivity
implements View.OnClickListener{
    FirebaseAuth mAuth;
    // 3.a Declare variables for UI Views
    TextView mTextViewEmail;
    TextView mTextViewVerificationState;
    TextView mTextViewTechnicalID;

    Button mButtonSignOut;
    Button mButtonSendVerificationMail;

    EditText mEditTextPassword;
    Button mButtonDeleteAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3.b Init variables for UI Views
        mTextViewEmail = findViewById( R.id.manageAccountTextViewEmail);
        mTextViewVerificationState = findViewById( R.id.manageAccountTextViewVerificationState);
        mTextViewTechnicalID = findViewById( R.id.manageAccountTextViewVerificationState);
        mButtonSignOut  = findViewById( R.id.manageAccountButtonSignOut);
        mButtonSendVerificationMail = findViewById( R.id.manageAccountButtonSendVerificationMail);
        mEditTextPassword  = findViewById( R.id.manageAccountEditTextPassword);
        mButtonDeleteAccount  = findViewById( R.id.manageAccountButtonDeleteAccount);

        // 3c Listener registrieren
        mButtonDeleteAccount.setOnClickListener( this );
        mButtonSignOut.setOnClickListener( this );
        mButtonSendVerificationMail.setOnClickListener( this );

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.manageAccountButtonSignOut){
            doSignOut();
        }

        if (view.getId() == R.id.manageAccountButtonSendVerificationMail){
            doSendVerificationMail();
        }

        if(view.getId() == R.id.manageAccountButtonDeleteAccount){
            doDeleteAccount();
        }
    }

    private void doDeleteAccount() {
        Toast.makeText(getApplicationContext(), "You pressed DeleteAccount", Toast.LENGTH_LONG).show();
    }

    private void doSendVerificationMail() {
        Toast.makeText(getApplicationContext(), "You pressed SendVeriMail", Toast.LENGTH_LONG).show();
    }

    private void doSignOut() {
        mAuth.signOut();
        finish();
    }
}