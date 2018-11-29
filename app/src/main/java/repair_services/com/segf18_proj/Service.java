package repair_services.com.segf18_proj;

import java.io.Serializable;

public class Service{
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
    public void setservRate(String newRate){
        servRate = newRate;
    }
}
