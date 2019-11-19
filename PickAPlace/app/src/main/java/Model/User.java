package Model;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    public User(String uName) {
        name = uName;
    }

    private String name;
    private ArrayList<CuisineRating> cuisineRatings = new ArrayList<>();

    public String getName() {
        return this.name;
    }

    public void saveRating(CuisineRating userRating){
        this.cuisineRatings.add(userRating);
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
