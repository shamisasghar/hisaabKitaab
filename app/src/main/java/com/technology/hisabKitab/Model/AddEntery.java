package com.technology.hisabKitab.Model;

public class AddEntery  {

   private String payed_person_name;
    private String date;
    private String total_amount;
    private String each_amount;
    private String remarks;
    private String selected_person;

    public String getPayed_person_name() {
        return payed_person_name;
    }

    public void setPayed_person_name(String payed_person_name) {
        this.payed_person_name = payed_person_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getEach_amount() {
        return each_amount;
    }

    public void setEach_amount(String each_amount) {
        this.each_amount = each_amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSelected_person() {
        return selected_person;
    }

    public void setSelected_person(String selected_person) {
        this.selected_person = selected_person;
    }


    public AddEntery(String payed_person_name, String total_amount, String each_amount, String remarks, String selected_person,String date) {
        this.payed_person_name = payed_person_name;
        this.total_amount = total_amount;
        this.date = date;
        this.each_amount = each_amount;
        this.remarks = remarks;
        this.selected_person = selected_person;
    }

    public AddEntery()
    {

    }




}
