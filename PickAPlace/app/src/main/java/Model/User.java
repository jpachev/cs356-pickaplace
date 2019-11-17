package Model;

import java.util.ArrayList;

public class User {
    public User(String uName) {
        name = uName;
    }

    private String name;
    private ArrayList<CuisineType> cuisineRatings = new ArrayList<>();

    public String getName() {
        return this.name;
    }

}
