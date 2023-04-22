package com.example.garagesale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Delete;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.garagesale.db.UserCredentialDAO;

public class DeleteUserActivity extends AppCompatActivity {

    TextView mDeleteUser;
    Button mFindDeleteButton;
    EditText mFindUserName;
    UserCredentialDAO mUserDAO = MainActivity.mUserDAO;
    boolean extras = false;
    int userId = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        mDeleteUser = findViewById(R.id.deleteUserActivity);
        mFindDeleteButton = findViewById(R.id.findUserToDeleteButton);
        mFindUserName = findViewById(R.id.deleteUserName);
        extras = getIntent().getExtras().getBoolean("mIsAdmin");
        userId = getIntent().getExtras().getInt("mUserId");

        mFindDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSearch();

            }
        });
    }

    public static Intent intentFactory(Context packageContent, int id, boolean isAdmin) {
        Intent intent = new Intent(packageContent, DeleteUserActivity.class);
        intent.putExtra("mIsAdmin", isAdmin);
        intent.putExtra("mUserId", id);

        return intent;
    }

    private void userSearch() {
        String username = "No username provided";
        username = mFindUserName.getText().toString();
        if (mUserDAO.getUserCredentialsByUsername(username) != null) {
            foundUserPopUp(username);

        } else {
            userNotFoundPopUp();
        }

    }

    private void foundUserPopUp(String username) {

        AlertDialog.Builder passwordAlert = new AlertDialog.Builder(this);
        passwordAlert.setTitle("Delete User?");
        passwordAlert.setMessage("Would you really like to delete " + username + " ???");

        passwordAlert.setPositiveButton("Absolutely!",

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        User deleteUser = mUserDAO.getUserCredentialsByUsername(username);
                        mUserDAO.delete(deleteUser);

                        userDeleteSuccess();
                    }
                });
        passwordAlert.setNegativeButton("No way!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = DeleteUserActivity.intentFactory(getApplicationContext(), userId, extras);
                        startActivity(intent);
                    }
                });

        AlertDialog alertDialog = passwordAlert.create();
        alertDialog.show();

    }

    private void userNotFoundPopUp() {
        AlertDialog.Builder notUser = new AlertDialog.Builder(this);
        notUser.setTitle("User Not Found");
        notUser.setMessage("Unable to find user...");
        notUser.setPositiveButton("OK!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainLandingActivity.intentFactory(getApplicationContext(), userId, extras);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = notUser.create();
        alertDialog.show();
    }

    private void userDeleteSuccess() {
        AlertDialog.Builder notUser = new AlertDialog.Builder(this);
        notUser.setTitle("Successful Delete");
        notUser.setMessage("User has gone bye-bye");
        notUser.setPositiveButton("Finally!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = MainLandingActivity.intentFactory(getApplicationContext(), userId, extras);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = notUser.create();
        alertDialog.show();
    }

}
