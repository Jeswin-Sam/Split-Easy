package com.jeswinsam.spliteasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OutputActivity extends AppCompatActivity {

    TextView output_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        output_text = findViewById(R.id.output_textview);

        Intent intent = getIntent();
        String output = intent.getStringExtra("output_text");

        output_text.setText(output);
    }
}
