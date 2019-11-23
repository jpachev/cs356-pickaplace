package Model;

public class CuisineRating {
    public CuisineRating(){}

    public CuisineRating(String t, int r) {
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

    @Override
    public String toString() {
        return "CuisineRating{" +
                "type='" + type + '\'' +
                ", rating=" + rating +
                '}';
    }
}
