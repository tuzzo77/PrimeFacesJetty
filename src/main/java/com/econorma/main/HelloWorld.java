package com.econorma.main;
 

import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class HelloWorld {
 
    private String firstName = "";
    private String lastName = "";
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String showGreeting() {
        return "Welcome Home my mate" + " " + firstName + " " + lastName + "!";
    }
}