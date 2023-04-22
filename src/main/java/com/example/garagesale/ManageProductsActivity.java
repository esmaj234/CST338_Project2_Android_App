package com.example.garagesale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ManageProductsActivity extends AppCompatActivity {

    int id = 0;
    boolean isAdmin = false;

    TextView mManageProducts;
    EditText mProductName;
    EditText mProductDescription;
    Button mSearchProductButton;

    boolean extras = false;
    int userId = 0;
    Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        mManageProducts = findViewById(R.id.manageProductTextView);
        mProductName = findViewById(R.id.productNameEditText);
        mProductDescription = findViewById(R.id.productDescriptionEditText);
        mSearchProductButton = findViewById(R.id.searchProductButton);

        extras = getIntent().getExtras().getBoolean("mIsAdmin");
        userId = getIntent().getExtras().getInt("userId");
        product = getTextFromDisplay();

        mSearchProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product = getTextFromDisplay();

                if(checkProduct(product)) {
                    updateExistingProduct();
                    updateExistingProductPopUp();
                } else {
                    MainActivity.mProductDAO.insert(product);
                    addNewProductPopUp();
                }
            }
        });
    }
    public static Intent intentFactory(Context packageContent, int id, boolean isAdmin) {

        Intent intent = new Intent(packageContent, ManageProductsActivity.class);

        intent.putExtra("mUserId", id);
        intent.putExtra("mIsAdmin", isAdmin);
        return intent;


    }

    private Product getTextFromDisplay() {
        String name = "No product provided";
        String description = "Please provide a description";

        name = mProductName.getText().toString();
        description = mProductDescription.getText().toString();

        Product product = new Product(name, 20, description, 1);

        return product;
    }

    private void updateExistingProduct() {
        String name = "No product provided";
        String description = "Please provide a description";
        name = mProductName.getText().toString();

        Product product = MainActivity.mProductDAO.getProductByName(name);
        int currentQuantitiy = product.getProductQuantity();
        product.setProductQuantity(currentQuantitiy + 1);

    }

    public void addNewProductPopUp() {

        AlertDialog.Builder newProduct = new AlertDialog.Builder(this);
        newProduct.setTitle("New Product Added");
        newProduct.setMessage("Your product has been added to our database. Happy shopping!");
        newProduct.setPositiveButton("OK!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainLandingActivity.intentFactory(getApplicationContext(),
                                MainLandingActivity.mUserId, MainLandingActivity.mIsAdmin);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = newProduct.create();
        alertDialog.show();
    }

    public void updateExistingProductPopUp() {

        AlertDialog.Builder newProduct = new AlertDialog.Builder(this);
        newProduct.setTitle("Existing Product Updated");
        newProduct.setMessage("We have added your product with the others. Happy shopping!");
        newProduct.setPositiveButton("OK!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainLandingActivity.intentFactory(getApplicationContext(),
                                MainLandingActivity.mUserId, MainLandingActivity.mIsAdmin);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = newProduct.create();
        alertDialog.show();
    }

    private boolean checkProduct(Product product) {
        boolean exists = false;

        if(MainActivity.mProductDAO.getProductByName(product.getProductName()) != null) {
            exists = true;
        }
        return  exists;
    }
}