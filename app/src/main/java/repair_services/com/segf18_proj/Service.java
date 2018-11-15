package repair_services.com.segf18_proj;

public class Service {
    public String servName, rate;

    public Service(String servName, String rate){
        this.servName = servName;
        this.rate = rate;
    }

    public Service(){

    }

    public String getServName() {
        return servName;
    }

    public void setServName(String name){
        name = servName;
    }

    public String getRate() {
        return rate;
    }
    public void setRate(String nrate){
        nrate = rate;
    }
}
