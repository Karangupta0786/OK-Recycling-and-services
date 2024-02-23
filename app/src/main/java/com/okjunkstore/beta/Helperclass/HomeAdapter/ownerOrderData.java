package com.okjunkstore.beta.Helperclass.HomeAdapter;

public class ownerOrderData {
    String owner;
    String contact;
    String title;
    String image;
    String date;
    String time;
    String key;
    String item;
    String TandC;
    String Add;

    public ownerOrderData() {
    }


    public ownerOrderData(String owner, String contact, String title, String image, String date, String time, String key, String item, String TandC, String Add) {
        this.owner = owner;
        this.contact = contact;
        this.title = title;
        this.image = image;
        this.date = date;
        this.time = time;
        this.key = key;
        this.item = item;
        this.TandC = TandC;
        this.Add = Add;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTandC() {
        return TandC;
    }

    public void setTandC(String tandC) {
        TandC = tandC;
    }

    public String getAdd() {
        return Add;
    }

    public void setAdd(String add) {
        Add = add;
    }

}
