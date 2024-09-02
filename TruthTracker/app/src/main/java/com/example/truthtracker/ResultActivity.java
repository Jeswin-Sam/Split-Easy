package com.example.truthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.result_activity);

        TextView news_text = findViewById(R.id.news_text);
        TextView probability_text = findViewById(R.id.probability_text);
        TextView message_text = findViewById(R.id.message_text);

        Intent intent = getIntent();
        String input_text = intent.getStringExtra("input_text");

        news_text.setText(input_text);

        // Initialize Python
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject pyf = py.getModule("forest-test");

        // Load the model and vectorizer from the PKL file
        PyObject modelAndVectorizer = pyf.callAttr("load_model_and_vectorizer", "random_forest_model.pkl");

        // Get the model and vectorizer as separate objects
        PyObject model = modelAndVectorizer.get("model");
        PyObject vectorizer = modelAndVectorizer.get("vectorizer");

        // Perform inference
        PyObject prediction = pyf.callAttr("predict", input_text);

        // Convert the result to Java string and display
        String result = prediction.toJava(String.class);

        Float probability = new Float(result);
        probability = probability%.4f;
        float percentage_float= probability * 250;

        String percentage_string = String.format("%.2f", percentage_float) + "%";

        probability_text.setText(percentage_string);

        String message = "Loading";

        if(percentage_float<100 & percentage_float>=80){
            message = "There is a very high chance that this news is true. For further verification, refer news articles.";
        }
        else if(percentage_float<80 & percentage_float>=60){
            message = "There is a high chance that this news is true. For further verification, refer news articles.";
        }
        else if(percentage_float<60 & percentage_float>=40){
            message = "There is a moderate chance that this news is true. For further verification, refer news articles.";
        }
        else if(percentage_float<40 & percentage_float>=20){
            message = "There is a high chance that this news is false. For further verification, refer news articles.";
        }
        else if(percentage_float<20 & percentage_float>=0){
            message = "There is a very high chance that this news is false. For further verification, refer news articles.";
        }

        message_text.setText(message);

    }
}