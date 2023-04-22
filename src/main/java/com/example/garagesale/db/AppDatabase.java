package com.example.garagesale.db;

import android.net.wifi.hotspot2.pps.Credential;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.garagesale.Product;
import com.example.garagesale.ShoppingCart;
import com.example.garagesale.User;

@Database(entities = {User.class, Product.class, ShoppingCart.class},  version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "USERCREDENTIAL_DATABASE";
    public static final String USERCRED_TABLE = "USERCREDENTIAL_TABLE";

    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";

    public static final String SHOPPING_CART_TABLE = "SHOPPING_CART_TABLE";

    public abstract UserCredentialDAO getUserCredentialDAO();
    public abstract ProductDAO getProductDAO();
    public abstract ShoppingCartDAO getShoppingCartDAO();
}
