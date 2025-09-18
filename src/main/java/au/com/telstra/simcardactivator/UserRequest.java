package au.com.telstra.simcardactivator;

import org.apache.catalina.User;

public class UserRequest {
    private String iccid;
    private String customerEmail;

    public UserRequest(){}

    public UserRequest(String iccid, String customerEmail) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
