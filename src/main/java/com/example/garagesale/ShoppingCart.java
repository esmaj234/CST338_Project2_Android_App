package com.example.garagesale;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.garagesale.db.AppDatabase;

@Entity(tableName = AppDatabase.SHOPPING_CART_TABLE)
public class ShoppingCart {
    @PrimaryKey(autoGenerate = true)
    private int mShoppingCartId;
    private int mUserId;
    private int mProductId;
    private int mCartQuantity;
    private int mCartTotalPrice;

    public ShoppingCart(int mUserId, int mProductId, int mCartQuantity, int mCartTotalPrice) {
        this.mUserId = mUserId;
        this.mProductId = mProductId;
        this.mCartQuantity = mCartQuantity;
        this.mCartTotalPrice = mCartTotalPrice;
    }

    public int getShoppingCartId() {
        return mShoppingCartId;
    }

    public void setShoppingCartId(int mShoppingCartId) {
        this.mShoppingCartId = mShoppingCartId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getCartQuantity() {
        return mCartQuantity;
    }

    public void setCartQuantity(int mCartQuantity) {
        this.mCartQuantity = mCartQuantity;
    }

    public int getCartTotalPrice() {
        return mCartTotalPrice;
    }

    public void setCartTotalPrice(int mCartTotalPrice) {
        this.mCartTotalPrice = mCartTotalPrice;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int mProductId) {
        this.mProductId = mProductId;
    }
}
