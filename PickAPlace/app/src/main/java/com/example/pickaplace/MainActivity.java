package com.example.pickaplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
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

import com.google.gson.Gson;

/*import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;*/
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import Model.Business;
import Model.BusinessResponse;
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
    protected final int NUM_CUISINE_OPTIONS = 4;
    protected final int NUM_ROUND_TWO_OPTIONS = 2;
    protected final int NUM_ROUND_THREE_OPTIONS = 2;
    protected final String API_HOST = "https://api.yelp.com";
    protected final String SEARCH_PATH = "/v3/businesses/search";
    protected final String BUSINESS_PATH = "/v3/businesses/";  // Business ID will come after slash.
    private final String API_KEY = "17XERKHngT8aqZA2fQRbVJ7peWIrsOTbbfwgLpDyA16I7SrUBVccD7neWqWpKOlH4VgdeuYwX32mCwJdQBoRmakAAMi03hnQhoPBQ6a0dUAxJ7bQpsdSiSmh5tHhXXYx";
    protected ArrayList<String> cuisineTypes = new java.util.ArrayList<>();
    protected ArrayList<String> roundTwoTypes = new java.util.ArrayList<>();
    protected ArrayList<String> roundThreeTypes = new java.util.ArrayList<>();
    public Business[] restaurants;

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
        }
    }


    protected void setRoundThreeTypes(String r2Res){
        Log.d("round3", "r2res = "+r2Res);
        switch(r2Res){
            case("American Mexican"):
                roundThreeTypes.add("American Mexican");
                break;
            case("Traditional Mexican"):
                roundThreeTypes.add("Traditional Mexican");
                break;
        }
    }


    protected void chooseRestaurant() {

        roundNum = 1;
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
                title.setText("Rate each restaurant");
                Log.d("round2", "set up round2 title");
                uName.setText(userName);
                break;
            case 3:
                title.setText("Rate each restaurant");
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
                resetRadio();
                doRounds(user, roundNum);
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
        Log.d("round3", "Round num = "+roundNum);
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
                title.setText(restaurants[optionNum].getName());
                nextButton.setOnClickListener(null);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClicked();
                        incrementOptionNum();
                        doRoundTwo(user);
                        callRadioButtonClear(radioChoiceGroup);
                    }
                });
                break;
            default:
                Toast.makeText(this, "Result is ??", Toast.LENGTH_SHORT).show();
                return;
        }

    }



    protected void callAPI(){
        RetrieveBusinessTask businessTask = new RetrieveBusinessTask();
        businessTask.mainActivity = MainActivity.this;
        businessTask.execute();
        // restaurants = businessTask.getResults();

        if (restaurants != null){
            Log.d("datashare", "Number of restaurants "+restaurants.length);
        }
        Log.d("round3", "No restaurants");
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

       /* for (int i = 0; i < user1.getRatings().size(); i++)
            //Log.d("ratings","user 1 rating: "+user1.getRatings().get(i).toString());

        for (int j = 0; j < user2.getRatings().size(); j++)
            //Log.d("ratings", "user2 rating: "+user2.getRatings().get(i).toString());*/


        showResult(user1.getRatings(), user2.getRatings());


    }



  /*  protected void doRoundTwo(final User user){
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

        showResult(user1.getRatings(), user2.getRatings());
    }*/

    protected void doRoundTwo(final User user){
        curUser = user;
        // Log.d("uname",user.getName());
        //Log.d("curOpt",curOption);
        setChoice(curUser, curOption);
        if (this.optionNum > restaurants.length-1) {
            finishRoundTwo(user);
            return;
        }

        final TextView title = findViewById(R.id.option_title);
        title.setText(restaurants[optionNum].getName());
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

        final Button nextRoundButton = findViewById(R.id.next_round_button);
        nextRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("round3", "round num: "+roundNum);
                nextRoundButton.setEnabled(false);
                callAPI();
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
        Log.d("rButton", "Saving cur choice "+strRating+ "for "+option);
    }

    public String getCurOption() {
        switch (roundNum){
            case 1:
                return cuisineTypes.get(optionNum);
            case 2:
                return restaurants[optionNum].getName();
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


    protected String buildURL(String result){
        String req = API_HOST+SEARCH_PATH+"?term="+result+"&location=Provo&limit=5";
        return req;
    }

    protected void handleAPIResults(){
        Button nextRoundButton = findViewById(R.id.next_round_button);
        nextRoundButton.setEnabled(true);
        incrementRoundNum();
        setUpRound(roundNum, user1.getName());
        startRound(user1);
    }


    class RetrieveBusinessTask extends AsyncTask<String, Void, Business[]> {

        private Exception exception;


        public MainActivity mainActivity = null;

        protected Business[] doInBackground(String... urls) {
            Business[] results = null;
            //HttpResponse response = null;
            try {
                /*
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                //Obviously gonna update this
                request.setHeader("Authorization", "Bearer " + API_KEY);
                String url = buildURL(mainActivity.currentRes);
                Log.d("round3", "url :"+url);
                request.setURI(new URI(url));
                response = client.execute(request);

                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);*/
                String urlString = buildURL(mainActivity.currentRes);
                URL url = new URL(urlString);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", "Bearer "+API_KEY);
                int responseCode = con.getResponseCode();
                Log.d("conn","GET Response Code :: " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer responseBody = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseBody.append(inputLine);
                }
                in.close();

                Log.d("round3", "Response body: "+responseBody);
                Gson gson = new Gson();
                BusinessResponse res = gson.fromJson(responseBody.toString(), BusinessResponse.class);

                int curNumB =  res.businesses.length;
                Log.d("round3", "# of businesses "+curNumB);
                for (int i = 0; i < res.businesses.length; i++){
                    Business curB = res.getBusinesses()[i];
                    Log.d("round3", curB.getName());
                }

                results = res.getBusinesses();

            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           // Log.d("round3", "Recieved response"+response.toString());

            return results;
        }

        @Override
        protected void onPostExecute(Business[] results) {
            super.onPostExecute(results);
            this.mainActivity.restaurants = results;
            handleAPIResults();
        }

    }
}





