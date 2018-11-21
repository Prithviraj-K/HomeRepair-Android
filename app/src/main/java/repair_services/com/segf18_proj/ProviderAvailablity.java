package repair_services.com.segf18_proj;

public class ProviderAvailablity {
    public String serviceName, dayOfWeek, serviceTime;

    public ProviderAvailablity (String serviceName, String dayOfWeek, String time){
        this.dayOfWeek = dayOfWeek;
        this.serviceTime = time;
        this.serviceName = serviceName;
    }
    public ProviderAvailablity(){

    }

    public String getServiceName() {
        return serviceName;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public String getserviceTime(){
        return serviceTime;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public void setserviceTime(String time) {
        this.serviceTime = time;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
