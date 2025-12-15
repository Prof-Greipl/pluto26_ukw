package de.hawlandshut.pluto26_ukw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hawlandshut.pluto26_ukw.test.Testdata;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "xxx MainActivity";
    CustomAdapter mAdapter;
    RecyclerView mRecyclerView;
    FirebaseAuth mAuth;

    // TODO: Only for testing remove later
    private static final String TEST_MAIL = "fhgreipl@gmail.com";
    private static final String TEST_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Manage the adapter with testdata
        mAdapter = new CustomAdapter();

        mAdapter.mPostList = Testdata.createPostList(3);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mAuth = FirebaseAuth.getInstance();

        // TODO: Nur zum Test, später löschen
        Log.d(TAG, "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser  user =  mAuth.getCurrentUser();
        if (user == null){
            // Kein User angemeldet
            Intent intent = new Intent(getApplication(), SignInActivity.class);
            startActivity(  intent );
        }

        // Absprung in die SignIn-Activity
        //Intent intent = new Intent(getApplication(), CreateAccountActivity.class);
        //startActivity( intent );
        Log.d(TAG, "onStart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_post) {
            Intent intent = new Intent(getApplication(), PostActivity.class);
            startActivity(  intent );
        }

        if (item.getItemId() == R.id.menu_manage_account) {
            Intent intent = new Intent(getApplication(), ManageAccountActivity.class);
            startActivity(  intent );
        }

        return super.onOptionsItemSelected(item);
    }

    private void AuthSignOut() {
        FirebaseUser user;
        user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(),
                    "No user signed in. Please sign in first",
                    Toast.LENGTH_LONG).show();
        } else {
            mAuth.signOut();
            Toast.makeText(getApplicationContext(),
                    "Your are signed out.",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void AuthSignInWithEmailAndPassword(String email, String password) {
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

    private void UserSendMailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
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

    private void AuthSendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "We sent you a link to your e-mail account.",
                                            Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                            "Could not send mail. Correct e-mail?.",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    private void UserDelete() {
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

                        } else {
                            Toast.makeText(getApplicationContext(),
                                            "Deletion failed : " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    private void AuthCreateUser(String testMail, String testPassword) {
        mAuth.createUserWithEmailAndPassword(testMail, testPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "User created",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Failure: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void UserTestAuthStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(), "No user signed in.", Toast.LENGTH_LONG).show();
        } else {
            String msg = " User : " + user.getEmail() + " (" + user.isEmailVerified() + ")";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
}