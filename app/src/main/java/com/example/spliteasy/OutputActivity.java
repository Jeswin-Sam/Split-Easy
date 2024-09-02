package com.example.spliteasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputActivity extends AppCompatActivity {

    TextView output_text;
    Button addToHistoryButton;
    EditText bill_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        Intent intent = getIntent();
        String output = intent.getStringExtra("output_text");

        output_text = findViewById(R.id.output_textview);
        addToHistoryButton = findViewById(R.id.add_to_history_button);
        bill_name = findViewById(R.id.bill_name);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        output_text.setText(output);

        addToHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String billName = bill_name.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy h:mm a");
                String date = formatter.format(new Date());

                if(billName.equals(""))
                    Toast.makeText(getApplicationContext(),"Bill name is empty",Toast.LENGTH_LONG).show();
                else {

                    if (dbHelper.addItem(billName, output, date)) {
                        Toast.makeText(getApplicationContext(), "Bill added to history", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(OutputActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
