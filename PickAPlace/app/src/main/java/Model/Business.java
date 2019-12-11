package Model;

public class Business {
    String name;
    String url;

    public String getName(){
        return this.name;
    }

    public String getUrl(){
        return this.url;
    }

    public String toString(){
        return "Name: "+name+ " "+"url: "+url;
    }

}
