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

import com.example.garagesale.db.ProductDAO;

public class DeleteProductActivity extends AppCompatActivity {

    Button mDeleteProductButton;
    EditText mDeleteProductEditText;

    boolean mIsAdmin;
    int mUserId;

    ProductDAO mProductDAO = MainActivity.mProductDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        mDeleteProductButton = findViewById(R.id.findProductToDeleteButton);
        mDeleteProductEditText = findViewById(R.id.deleteProductName);

        mUserId = MainLandingActivity.mUserId;
        mIsAdmin = MainLandingActivity.mIsAdmin;

        mDeleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productSearch();
            }
        });
    }

    public static Intent intentFactory(Context packageContent, int id, boolean isAdmin) {
        Intent intent = new Intent(packageContent, DeleteProductActivity.class);
        intent.putExtra("mIsAdmin", isAdmin);
        intent.putExtra("mUserId", id);

        return intent;
    }

    private void productSearch() {
        String product = "No product provided";
        product = mDeleteProductEditText.getText().toString();
        if (mProductDAO.getProductByName(product) != null) {
            foundProductPopUp(product);

        } else {
            productNotFoundPopUp();
        }

    }

    private void foundProductPopUp(String product) {

        AlertDialog.Builder passwordAlert = new AlertDialog.Builder(this);
        passwordAlert.setTitle("Delete Product?");
        passwordAlert.setMessage("Would you really like to delete all " + product + " from database ???");

        passwordAlert.setPositiveButton("Absolutely!",

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Product deleteProduct = mProductDAO.getProductByName(product);
                        mProductDAO.delete(deleteProduct);

                        productDeleteSuccess();
                    }
                });
        passwordAlert.setNegativeButton("No way!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = DeleteProductActivity.intentFactory(getApplicationContext(),
                                MainLandingActivity.mUserId, MainLandingActivity.mIsAdmin);
                        startActivity(intent);
                    }
                });

        AlertDialog alertDialog = passwordAlert.create();
        alertDialog.show();

    }

    private void productNotFoundPopUp() {
        AlertDialog.Builder notUser = new AlertDialog.Builder(this);
        notUser.setTitle("Product Not Found");
        notUser.setMessage("Unable to find product...");
        notUser.setPositiveButton("OK!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainLandingActivity.intentFactory(getApplicationContext(),
                                MainLandingActivity.mUserId, MainLandingActivity.mIsAdmin);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = notUser.create();
        alertDialog.show();
    }

    private void productDeleteSuccess() {
        AlertDialog.Builder notUser = new AlertDialog.Builder(this);
        notUser.setTitle("Successful Delete");
        notUser.setMessage("Product has gone bye-bye");
        notUser.setPositiveButton("Finally!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainLandingActivity.intentFactory(getApplicationContext(),
                                MainLandingActivity.mUserId, MainLandingActivity.mIsAdmin);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = notUser.create();
        alertDialog.show();
    }

}