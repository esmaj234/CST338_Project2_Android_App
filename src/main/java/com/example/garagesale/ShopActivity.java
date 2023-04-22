package com.example.garagesale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.garagesale.MainActivity;
import com.example.garagesale.MainLandingActivity;
import com.example.garagesale.R;
import com.example.garagesale.db.ProductDAO;
import com.example.garagesale.db.ShoppingCartDAO;
import com.example.garagesale.db.UserCredentialDAO;

import java.util.List;

public class ShopActivity extends AppCompatActivity {


    ProductDAO mProductDAO = MainActivity.mProductDAO;
    ShoppingCartDAO mCartDAO = MainActivity.mCartDAO;
    int mUserId = MainActivity.mUserId;
    TextView mProductView;
    Button mSearchProductButton;
    EditText mSearchProductEditText;
    Button mAddToCartButton;
    Product mProducts;
    Button mViewCartButton;
    Button mShopBackButton;
    boolean mIsAdmin = MainLandingActivity.mIsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mProductView = findViewById(R.id.ProductListView);
        mSearchProductButton = findViewById(R.id.searchForProduct);
        mSearchProductEditText = findViewById(R.id.shopProductSearch);
        mAddToCartButton = findViewById(R.id.productAddToCart);
        mViewCartButton = findViewById(R.id.viewCartOnShopPage);
        mShopBackButton = findViewById(R.id.shopPageBackButton);

        mSearchProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshDisplay(getFromDisplay());
            }
        });

        mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ShoppingCart> cartItems = mCartDAO.getShoppingCartsByUserId(mUserId);
                ShoppingCart item = new ShoppingCart(mUserId, getFromDisplay().getProductId(),
                        1, getFromDisplay().getProductPrice());
                Product viewingProduct = mProductDAO.getProductsById(item.getProductId());

                if(cartItems.isEmpty()) {
                    mCartDAO.insert(item);

                } else {
                    for (ShoppingCart cart : cartItems) {
                        if (cart.getProductId() == item.getProductId()) {

                            // increase count and quantity on current cart item
                            int cartQuantity = cart.getCartQuantity();
                            cart.setCartQuantity(cartQuantity + 1);

                            int currentPrice = viewingProduct.getProductPrice();
                            int cartAmount = cart.getCartTotalPrice();
                            cart.setCartTotalPrice(cartAmount + currentPrice);

                            // Reduce quantity of products in product db

                            if(viewingProduct.getProductQuantity() == 1) {
                                mProductDAO.delete(viewingProduct);

                            } else {

                                int currQuantity = viewingProduct.getProductQuantity();
                                viewingProduct.setProductQuantity(currQuantity - 1);
                            }
                        }
                    }
                }

                successfulCartAddPopUp();
            }

        });
        mViewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ViewCartActivity.intentFactory(getApplicationContext(), mUserId, mIsAdmin);
                startActivity(intent);
            }
        });

        mShopBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainLandingActivity.intentFactory(getApplicationContext(),
                        MainLandingActivity.mUserId, MainLandingActivity.mIsAdmin);
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context packageContent, int id, boolean isAdmin) {
        Intent intent = new Intent(packageContent, ShopActivity.class);
        intent.putExtra("mUserId", id);
        intent.putExtra("mIsAdmin", isAdmin);
        return intent;
    }
    private Product getFromDisplay() {
        String productName = "No product provided";
        productName = mSearchProductEditText.getText().toString();

        Product foundProduct = mProductDAO.getProductByName(productName);

        return foundProduct;
    }

    private void refreshDisplay(Product product) {
        mProducts = mProductDAO.getProductByName(product.getProductName());

        if(mProducts == null) {
            mProductView.setText("Select a different product");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(mProducts.getProductName().toString());
        sb.append("\n");
        sb.append(mProducts.getProductDescription().toString());
        sb.append("\n");
        sb.append("$" + mProducts.getProductPrice());

        mProductView.setText(sb.toString());
    }

    private void successfulCartAddPopUp() {

        AlertDialog.Builder success = new AlertDialog.Builder(this);
        success.setTitle("Item Added to Cart");
        success.setMessage("Your item has been added to your cart!");
        success.setPositiveButton("OK!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainLandingActivity.intentFactory(getApplicationContext(), mUserId,mIsAdmin);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = success.create();
        alertDialog.show();
    }
}