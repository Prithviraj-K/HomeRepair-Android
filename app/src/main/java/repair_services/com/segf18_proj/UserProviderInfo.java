package repair_services.com.segf18_proj;

public class UserProviderInfo {
    String address, phoneNum,company, servDescription;
    boolean isLicensed;
    public UserProviderInfo(String address, String phoneNum, String company, String servDescription, boolean isLicensed){
        this.address = address;
        this.phoneNum = phoneNum;
        this.company = company;
        this.servDescription = servDescription;
        this.isLicensed = isLicensed;
    }
    public UserProviderInfo(){

    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getCompany() {
        return company;
    }

    public String getServDescription() {
        return servDescription;
    }

    public boolean isLicensed() {
        return isLicensed;
    }
}
