package Model;

import java.util.ArrayList;

public class CuisineResult {
    public CuisineResult(){}

    public CuisineResult(ArrayList<CuisineRating> one, ArrayList<CuisineRating> two) {
        userOneList = one;
        userTwoList = two;
    }

    ArrayList<CuisineRating> userOneList = new ArrayList<>();
    ArrayList<CuisineRating> userTwoList = new ArrayList<>();
    private String result;

    public String evaluateResult() {
        int currentMax = 0;
        if (userOneList.size() != userTwoList.size()){
            result = "Error";
            return result;
        }
        for (int i = 0; i < userOneList.size()-1; i++) {
            int currentRatingAverage = (userOneList.get(i).getRating() + userTwoList.get(i).getRating()) / 2;
            if (currentRatingAverage > currentMax) {
                currentMax = currentRatingAverage;
                result = userOneList.get(i).getType();
            }
        }
        return result;
    }
}
