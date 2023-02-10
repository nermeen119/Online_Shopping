package com.example.online_shopping.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "all_product_table")
public class AllProductModel {

    String title;
    int image;
    double price;
    boolean foundPro=true;
    int quntity;
    int purchaseCount = 0;
    @PrimaryKey(autoGenerate = true)
    Integer id;

    public AllProductModel() {
    }
    public AllProductModel(String title, int image, double price, int quntity) {
        this.title = title;
        this.price = price;
        this.quntity = quntity;
        this.image=image;
    }


    public int getQuntity() {
        return quntity;
    }

    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }

    public boolean isFoundPro() {
        return foundPro;
    }

    public void setFoundPro(boolean foundPro) {
        this.foundPro = foundPro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
}

