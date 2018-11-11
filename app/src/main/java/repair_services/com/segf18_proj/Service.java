package repair_services.com.segf18_proj;

public class Service {
    public String servName, hourlypay;

    public Service(String servName, String hourlypay){
        this.servName = servName;
        this.hourlypay = hourlypay;
    }

    public Service(){

    }

    public String getServName() {
        return servName;
    }

    public String getHourlypay() {
        return hourlypay;
    }
}
