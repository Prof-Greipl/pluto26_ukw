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

public class PostActivity extends AppCompatActivity
        implements View.OnClickListener{

    EditText mEditTextTitle;
    EditText mEditTextBody;
    Button mButtonPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mEditTextBody = findViewById(R.id.postEditTextText);
        mEditTextTitle = findViewById(R.id.postEditTextTitle);
        mButtonPost = findViewById(R.id.postButtonPost);

        mButtonPost.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.postButtonPost) {
            doPost();
        }
    }

    private void doPost() {
        Toast.makeText(getApplicationContext(), "Pressed Post", Toast.LENGTH_LONG).show();
    }
}