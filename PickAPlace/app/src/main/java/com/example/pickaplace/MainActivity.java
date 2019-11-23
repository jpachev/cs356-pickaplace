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

import java.util.ArrayList;

import Model.CuisineRating;
import Model.User;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    EditText userName1, userName2;
    Button startButton;
    Button startRoundButton, nextButton;
    User user1, user2;
    User curUser;
    private RadioGroup radioChoiceGroup;
    private RadioButton radioChoiceButton;
    private int roundNum;
    protected int optionNum;
    protected final int NUM_CUISINE_OPTIONS = 4;
    protected ArrayList<String> cuisineTypes = new java.util.ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTempCuisineTypes();
        userName1 = findViewById(R.id.user1);
        userName2 = findViewById(R.id.user2);

        startButton = findViewById(R.id.start_button);
        roundNum = 1;
        optionNum = 0;



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user1 = new User(userName1.getText().toString());
                user2 = new User(userName2.getText().toString());
                chooseRestaurant();
            }
        });



    }


    protected void setTempCuisineTypes() {
        //Will probably change this later
        cuisineTypes.add("Mexican");
        cuisineTypes.add("Chinese");
        cuisineTypes.add("American");
        cuisineTypes.add("Indian");
    }


    protected void chooseRestaurant() {

        setUpRound(1, user1.getName());
        startRound(user1);
    }


    protected void setUpRound(int roundNum, String userName) {
        setContentView(R.layout.round_title);
        TextView title = findViewById(R.id.title);
        TextView uName = findViewById(R.id.round_username);

        switch (roundNum) {
            case 1:
                title.setText("Rate each type of cuisine");
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
                Toast.makeText(this, "Result is ??", Toast.LENGTH_SHORT).show();
                return;
        }

    }

    protected void startRound(final User curUser) {
        startRoundButton = findViewById(R.id.start_round_button);
        startRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.round_step);
                radioChoiceGroup = findViewById(R.id.option_buttons);
                doRounds(curUser, 1);
                //onRadioButtonClicked();
            }
        });
    }

    protected void doRoundOne(final User curUser) {
        if (this.optionNum > NUM_CUISINE_OPTIONS - 1) {
            finishRoundOne(curUser);
            return;
        }


        final TextView title = findViewById(R.id.option_title);
        radioChoiceGroup.clearCheck();
        title.setText(cuisineTypes.get(optionNum));

        //int userChoice = getChoice();
        int userChoice = 1;
        //CuisineRating userRating = new CuisineRating(cuisineTypes.get(optionNum), userChoice);
        //curUser.saveRating(userRating);
    }


    protected void finishRoundOne(User curUser) {
        if (curUser.equals(user1)) {
            if(user1.getRatings().size() <= 0)
                Toast.makeText(this,"Size is zero", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < user1.getRatings().size(); i++){
                String rating = user1.getRatings().get(i).toString();
                Toast.makeText(this,rating , Toast.LENGTH_SHORT).show();
            }
            setUpRound(1, user2.getName());
            startRound(user2);
            return;
        }
        //Toast.makeText(this, "Result is ??", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.round_result);
    }


    protected void doRounds(final User curUser, final int roundNum) {

        optionNum = 0;

        switch (roundNum) {
            case 1:
                doRoundOne(user1);
                nextButton = findViewById(R.id.next_button);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incrementOptionNum();
                        doRoundOne(curUser);
                        //Toast.makeText(MainActivity.this, newOptionNum.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
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


    protected void incrementRoundNum() {
        this.roundNum++;
    }

    protected void incrementOptionNum() {
        this.optionNum++;
    }


    protected void setChoice(User user, String strRating, String option){
        int r = Integer.parseInt(strRating);
        CuisineRating choice = new CuisineRating(option, r);
        user.saveRating(choice);
    }





    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String str = "";

        // Check which radio button was clicked
       switch(view.getId()) {
            case R.id.one:
                str = "1";
                break;
            case R.id.two:
                str = "2";
                break;
            case R.id.three:
                str = "3";
                break;
            case R.id.four:
                str = "4";
                break;
            case R.id.five:
                str = "5";
                break;
        }

        setChoice(user1, str, "Mexican");

    }


    protected int getChoice() {



        int selectedId = radioChoiceGroup.getCheckedRadioButtonId();
        radioChoiceButton = findViewById(selectedId);

        String strChoice = radioChoiceButton.getText().toString();
        return parseInt(strChoice);
    }




}

