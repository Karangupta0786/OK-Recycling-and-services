package com.okjunkstore.beta.Helperclass.HomeAdapter;

public class ItemsData {
    String aboutItem, stock, item,image, condition, dat, tim, uniqueKey;

    public ItemsData(){
    }

    public ItemsData(String aboutItem,String stock,String item, String image,String condition, String dat, String tim, String uniqueKey) {
        this.aboutItem = aboutItem;
        this.stock = stock;
        this.item = item;
        this.image = image;
        this.condition = condition;
        this.dat = dat;
        this.tim = tim;
        this.uniqueKey = uniqueKey;
    }

    public String getAboutItem() {
        return aboutItem;
    }

    public void setAboutItem(String aboutItem) {
        this.aboutItem = aboutItem;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}