package com.example.garagesale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.garagesale.databinding.ActivityMainBinding;
import com.example.garagesale.db.AppDatabase;
import com.example.garagesale.db.ProductDAO;
import com.example.garagesale.db.ShoppingCartDAO;
import com.example.garagesale.db.UserCredentialDAO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView mMainDisplay;

    EditText mUsername;
    EditText mPassword;

    static int mUserId;

    Button mLoginButton;

    Button mCreateAccountButton;


    static UserCredentialDAO mUserDAO;
    static ProductDAO mProductDAO;
    static ShoppingCartDAO mCartDAO;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainDisplay = findViewById(R.id.mainLogInPageTextView);
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserCredentialDAO();

        mProductDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getProductDAO();

        mCartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getShoppingCartDAO();

        mUsername = findViewById(R.id.mainLogInPageUsername);
        mPassword = findViewById(R.id.mainLogInPagePassword);

        mLoginButton = findViewById(R.id.mainSubmitLoginCredentialsButton);
        mCreateAccountButton = findViewById(R.id.mainCreateAccountButton);

        // Add info to DB if empty
        if(mUserDAO.getUserCredentials().isEmpty()) {
            User admin2 = new User("admin2", "admin2", true);
            User nonAdmin = new User("testuser1", "testuser1", false);

            mUserDAO.insert(admin2);
            mUserDAO.insert(nonAdmin);
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            User user = getCredsFromDisplay();

            @Override
            public void onClick(View view) {

                if (mUserDAO.getUserCredentialsByUsername(mUsername.getText().toString()) != null) {

                    user = mUserDAO.getUserCredentialsByUsername(mUsername.getText().toString());
                    Intent intent = MainLandingActivity.intentFactory(getApplicationContext(), user.getUserId(), user.getIsAdmin());
                    startActivity(intent);
                }
            }
        });

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = getCredsFromDisplay();

                if (checkUserCred(user)) {
                    //TODO: tell user already exists
                    List<User> userList = new ArrayList<>(mUserDAO.getUserCredentials());

                    for (User u : userList) {
                        if (user.getUsername().equals(u.getUsername())) {
                            userExistsPopUp();
                        }
                    }

                } else {
                    if(user.getPassword().length() < 5) {
                        insufficientPassword();
                    } else {
                        mUserDAO.insert(user);
                    }
                }
            }
        });
    }


    private User getCredsFromDisplay() {
        String username = "No username provided";
        String pw = "Please provide a password";
        User userCred;

        username = mUsername.getText().toString();
        pw = mPassword.getText().toString();

        userCred = new User(username, pw, false);

        return userCred;
    }


    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);

        return intent;
    }

    private boolean checkUserCred(User user) {
        boolean exists = false;

        if(mUserDAO.getUserCredentialsByUsername(user.getUsername()) != null) {
            exists = true;
        }
        return  exists;
    }

    // Code below has been adapted from TutorialsPoint
    //https://www.tutorialspoint.com/how-do-i-display-an-alert-dialog-on-android
    public void insufficientPassword() {

        AlertDialog.Builder passwordAlert = new AlertDialog.Builder(this);
        passwordAlert.setTitle("Insufficient Password");
        passwordAlert.setMessage("Password must have at least 5 characters!");
        passwordAlert.setPositiveButton("OK!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = passwordAlert.create();
        alertDialog.show();

    }

    // Code below has been adapted from TutorialsPoint
    //https://www.tutorialspoint.com/how-do-i-display-an-alert-dialog-on-android
    public void userExistsPopUp() {
        AlertDialog.Builder passwordAlert = new AlertDialog.Builder(this);
        passwordAlert.setTitle("Username Taken");
        passwordAlert.setMessage("Please choose another username");
        passwordAlert.setPositiveButton("OK!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = passwordAlert.create();
        alertDialog.show();
    }
}