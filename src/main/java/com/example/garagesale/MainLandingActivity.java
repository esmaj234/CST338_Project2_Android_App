package com.example.garagesale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garagesale.ShopActivity;
import com.example.garagesale.db.ShoppingCartDAO;
import com.example.garagesale.db.UserCredentialDAO;


public class MainLandingActivity extends AppCompatActivity {

    TextView mMainLandingPage;

    Button mShopButton;
    Button mViewCartButton;
    Button mManageProductsButton;
    Button mLogoutButton;

    // Admin buttons below
    Button mAdminManageUsersButton;
    Button mAdminDeleteProductButton;

    static int mUserId;
    static boolean mIsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_admin_landing);
        mMainLandingPage = findViewById(R.id.landingPage);

        // All users can see these buttons
        mShopButton = findViewById(R.id.shopButton);
        mViewCartButton = findViewById(R.id.viewCartButton);
        mManageProductsButton = findViewById(R.id.manageProductsButton);
        mLogoutButton = findViewById(R.id.logoutButton);

        // Admin buttons
        mAdminManageUsersButton = findViewById(R.id.adminManageUsersButton);
        mAdminDeleteProductButton = findViewById(R.id.adminDeleteProductButton);


        // Below checks if a user is an admin or not and displays the appropriate buttons
        mIsAdmin = getIntent().getExtras().getBoolean("mIsAdmin");
        mUserId = getIntent().getExtras().getInt("userId");

        if(mIsAdmin) {
            mAdminManageUsersButton.setVisibility(View.VISIBLE);
            mAdminDeleteProductButton.setVisibility(View.VISIBLE);

            mAdminManageUsersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = DeleteUserActivity.intentFactory(getApplicationContext(), mUserId, mIsAdmin);
                    startActivity(intent);
                }
            });

        } else {
            mAdminManageUsersButton.setVisibility(View.INVISIBLE);
            mAdminDeleteProductButton.setVisibility(View.INVISIBLE);
        }

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        mAdminDeleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DeleteProductActivity.intentFactory(getApplicationContext(), mUserId, mIsAdmin);
                startActivity(intent);
            }
        });
        //TODO: All onClickListeners

        mManageProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ManageProductsActivity.intentFactory(getApplicationContext(), mUserId, mIsAdmin);
                startActivity(intent);
            }
        });
        mShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShopActivity.intentFactory(getApplicationContext(), mUserId, mIsAdmin);
                startActivity(intent);
            }
        });

        mViewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ViewCartActivity.intentFactory(getApplicationContext(), mUserId, mIsAdmin);
                startActivity(intent);
            }
        });
    }


    public static Intent intentFactory(Context packageContent, int id, boolean isAdmin) {
        Intent intent = new Intent(packageContent, MainLandingActivity.class);

        intent.putExtra("mUserId", id);
        intent.putExtra("mIsAdmin", isAdmin);
        return intent;
    }

    public int getUserIdFromParent() {
        int userIdBundle = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userIdBundle = extras.getInt("mUserId");
        }
        return userIdBundle;
    }


}
