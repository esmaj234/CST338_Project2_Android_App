package com.example.garagesale;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.garagesale.db.AppDatabase;

import com.example.garagesale.db.ProductDAO;
@Entity(tableName = AppDatabase.PRODUCT_TABLE)
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int mProductId;
    private String mProductName;
    private int mProductPrice;
    private String mProductDescription;
    private int mProductQuantity;

    public Product(String mProductName, int mProductPrice, String mProductDescription, int mProductQuantity) {
        this.mProductName = mProductName;
        this.mProductPrice = mProductPrice;
        this.mProductDescription = mProductDescription;
        this.mProductQuantity = mProductQuantity;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int mProductId) {
        this.mProductId = mProductId;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public int getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(int mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public String getProductDescription() {
        return mProductDescription;
    }

    public void setProductDescription(String mProductDescription) {
        this.mProductDescription = mProductDescription;
    }

    public int getProductQuantity() {
        return mProductQuantity;
    }

    public void setProductQuantity(int mProductQuantity) {
        this.mProductQuantity = mProductQuantity;
    }
}
