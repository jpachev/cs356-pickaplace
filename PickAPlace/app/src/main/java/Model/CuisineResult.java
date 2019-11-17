package Model;

import java.util.ArrayList;

public class CuisineResult {
    public CuisineResult(){}

    public CuisineResult(ArrayList<CuisineType> one, ArrayList<CuisineType> two) {
        userOneList = one;
        userTwoList = two;
    }

    ArrayList<CuisineType> userOneList = new ArrayList<>();
    ArrayList<CuisineType> userTwoList = new ArrayList<>();
    private String result;

    public String evaluateResult() {
        ArrayList<Integer> averages = new ArrayList<>();
        int currentMax = 0;
        for (int i = 0; i < userOneList.size(); ++i) {
            int currentRatingAverage = userOneList.get(i).getRating() + userTwoList.get(i).getRating() / 2;
            if (currentRatingAverage > currentMax) {
                currentMax = currentRatingAverage;
                result = userOneList.get(i).getType();
            }
        }
        return result;
    }
}
