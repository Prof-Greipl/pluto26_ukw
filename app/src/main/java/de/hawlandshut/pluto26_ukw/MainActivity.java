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
    private static final String TEST_PASSWORD ="123456";

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

        mAdapter.mPostList = Testdata.createPostList(100);

        mRecyclerView = (RecyclerView) findViewById( R.id.recycler_view);
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this ));
        mRecyclerView.setAdapter( mAdapter );

        mAuth = FirebaseAuth.getInstance();

        // TODO: Nur zum Test, später löschen
        Log.d(TAG, "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
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
        if (item.getItemId() == R.id.menu_test_auth) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user == null) {
                Toast.makeText(getApplicationContext(), "No user signed in.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "User : " + user.getEmail(), Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
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