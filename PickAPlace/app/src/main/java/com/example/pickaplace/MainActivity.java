package com.example.pickaplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
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
    protected String curChoice;
    protected String curOption;
    protected final int NUM_CUISINE_OPTIONS = 5;
    protected final int NUM_ROUND_TWO_OPTIONS = 2;
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

        userName1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        userName2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user1 = new User(userName1.getText().toString());
                user2 = new User(userName2.getText().toString());
                chooseRestaurant();
            }
        });
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                roundTwoTypes.add("American Option 1");
                roundTwoTypes.add("American Option 2");
                break;
            case("Indian"):
                roundTwoTypes.add("Indian Option 1");
                roundTwoTypes.add("Indian Option 2");
                break;
            case("Japanese"):
                roundTwoTypes.add("Japanese Option 1");
                roundTwoTypes.add("Japanese Option 2");
                break;
        }
    }


    protected void chooseRestaurant() {

        setUpRound(1, user1.getName());
        startRound(user1);
    }


    protected void setUpRound(int rNum, String userName) {
        setContentView(R.layout.round_title);
        TextView title = findViewById(R.id.title);
        TextView uName = findViewById(R.id.round_username);

        switch (rNum) {
            case 1:
                title.setText("Rate each type of cuisine");
                uName.setText(userName);
                break;
            case 2:
                title.setText("Welcome to round 2");
                Log.d("round2", "set up round2 title");
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

        Log.d("round2", "rNum: "+rNum);

    }

    protected void startRound(final User user) {
        curUser = user;
        startRoundButton = findViewById(R.id.start_round_button);
        startRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.round_step);
                optionNum = 0;
                roundNum = 1;
                resetRadio();
                doRounds(user, roundNum);
                //onRadioButtonClicked();
            }
        });
    }

    protected void resetRadio()
    {
        radioChoiceGroup = findViewById(R.id.option_buttons);
        radioChoiceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               // Log.d("checkedChange","checkedid = "+checkedId);
            }
        });
    }

    protected void doRounds(final User user, final int rNum) {
        optionNum = 0;
        setContentView(R.layout.round_step);
        final TextView title = findViewById(R.id.option_title);
        nextButton = findViewById(R.id.next_button);
        if (!user.equals(curUser)) curUser=user;
        switch (rNum) {
            case 1:
                title.setText(cuisineTypes.get(optionNum));
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //setChoice(curUser, curOption);
                        Log.d("rButton", "clicked next");
                        onRadioButtonClicked();
                        incrementOptionNum();
                        doRoundOne(user);
                        callRadioButtonClear(radioChoiceGroup);
                        //Toast.makeText(MainActivity.this, "Moved to option "+optionNum,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                setRoundTwoTypes(currentRes);
                title.setText(roundTwoTypes.get(optionNum));
                Log.d("round2", "title "+roundTwoTypes.get(optionNum));
                nextButton.setOnClickListener(null);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClicked();
                        incrementOptionNum();
                        Log.d("round2", "clicked nextButton 2");
                        doRoundTwo(user);
                        callRadioButtonClear(radioChoiceGroup);
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
        setChoice(curUser, curOption);
        if (this.optionNum > NUM_CUISINE_OPTIONS - 1) {
            finishRoundOne(user);
            return;
        }

        final TextView title = findViewById(R.id.option_title);
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


        //Log.d("arraysize", "size of user1 "+user1.getRatings().size());
        //Log.d("arraysize", "size of user2 "+user2.getRatings().size());

        for (int i = 0; i < user1.getRatings().size(); i++)
            //Log.d("ratings","user 1 rating: "+user1.getRatings().get(i).toString());

        for (int j = 0; j < user2.getRatings().size(); j++)
            //Log.d("ratings", "user2 rating: "+user2.getRatings().get(i).toString());


        showResult(user1.getRatings(), user2.getRatings());


    }

    protected void doRoundTwo(final User user){
        curUser = user;
       // Log.d("uname",user.getName());
        //Log.d("curOpt",curOption);
        setChoice(curUser, curOption);
        if (this.optionNum > NUM_ROUND_TWO_OPTIONS - 1) {
            finishRoundTwo(user);
            return;
        }

        final TextView title = findViewById(R.id.option_title);
        title.setText(roundTwoTypes.get(optionNum));
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


       /* Log.d("arraysize", "size of user1 "+user1.getRatings().size());
        Log.d("arraysize", "size of user2 "+user2.getRatings().size());

        for (int i = 0; i < user1.getRatings().size(); i++)
            Log.d("ratings","user 1 rating: "+user1.getRatings().get(i).toString());

        for (int i = 0; i < user2.getRatings().size(); i++)
            Log.d("ratings", "user2 rating: "+user2.getRatings().get(i).toString());*/

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

        Button nextRoundButton = findViewById(R.id.next_round_button);
        nextRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementRoundNum();
                setUpRound(2, user1.getName());
                doRounds(user1, 2);
                //Toast.makeText(MainActivity.this, newOptionNum.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void incrementRoundNum() {
        this.roundNum++;
    }

    protected void incrementOptionNum() {
        this.optionNum++;
    }


    protected void setChoice(User user, String option){
        int r = user.getRating(option);
        CuisineRating choice = new CuisineRating(option, r);
        user.saveRating(choice);
        callRadioButtonClear(radioChoiceGroup);
        //Log.d("rButton", "Cleared buttons");
    }

    protected void setCurChoice(User user, String strRating, String option){
        int r = Integer.parseInt(strRating);
       // Log.d("user cur",user.getName());
        user.saveTempRating(option, r);
        //Log.d("rButton", "Saving cur choice "+strRating+ "for "+option);
    }

    public String getCurOption() {
        switch (roundNum){
            case 1:
                return cuisineTypes.get(optionNum);
            case 2:
                return roundTwoTypes.get(optionNum);
        }

        return "";
    }

    public void onRadioButtonClicked() {
        // Is the button now checked?
        String str = "";
        curOption = getCurOption();
        resetRadio();
        int checkedId = radioChoiceGroup.getCheckedRadioButtonId();
       // Log.d("rButton", "checkedId: "+checkedId);
        // Check which radio button was clicked
       switch(checkedId) {
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

        curChoice = str;
        setCurChoice(curUser,str, curOption);
    }

    // Method to clear radio group
    public void callRadioButtonClear(final RadioGroup radiobuttongroup)
    {
        new CountDownTimer(5,1){
            public void onTick(long blah){  return;}
            public void onFinish() {
                //Log.d("rButton", "timer called");
                radiobuttongroup.clearCheck();           // ClearCheck making all buttons in the radio group to uncheck
            }
        }.start();
    }

}

