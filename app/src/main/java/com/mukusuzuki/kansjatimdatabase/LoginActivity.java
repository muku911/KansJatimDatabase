package com.mukusuzuki.kansjatimdatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {


    // UI references.
    EditText username_input, password_input;
    Button addbtn, loginButton;
    ProgressBar progressBar;
    private CheckBox savelogin;

    private SharedPreferences loginSave;
    private SharedPreferences.Editor loginEditor;
    private boolean saveLoginbool;


    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabase;

    private User user;

    private List<User> user_list = new ArrayList<User>();

    private String user_username, user_password, username, uiD, userlogin;

    boolean login_Suces = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Animation//
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        init();

        loginSave = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginEditor = loginSave.edit();

        saveLoginbool = loginSave.getBoolean("saveLogin", false);

        if(saveLoginbool == true){
            username_input.setText(loginSave.getString("username",""));
            password_input.setText(loginSave.getString("password",""));
            savelogin.setChecked(true);

        }

        addEventFirebaseListener();


        //oooooooooooooooooooo
        addbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,CreateActivity.class);
                startActivity(i);
                finish();
            }
        });
        //oooooooooooooooooooo

    }

    private void addEventFirebaseListener() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
        addbtn.setVisibility(View.GONE);

        userDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (user_list.size() > 0)
                    user_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user = snapshot.getValue(User.class);
                    user_list.add(user);
                }

                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                addbtn.setVisibility(View.VISIBLE);

                loginButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user_username = username_input.getText().toString().toLowerCase();
                        user_password = password_input.getText().toString();


                        for (int i = 0; i < user_list.size(); i++){
                            userlogin = user_list.get(i).getUser();
                            //Toast.makeText(getBaseContext(), "Name of Fuck\n " + userlogin, Toast.LENGTH_SHORT).show();
                            if (user_username.equals(userlogin)){
                                uiD = user_list.get(i).getUid();
                                username = user_list.get(i).getUser();
                                userDatabase.child("users").child(username).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        LoginPassword password = dataSnapshot.getValue(LoginPassword.class);
                                        if (password == null){
                                            Intent GO = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(GO);
                                            finish();
                                        }else if (user_password.equals(password.getPassword())){
                                            Toast.makeText(getBaseContext(), "Welcome " + user_username, Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("ID", uiD);
                                            bundle.putString("USERNAME", username);
                                            i.putExtras(bundle);
                                            //remember
                                                if(savelogin.isChecked()){
                                                    loginEditor.putBoolean("saveLogin",true);
                                                    loginEditor.putString("username", username);
                                                    loginEditor.putString("password", user_password);
                                                    loginEditor.commit();
                                                }else{
                                                    loginEditor.clear();
                                                    loginEditor.commit();
                                                }
                                            //start
                                            startActivity(i);
                                            finish();

                                        }
                                        else if (!user_password.equals(password.getPassword())){
                                            Toast.makeText(getBaseContext(), "Nickname or Password Incorrect", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getBaseContext(), "(Press Login Again)", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDatabase = firebaseDatabase.getReference();

        addbtn = (Button)findViewById(R.id.add_btn);
        loginButton = (Button) findViewById(R.id.login_btn);
        username_input = (EditText)findViewById(R.id.username_login);
        password_input = (EditText)findViewById(R.id.password);
        progressBar = (ProgressBar)findViewById(R.id.login_progress);

        savelogin = (CheckBox)findViewById(R.id.saveuserpass);

        userlogin = null;

    }
}

