package com.example.spliteasy;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {

    TextView bill_amount_textView;
    Button calculate_button;
    TableLayout table;

    public static String BillSplit(ArrayList<Person> people){

        // declare positive and negative array lists
        ArrayList<Person> positive_persons = new ArrayList<Person>();
        ArrayList<Person> negative_persons = new ArrayList<Person>();

        String output = "";

        // to split the array into positive and negative arrays
        for(int i = 0; i < people.size(); i++){

            if(people.get(i).balance > 0)
                positive_persons.add(people.get(i));

            if(people.get(i).balance < 0)
                negative_persons.add(people.get(i));
        }

        // convert negative balances into positive balances for negative people
        for(int i = 0; i < negative_persons.size(); i++)
            negative_persons.get(i).balance = negative_persons.get(i).balance*-1;


        int p=0, n=0;
        while(p<positive_persons.size() && n<negative_persons.size()) {


            if(positive_persons.get(p).balance > negative_persons.get(n).balance){

                output = output + "\n" + negative_persons.get(n).name + " owes " + positive_persons.get(p).name + " " + negative_persons.get(n).balance + " rupees\n";

                positive_persons.get(p).balance = positive_persons.get(p).balance - negative_persons.get(n).balance;
                negative_persons.get(n).balance = 0;

                n = n + 1;
                continue;
            }


            if(positive_persons.get(p).balance < negative_persons.get(n).balance){

                output = output + "\n" + negative_persons.get(n).name + " owes " + positive_persons.get(p).name + " " + positive_persons.get(p).balance + " rupees\n";

                positive_persons.get(p).balance = 0;
                negative_persons.get(n).balance = negative_persons.get(n).balance - positive_persons.get(p).balance;

                p = p + 1;
                continue;
            }


            if(positive_persons.get(p).balance == negative_persons.get(n).balance){

                output = output + "\n" + negative_persons.get(n).name + " owes " + positive_persons.get(p).name + " " + positive_persons.get(p).balance + " rupees\n";

                positive_persons.get(p).balance = 0;
                negative_persons.get(n).balance = 0;

                p = p + 1;
                n = n + 1;
                continue;
            }
        }
        return output;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent = getIntent();
        int bill_amount = intent.getIntExtra("bill_amount",0);
        int no_of_persons = intent.getIntExtra("no_of_persons", 1);

        bill_amount_textView = findViewById(R.id.bill_amount_textView);
        calculate_button = findViewById(R.id.calculate_button);
        table = findViewById(R.id.table);

        bill_amount_textView.setText("Total bill amount : " + bill_amount);

//        for dynamically creating the fields
        for (int i = 1; i<=no_of_persons; i++){
            TableRow row = new TableRow(this);

            row.setPadding(10,10,10,10);

            EditText nameEditText = new EditText(this);
            nameEditText.setId(i);
            nameEditText.setHint("Person " + i);
            nameEditText.setWidth(400);

            EditText amountEditText = new EditText(this);
            amountEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            amountEditText.setId(no_of_persons+i);
            amountEditText.setHint("Amount");
            amountEditText.setWidth(400);

            row.addView(nameEditText);
            row.addView(amountEditText);

            table.addView(row);
        }

        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String output;
                ArrayList<Person> people = new ArrayList<>();

                double share_per_person = bill_amount / no_of_persons;
                double total_amount = 0;

                for (int i = 1; i <= no_of_persons; i++) {
                    EditText editTextName = findViewById(i);
                    EditText editTextContribution = findViewById(no_of_persons+i);

                    String name;
                    double contribution, balance;

                    name = editTextName.getText().toString();
                    contribution = Integer.parseInt(editTextContribution.getText().toString());

                    balance = contribution - share_per_person;

                    people.add(new Person(name, contribution, balance));

                    total_amount += contribution;
                }

                if(total_amount < bill_amount)
                    Toast.makeText(getApplicationContext(), "Total amount is less than bill amount", Toast.LENGTH_LONG).show();

                else if (total_amount > bill_amount)
                    Toast.makeText(getApplicationContext(), "Total amount is greater than bill amount", Toast.LENGTH_LONG).show();

                else {
                    output = BillSplit(people);
                    Intent intent = new Intent(InputActivity.this, OutputActivity.class);
                    intent.putExtra("output_text", output);
                    startActivity(intent);
                }
            }
        });

    }
}