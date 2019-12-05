package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
    public User(String uName) {
        name = uName;
    }

    private String name;
    private ArrayList<CuisineRating> cuisineRatings = new ArrayList<>();
    private Map<String, Integer> tempCuisineRatings = new HashMap<>();
    private ArrayList<CuisineRating> roundTwoRatings = new ArrayList<>();
    public String getName() {
        return this.name;
    }

    public void saveRating(CuisineRating userRating){
        this.cuisineRatings.add(userRating);
    }

    public void saveTempRating(String option, int r){
        this.tempCuisineRatings.put(option, r);
    }

    public int getRating(String option){
        return this.tempCuisineRatings.get(option);
    }

    public ArrayList<CuisineRating> getRatings(){
        return this.cuisineRatings;
    }

    public void clearRatings(){
        this.cuisineRatings.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (!((User) o).getName().equals(this.getName()))
            return false;
        return true;
    }
}
