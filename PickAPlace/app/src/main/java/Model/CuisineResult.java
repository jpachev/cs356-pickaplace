package Model;

import java.util.ArrayList;

public class CuisineResult {
    public CuisineResult(){}

    public CuisineResult(ArrayList<CuisineType> one, ArrayList<CuisineType> two) {
        UserOneList = one;
        UserTwoList = two;
    }

    ArrayList<CuisineType> UserOneList = new ArrayList<>();
    ArrayList<CuisineType> UserTwoList = new ArrayList<>();
    private String result;

    public String evaluateResult() {
        ArrayList<Integer> averages = new ArrayList<>();
        int currentMax = 0;
        for (int i = 0; i < UserOneList.size(); ++i) {
            int currentRatingAverage = UserOneList.get(i).getRating() + UserTwoList.get(i).getRating() / 2;
            if (currentRatingAverage > currentMax) {
                currentMax = currentRatingAverage;
                result = UserOneList.get(i).getType();
            }
        }
        return result;
    }
}
