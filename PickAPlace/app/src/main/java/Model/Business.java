package Model;

public class Business {
    String image_url;
    String name;
    String url;

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

}
