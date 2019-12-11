package Model;

public class BusinessResponse {
    public int total;
    public Business[] businesses;
    public Business[] getBusinesses(){
        return this.businesses;
    }
}
