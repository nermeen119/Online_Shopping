package com.example.online_shopping.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_admin_table")
public class CartAdminModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String itemTitle;
    private int itemImage;
    private double totalPrice;
    private int quantity;
    String date;
    String userName;
    String rate;

    public CartAdminModel() {
    }


    public CartAdminModel(String itemTitle, int itemImage, double totalPrice, int quantity, String date, String userName, String rate) {
        this.itemTitle = itemTitle;
        this.itemImage = itemImage;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.userName = userName;
        this.date = date;
        this.rate=rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
