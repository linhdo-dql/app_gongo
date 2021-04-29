package com.example.app_go_go.Objects;

public class Accounts {
    public String accName, accPass, accMail;
    public boolean sttActivity;

    public Accounts() {
    }

    public Accounts(String accName, String accPass, String accMail, boolean sttActivity) {
        this.accName = accName;
        this.accPass = accPass;
        this.accMail = accMail;
        this.sttActivity = sttActivity;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccPass() {
        return accPass;
    }

    public void setAccPass(String accPass) {
        this.accPass = accPass;
    }

    public boolean isSttActivity() {
        return sttActivity;
    }

    public void setSttActivity(boolean sttActivity) {
        this.sttActivity = sttActivity;
    }

    public String getAccMail() {
        return accMail;
    }

    public void setAccMail(String accMail) {
        this.accMail = accMail;
    }
}
