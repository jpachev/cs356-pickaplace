package com.example.pickaplace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.CuisineRating;
import Model.CuisineResult;
import Model.User;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    protected EditText userName1, userName2;
    protected Button startButton;
    protected Button startRoundButton, nextButton;
    protected User user1, user2;
    User curUser;
    private RadioGroup radioChoiceGroup;
    private RadioButton radioChoiceButton;
    private int roundNum;
    protected int optionNum;
    protected String currentRes;
    protected final int NUM_CUISINE_OPTIONS = 4;
    protected ArrayList<String> cuisineTypes = new java.util.ArrayList<>();
    protected ArrayList<String> roundTwoTypes = new java.util.ArrayList<>();

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
        cuisineTypes.add("Japanese");
    }

    protected void setRoundTwoTypes(String r1Res){
        switch(r1Res){
            case("Mexican"):
                roundTwoTypes.add("American Mexican");
                roundTwoTypes.add("Traditional Mexican");
                break;
            case("Chinese"):
                roundTwoTypes.add("American Chinese");
                roundTwoTypes.add("Traditional Chinese");
                break;
            case("American"):
                roundTwoTypes.add("Option 1");
                roundTwoTypes.add("Option 2");
                break;
            case("Indian"):
                roundTwoTypes.add("Option 1");
                roundTwoTypes.add("Option 2");
                break;
            case("Japanese"):
                roundTwoTypes.add("Option 1");
                roundTwoTypes.add("Option 2");
                break;
        }
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

    protected void startRound(final User user) {
        curUser = user;
        startRoundButton = findViewById(R.id.start_round_button);
        startRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.round_step);
                radioChoiceGroup = findViewById(R.id.option_buttons);
                doRounds(user, roundNum);
                //onRadioButtonClicked();
            }
        });
    }

    protected void doRounds(final User user, final int rNum) {

        optionNum = 0;
        roundNum = 1;
        TextView title = findViewById(R.id.option_title);
        switch (rNum) {
            case 1:
                title.setText(cuisineTypes.get(optionNum));
                nextButton = findViewById(R.id.next_button);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incrementOptionNum();
                        doRoundOne(user);
                        //Toast.makeText(MainActivity.this, newOptionNum.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                title.setText(cuisineTypes.get(optionNum));
                nextButton = findViewById(R.id.next_button);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incrementOptionNum();
                        doRoundTwo(user, currentRes);
                        //Toast.makeText(MainActivity.this, newOptionNum.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 3:

                //uName.setText(userName);
                break;
            default:
                Toast.makeText(this, "Result is ??", Toast.LENGTH_SHORT).show();
                return;
        }

    }


    protected void doRoundOne(final User user) {
        curUser = user;
        if (this.optionNum > NUM_CUISINE_OPTIONS - 1) {
            finishRoundOne(user);
            return;
        }

        final TextView title = findViewById(R.id.option_title);
        radioChoiceGroup.clearCheck();
        title.setText(cuisineTypes.get(optionNum));
    }


    protected void finishRoundOne(User user) {
        curUser = user;
        if (user.equals(user1)) {
            setUpRound(1, user2.getName());
            curUser = user2;
            startRound(user2);
            return;
        }

        if (user2.getRatings().size() <= 0){
            Toast.makeText(this, "No ratings for user2", Toast.LENGTH_SHORT).show();
             return;
        }


        Log.d("arraysize", "size of user1 "+user1.getRatings().size());
        Log.d("arraysize", "size of user2 "+user2.getRatings().size());

        for (int i = 0; i < user1.getRatings().size(); i++)
            Log.d("ratings","user 1 rating: "+user1.getRatings().get(i).toString());

        for (int i = 0; i < user2.getRatings().size(); i++)
            Log.d("ratings", "user2 rating: "+user2.getRatings().get(i).toString());

        showResult(user1.getRatings(), user2.getRatings());
    }

    protected void doRoundTwo(final User user, String res){
        curUser = user;
        setRoundTwoTypes(res);
        if (this.optionNum > NUM_CUISINE_OPTIONS - 1) {
            finishRoundTwo(user);
            return;
        }

        final TextView title = findViewById(R.id.option_title);
        radioChoiceGroup.clearCheck();
        title.setText(cuisineTypes.get(optionNum));
    }

    protected void finishRoundTwo(User user){
        curUser = user;
        if (user.equals(user1)) {
            setUpRound(2, user2.getName());
            curUser = user2;
            startRound(user2);
            return;
        }

        if (user2.getRatings().size() <= 0){
            Toast.makeText(this, "No ratings for user2", Toast.LENGTH_SHORT).show();
            return;
        }


        Log.d("arraysize", "size of user1 "+user1.getRatings().size());
        Log.d("arraysize", "size of user2 "+user2.getRatings().size());

        for (int i = 0; i < user1.getRatings().size(); i++)
            Log.d("ratings","user 1 rating: "+user1.getRatings().get(i).toString());

        for (int i = 0; i < user2.getRatings().size(); i++)
            Log.d("ratings", "user2 rating: "+user2.getRatings().get(i).toString());

        showResult(user1.getRatings(), user2.getRatings());
    }

    protected void showResult(ArrayList<CuisineRating> one, ArrayList<CuisineRating> two){
        CuisineResult result = new CuisineResult(one, two);
        String stringRes = result.evaluateResult();
        //Toast.makeText(this, "Result is ??", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.round_result);
        TextView resTitle = findViewById(R.id.result);
        resTitle.setText(stringRes);
        currentRes = stringRes;
        incrementRoundNum();
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
        TextView option = findViewById(R.id.option_title);
        String curOption = option.getText().toString();

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

        setChoice(curUser, str, curOption);

    }

}

