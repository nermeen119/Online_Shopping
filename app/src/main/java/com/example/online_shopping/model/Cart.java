package com.example.online_shopping.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.online_shopping.database.CartDataBase;

@Entity(tableName = "baskets_tables")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String itemTitle;
    private int itemImage;
    private double price;
    private int quantity;
    String userName;

    public Cart() {
    }

    public Cart(String itemTitle, int itemImage, double price, int quantity, String userName) {
        this.itemTitle = itemTitle;
        this.itemImage = itemImage;
        this.price = price;
        this.quantity = quantity;
        this.userName = userName;
    }

    public Cart(String itemTitle, int itemImage, double price, String userName) {
        this.itemTitle = itemTitle;
        this.itemImage = itemImage;
        this.price = price;
        this.userName = userName;
    }


    public Cart(String itemTitle, int itemImage, double price) {

        this.itemTitle = itemTitle;
        this.itemImage = itemImage;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
