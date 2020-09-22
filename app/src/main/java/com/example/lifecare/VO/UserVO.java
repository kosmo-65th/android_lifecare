package com.example.lifecare.VO;

import java.sql.Timestamp;

/**
 * Created by psn on 2018-01-18.
 */

public class UserVO {
    private UserVO(){}
    private static UserVO userVO= new UserVO();
    public static UserVO getInstance(){
        return userVO;
    }

    private String id="";
    private String customer_echeck="";
    private String enabled="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getCustomer_echeck() {
        return customer_echeck;
    }

    public void setCustomer_echeck(String customer_echeck) {
        this.customer_echeck = customer_echeck;
    }
}