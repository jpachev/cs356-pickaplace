package Model;

public class CuisineType {
    public CuisineType(){}

    public CuisineType(String t, int r) {
        type = t;
        rating = r;
    }

    private String type;
    private int rating;

    int getRating() {
        return rating;
    }

    String getType() {
        return type;
    }
}
