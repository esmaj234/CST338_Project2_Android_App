package com.example.garagesale.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.garagesale.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insert(Product...products);

    @Update
    void update(Product...products);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE)
    List<Product> getProducts();


    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mProductName = :productName")
    Product getProductByName(String productName);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mProductId = :productId")
    Product getProductsById(int productId);

}
