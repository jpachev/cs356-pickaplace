package com.example.pickaplace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import Model.User;

public class MainActivity extends AppCompatActivity {

    EditText userName1, userName2;
    Button startButton;
    Button startRoundButton;
    User user1, user2;
    private RadioGroup radioChoiceGroup;
    private RadioButton radioChoiceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName1 = findViewById(R.id.user1);
        userName2 = findViewById(R.id.user2);
        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user1 = new User(userName1.getText().toString());
                user2 = new User(userName2.getText().toString());
                chooseRestaurant();
            }
        });

    }

    protected void chooseRestaurant(){
        for (int i = 1; i <= 3; i++){
            setUpRound(i);
            startRound(i);
        }
    }


    protected void setUpRound(int roundNum){
        setContentView(R.layout.round_title);
        TextView title = findViewById(R.id.title);
        TextView uName = findViewById(R.id.round_username);

        switch(roundNum){
            case 1:
                title.setText("Rate each type of cuisine");
               // uName.setText(userName);
                break;
            case 2:
                title.setText("Welcome to round 2");
              //  uName.setText(userName);
                break;
            case 3:
                title.setText("Welcome to round 3");
                //uName.setText(userName);
                break;
            default:
                Toast.makeText(this, "Result is ??", Toast.LENGTH_SHORT).show();
                return;
        }

    }


    protected void startRound(int roundNum) {
        startRoundButton = findViewById(R.id.start_round_button);
        startRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //doRound(user1);
                //doRound(user2);
            }
        });
    }

    protected void doRound(User curUser, int roundNum){
        setContentView(R.layout.round_step);

        switch(roundNum){
            case 1:
                // uName.setText(userName);
                break;
            case 2:

                //  uName.setText(userName);
                break;
            case 3:

                //uName.setText(userName);
                break;
            default:
                Toast.makeText(this, "Result is ??", Toast.LENGTH_SHORT).show();
                return;
        }

    }


    protected int getChoice() {

        radioChoiceGroup = findViewById(R.id.option_buttons);

        int selectedId = radioChoiceGroup.getCheckedRadioButtonId();
        radioChoiceButton = findViewById(selectedId);

        String strChoice = radioChoiceButton.getText().toString();
        return Integer.parseInt(strChoice);
    }







}

