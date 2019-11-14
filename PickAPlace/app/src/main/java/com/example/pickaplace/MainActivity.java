package com.example.pickaplace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText user1, user2;
    Button startButton;
    Button startRoundButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user1 = findViewById(R.id.user1);
        user2 = findViewById(R.id.user2);
        startButton = findViewById(R.id.start_button);
        startRoundButton = findViewById(R.id.start_round_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRound(user1.getText().toString(), 1);
            }
        });

        startRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    protected void startRound(String userName, int roundNum){
        setContentView(R.layout.round_title);
        TextView title = findViewById(R.id.title);
        TextView uName = findViewById(R.id.round_username);
        switch(roundNum){
            case 1:
                title.setText("Welcome to round 1:");
                uName.setText(userName);
                break;
            case 2:
                title.setText("Welcome to round 2");
                uName.setText(userName);
                break;
            case 3:
                title.setText("Welcome to round 3");
                uName.setText(userName);
                break;
            default:
                break;
        }

    }




}
