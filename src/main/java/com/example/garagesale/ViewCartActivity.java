package com.example.garagesale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.garagesale.db.ProductDAO;
import com.example.garagesale.db.ShoppingCartDAO;

import java.util.List;

public class ViewCartActivity extends AppCompatActivity {

    Button mCheckoutButton;
    Button mBackButton;
    TextView mViewCart;

    ShoppingCartDAO mCartDAO = MainActivity.mCartDAO;
    ProductDAO mProductDAO = MainActivity.mProductDAO;

    boolean isAdmin = MainLandingActivity.mIsAdmin;
    int userId = MainLandingActivity.mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        mCheckoutButton = findViewById(R.id.checkoutUserCartButton);
        mBackButton = findViewById(R.id.cartPageBackButton);
        mViewCart = findViewById(R.id.cartViewTable);

        this.refreshDisplay();

        mCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkoutConfirm();

            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShopActivity.intentFactory(getApplicationContext(), userId, isAdmin);
                startActivity(intent);
            }
        });

    }

    public static Intent intentFactory(Context packageContext, int userId, boolean isAdmin) {
        Intent intent = new Intent(packageContext, ViewCartActivity.class);

        intent.putExtra("mUserId", userId);
        intent.putExtra("mIsAdmin", isAdmin);
        return intent;
    }

    private void checkoutConfirm() {

        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Order Details");
        confirm.setMessage("Your order is on its way!");
        confirm.setPositiveButton("OK!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = confirm.create();
        alertDialog.show();
    }
    private void refreshDisplay() {
        List<ShoppingCart> allCartItems = mCartDAO.getShoppingCartsByUserId(userId);

        if(allCartItems.size() == 0) {
            mViewCart.setText("You need to buy something...");
        }

        StringBuilder sb = new StringBuilder();
        for (ShoppingCart item : allCartItems) {
            Product productInfo = mProductDAO.getProductsById(item.getProductId());
            sb.append(productInfo.getProductName().toString());
            sb.append("\t");
            sb.append(productInfo.getProductQuantity());
            sb.append("\t");
            sb.append(productInfo.getProductPrice());
            sb.append("\n");
        }
        mViewCart.setText(sb.toString());
    }

}