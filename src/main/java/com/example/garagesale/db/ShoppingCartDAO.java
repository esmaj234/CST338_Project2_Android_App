package com.example.garagesale.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.garagesale.ShoppingCart;

import java.util.List;

@Dao
public interface ShoppingCartDAO {
    @Insert
    void insert(ShoppingCart...shoppingCarts);

    @Update
    void update(ShoppingCart...shoppingCarts);

    @Delete
    void delete(ShoppingCart shoppingCart);

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_CART_TABLE)
    List<ShoppingCart> getShoppingCarts();


    @Query("SELECT * FROM " + AppDatabase.SHOPPING_CART_TABLE + " WHERE mShoppingCartId = :shoppingCartId")
    ShoppingCart getShoppingCartsId(int shoppingCartId);

    @Query("SELECT * FROM " + AppDatabase.SHOPPING_CART_TABLE + " WHERE mUserId = :userId")
    List<ShoppingCart> getShoppingCartsByUserId(int userId);

}
