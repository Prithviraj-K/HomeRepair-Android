package repair_services.com.segf18_proj;

public class Service {
    public String servName, servRate;

    public Service(String servName, String servRate){
        this.servName = servName;
        this.servRate = servRate;
    }

    public Service(){

    }

    public String getServName() {
        return servName;
    }

    public void setServName(String name){
        servName = name;
    }

    public String getServRate() {
        return servRate;
    }
    public void setRate(String newRate){
        servRate = newRate;
    }
}
