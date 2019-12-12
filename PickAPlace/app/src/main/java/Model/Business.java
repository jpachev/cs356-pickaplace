package Model;

public class Business {
    String image_url;
    String name;
    String url;
    Coordinates coordinates;

    public String getName(){
        return this.name;
    }

    public String getUrl(){
        return this.url;
    }

    public String getImage_url(){
        return this.image_url;
    }

    public String toString(){
        return "ImageUrl: "+ image_url+ " Name: "+name+" url: "+url;
    }

    public float getLatitude(){
        return this.coordinates.latitude;
    }

    public float getLongitude(){
        return this.coordinates.longitude;
    }

}
