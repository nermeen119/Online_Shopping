package com.example.online_shopping.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "product_table")
public class product {

    String title;
    int image;
    double price;
    boolean foundPro=true;
    int quntity;
    int purchaseCount = 0;
    @PrimaryKey(autoGenerate = true)
     Integer id;
    String imageproduct;
    @ColumnInfo(name = "mylist_product")
    private ArrayList<product> productArrayList;

    public product() {
    }

    public product(String title, int image, double price) {
        this.title = title;
        this.image = image;
        this.price = price;
    }

    public product(String title, double price,String imageproduct, int quntity) {
        this.title = title;
        this.imageproduct = imageproduct;
        this.price = price;
        this.quntity = quntity;
    }

    public String getImageproduct() {
        return imageproduct;
    }

    public void setImageproduct(String imageproduct) {
        this.imageproduct = imageproduct;
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

    public ArrayList<product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<product> productArrayList) {
        this.productArrayList = productArrayList;
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
