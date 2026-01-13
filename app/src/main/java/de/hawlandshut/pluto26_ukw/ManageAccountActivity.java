package de.hawlandshut.pluto26_ukw;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        mTextViewTechnicalID = findViewById( R.id.manageAccountTextViewTechnicalId);
        mButtonSignOut  = findViewById( R.id.manageAccountButtonSignOut);
        mButtonSendVerificationMail = findViewById( R.id.manageAccountButtonSendVerificationMail);
        mEditTextPassword  = findViewById( R.id.manageAccountEditTextPassword);
        mButtonDeleteAccount  = findViewById( R.id.manageAccountButtonDeleteAccount);

        // 3c Listener registrieren
        mButtonDeleteAccount.setOnClickListener( this );
        mButtonSignOut.setOnClickListener( this );
        mButtonSendVerificationMail.setOnClickListener( this );

        mAuth = FirebaseAuth.getInstance();

        // Back Arrow einrichten.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user;
        user = mAuth.getCurrentUser();
        mTextViewEmail.setText("E-Mail : " + user.getEmail());

        if (user.isEmailVerified())
            mTextViewVerificationState.setText("Konto ist verifiziert");
        else
            mTextViewVerificationState.setText("Konto ist nicht verifiziert");

        mTextViewTechnicalID.setText("Technical Id : " + user.getUid());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i =  item.getItemId() ;
        if (i == android.R.id.home){
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(),
                            "No user signed in.",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        user.delete()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Account was deleted.",
                                            Toast.LENGTH_LONG)
                                    .show();
                            // User is deleted - lets go home to MainAct...
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                            "Deletion failed : " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }



    private void doSendVerificationMail() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) { // Should never happen...
            Toast.makeText(getApplicationContext(), "No user authentiated.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        user.sendEmailVerification()
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Erfolgsfall
                                    Toast.makeText(getApplicationContext(),
                                                    "Ver. Mail sent.",
                                                    Toast.LENGTH_LONG)
                                            .show();
                                } else {
                                    // Fehlerfall
                                    Toast.makeText(getApplicationContext(),
                                                    "Sending Verif. Mail Failed: "
                                                            + task.getException().getMessage(),
                                                    Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        }
                );
    }


    private void doSignOut() {
        mAuth.signOut();
        finish();
    }
}