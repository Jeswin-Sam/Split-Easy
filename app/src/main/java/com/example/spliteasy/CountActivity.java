package com.example.spliteasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CountActivity extends AppCompatActivity {

    EditText bill_amount, no_of_persons;
    Button next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        bill_amount = findViewById(R.id.bill_amount_editText);
        no_of_persons = findViewById(R.id.no_of_persons_editText);
        next_button = findViewById(R.id.next_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bill_amount_value = Integer.parseInt(bill_amount.getText().toString());
                int no_of_persons_value = Integer.parseInt(no_of_persons.getText().toString());

                // input validation
                if(no_of_persons_value < 2)
                    Toast.makeText(getApplicationContext(),"Number of persons should be greater than 1",Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent(CountActivity.this, InputActivity.class);

                    intent.putExtra("bill_amount", bill_amount_value);
                    intent.putExtra("no_of_persons", no_of_persons_value);

                    startActivity(intent);
                }
            }
        });
    }
}
