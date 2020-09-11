package com.example.finalmtenant;

import android.service.autofill.FillEventHistory;

public class Histories {

    String name;
    String pNo;
    String amount;
    String mode;

    public Histories() {
    }

    public Histories(String name, String pNo, String amount, String mode) {

        this.name = name;
        this.pNo = pNo;
        this.amount = amount;
        this.mode = mode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getpNo() {
        return pNo;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
