package repair_services.com.segf18_proj;

public class ProviderAvailability {
    public String time, userID;

    public ProviderAvailability(String time, String userID){
        this.time = time;
        this.userID = userID;
    }
    public ProviderAvailability(){}

    public String getTime() { return time; }
    public String getUserID() { return userID; }

}
