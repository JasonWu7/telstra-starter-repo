package au.com.telstra.simcardactivator.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SimInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String iccid;
    private String customerEmail;
    private boolean active;

    protected SimInfo() {}

    public SimInfo(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return String.format(
                "Record[id=%d, iccid='%s', customer email='%s', is active='%s']",
                id, iccid, customerEmail, active);
    }

}
