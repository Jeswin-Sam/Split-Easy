package com.example.passwordkeeper;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    EditText password_field;
    Button open_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        open_button = findViewById(R.id.open_button);
        password_field = findViewById(R.id.password_field);

        open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered_password = password_field.getText().toString();

                if (entered_password.equals("1234")) {
                    Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Wrong password! Try again", Toast.LENGTH_LONG).show();
            }
        });
    }
}