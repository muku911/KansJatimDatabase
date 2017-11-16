package com.mukusuzuki.kansjatimdatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateActivity extends AppCompatActivity {

    private Spinner dropdown_create;
    private CheckBox smp_create, sma_create;

    private EditText username_create, password_create, name_create, nickname_create, birthday_create, phone_create,
            email_create, address_create, school_create, motto_create, jurusan_create, name_parrent_create, phone_parrent_create
            , address_parrent_create, lineID_create, instagramID_create;

    private String name, nickname, birthday, phone, email, smasmp,school;
    private String nameschool,totalschool, address, motto, password, univFull;
    private String instagramID, lineID, username, nameparrent, phoneparrent, addressparent;

    //firebase
    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userDatabase = mydatabase.getReference();


    private List<User> user_list = new ArrayList<User>();


    private String UID = UUID.randomUUID().toString();


    @Override
    public void onBackPressed() {
        Intent opo = new Intent(CreateActivity.this, MainActivity.class);
        startActivity(opo);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        init();
        dropdown();

    }

    private void init() {
        //get the spinner from the xml.
        dropdown_create = (Spinner) findViewById(R.id.drop_school_create);
        //--------------------------------------------------
        username_create = (EditText)findViewById(R.id.username_create);
        password_create = (EditText)findViewById(R.id.password_create);

        name_create = (EditText) findViewById(R.id.fullname_create);
        nickname_create = (EditText) findViewById(R.id.nickname_create);
        birthday_create = (EditText) findViewById(R.id.birthday_create);
        phone_create = (EditText) findViewById(R.id.phone_create);
        email_create = (EditText) findViewById(R.id.email_create);
        school_create = (EditText) findViewById(R.id.schoolgen_create);
        address_create = (EditText) findViewById(R.id.address_create);
        motto_create = (EditText) findViewById(R.id.motto_create);
        jurusan_create = (EditText)findViewById(R.id.jurusan_create);
        smp_create = (CheckBox)findViewById(R.id.cb_SMP_create);
        sma_create = (CheckBox)findViewById(R.id.cb_SMA_create);

        name_parrent_create = (EditText)findViewById(R.id.name_parrent_create);
        phone_parrent_create = (EditText)findViewById(R.id.phone_parrent_create);
        address_parrent_create = (EditText)findViewById(R.id.address_parrent_create);

        lineID_create = (EditText)findViewById(R.id.line_create);
        instagramID_create = (EditText)findViewById(R.id.instagram_create);
        //firebase
        FirebaseApp.initializeApp(this);
    }

    private void dropdown() {
        //create a list of items for the spinner.
        String[] items = new String[]{"School", "Anyer", "Lembang"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown_create.setAdapter(adapter);
    }

    public void saveData(View view) {
        boolean user_bool = true,
                lineinfo_bool = true,
                instagram_bool = true,
                nameparrent_bool = true,
                phoneparrent_bool = true,
                addressparrent_bool = true,
                name_bool = true,
                nickname_bool = true,
                birthday_bool = true,
                phone_bool = true,
                email_bool = true,
                address_bool = true,
                motto_bool = true,
                school_bool = true,
                password_bool = true,
                jurusan_bool = true ,
                droplist_bool = false,
                smp_sma_bool = false;

        name_create.setError(null);
        nickname_create.setError(null);
        birthday_create.setError(null);
        phone_create.setError(null);
        email_create.setError(null);
        school_create.setError(null);
        address_create.setError(null);
        motto_create.setError(null);
        password_create.setError(null);
        jurusan_create.setError(null);
        //
        instagramID_create.setError(null);
        lineID_create.setError(null);
        username_create.setError(null);
        name_parrent_create.setError(null);
        phone_parrent_create.setError(null);
        address_parrent_create.setError(null);


        instagramID = instagramID_create.getText().toString();
        lineID = lineID_create.getText().toString();
        username = username_create.getText().toString().toLowerCase();
        nameparrent = name_parrent_create.getText().toString();
        phoneparrent = phone_parrent_create.getText().toString();
        addressparent = address_parrent_create.getText().toString();

        name = name_create.getText().toString();
        nickname = nickname_create.getText().toString();
        birthday = birthday_create.getText().toString();
        phone = phone_create.getText().toString();
        email = email_create.getText().toString();
        school = school_create.getText().toString();
        nameschool = dropdown_create.getSelectedItem().toString();
        address = address_create.getText().toString();
        motto = motto_create.getText().toString();
        password = password_create.getText().toString();
        univFull = jurusan_create.getText().toString();
        if (sma_create.isChecked() && smp_create.isChecked()) smasmp = "SMP SMA ";
        else if (smp_create.isChecked()) smasmp = "SMP";
        else if (sma_create.isChecked()) smasmp = "SMA";

        totalschool = smasmp + " NFBS " + nameschool + " " + school;

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(instagramID)) {
            instagramID_create.setError(getString(R.string.error_field_required));
            focusView = instagramID_create;
            cancel = true;
            instagram_bool = false;
        }else if (isusernameValid(instagramID)) {
            instagramID_create.setError(getString(R.string.error_nickname_struck));
            focusView = instagramID_create;
            cancel = true;
            instagram_bool = false;
        }

        if (TextUtils.isEmpty(lineID)) {
            lineID_create.setError(getString(R.string.error_field_required));
            focusView = lineID_create;
            cancel = true;
            lineinfo_bool = false;
        }else if (isusernameValid(lineID)) {
            lineID_create.setError(getString(R.string.error_nickname_struck));
            focusView = lineID_create;
            cancel = true;
            lineinfo_bool = false;
        }

        if (TextUtils.isEmpty(nameparrent)) {
            name_parrent_create.setError(getString(R.string.error_field_required));
            focusView = name_parrent_create;
            cancel = true;
            nameparrent_bool = false;
        }
        if (TextUtils.isEmpty(phoneparrent)) {
            phone_parrent_create.setError(getString(R.string.error_field_required));
            focusView = phone_parrent_create;
            cancel = true;
            phoneparrent_bool = false;
        }
        if (TextUtils.isEmpty(addressparent)) {
            address_parrent_create.setError(getString(R.string.error_field_required));
            focusView = address_parrent_create;
            cancel = true;
            addressparrent_bool = false;
        }

        if(nameschool == "School"){
            Toast.makeText(getBaseContext(), getString(R.string.dropdown_invalid), Toast.LENGTH_LONG).show();
        }else droplist_bool = true;

        if (!smp_create.isChecked()&&!sma_create.isChecked()){
            Toast.makeText(getBaseContext(), getString(R.string.smp_or_sma), Toast.LENGTH_LONG).show();
        }else smp_sma_bool = true;

        if (TextUtils.isEmpty(name)) {
            name_create.setError(getString(R.string.error_field_required));
            focusView = name_create;
            cancel = true;
            name_bool = false;
        }

        if (TextUtils.isEmpty(nickname)) {
            nickname_create.setError(getString(R.string.error_field_required));
            focusView = nickname_create;
            cancel = true;
            nickname_bool = false;
        }

        if (TextUtils.isEmpty(univFull)) {
            jurusan_create.setError(getString(R.string.error_invalid_jurusan));
            focusView = jurusan_create;
            cancel = true;
            jurusan_bool = false;
        }

        if (TextUtils.isEmpty(username)) {
            username_create.setError(getString(R.string.error_field_required));
            focusView = username_create;
            cancel = true;
            user_bool = false;
        }else if (isusernameValid(username)) {
            username_create.setError(getString(R.string.error_nickname_struck));
            focusView = username_create;
            cancel = true;
            user_bool = false;
        }else if (!isusernameTEJO(username)){
            username_create.setError("YOUR NICKNAME TO SHORT");
            focusView = username_create;
            cancel = true;
            user_bool = false;
        }

        if (TextUtils.isEmpty(birthday)) {
            birthday_create.setError(getString(R.string.error_field_required));
            focusView = birthday_create;
            cancel = true;
            birthday_bool = false;
        }else if (!isBirthdayValid(birthday)) {
            birthday_create.setError(getString(R.string.error_invalid_birthday));
            focusView = birthday_create;
            cancel = true;
            birthday_bool = false;
        }

        if (TextUtils.isEmpty(phone)) {
            phone_create.setError(getString(R.string.error_field_required));
            focusView = phone_create;
            cancel = true;
            phone_bool = false;
        }

        if (TextUtils.isEmpty(email)) {
            email_create.setError(getString(R.string.error_field_required));
            focusView = email_create;
            cancel = true;
            email_bool = false;
        } else if (!isEmailValid(email)) {
            email_create.setError(getString(R.string.error_invalid_email));
            focusView = email_create;
            cancel = true;
            email_bool = false;
        }

        if (TextUtils.isEmpty(password)) {
            password_create.setError(getString(R.string.error_field_required));
            focusView = password_create;
            cancel = true;
            password_bool = false;
        } else if (!isPasswordValid(password)) {
            password_create.setError(getString(R.string.error_invalid_password));
            focusView = password_create;
            cancel = true;
            password_bool = false;
        }

        if (TextUtils.isEmpty(school)) {
            school_create.setError(getString(R.string.error_field_required));
            focusView = school_create;
            cancel = true;
            school_bool = false;
        }

        if (TextUtils.isEmpty(address)) {
            address_create.setError(getString(R.string.error_field_required));
            focusView = address_create;
            cancel = true;
            address_bool = false;
        }

        if (TextUtils.isEmpty(motto)) {
            motto_create.setError(getString(R.string.error_field_required));
            focusView = motto_create;
            cancel = true;
            motto_bool = false;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }


        if (lineinfo_bool && instagram_bool && nameparrent_bool && phoneparrent_bool && addressparrent_bool &&
                user_bool && jurusan_bool && password_bool && name_bool && birthday_bool && address_bool && droplist_bool && email_bool
                && motto_bool && nickname_bool && phone_bool && school_bool && smp_sma_bool){

            userDatabase.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);
                        user_list.add(user);
                    }

                    if (user_list.size() > 0){
                        for (int x = 0 ; x < user_list.size(); x++){
                            if (!username.equals(user_list.get(x).getUser())){

                                User user = new User(username,UID, nickname, name, password, phone, univFull);
                                userDatabase.child("users").child(user.getUser()).setValue(user);

                                Database database = new Database(UID, name, nickname, birthday, phone, email,
                                        totalschool, address, motto, univFull,nameparrent , phoneparrent, addressparent , lineID,
                                        instagramID, username );
                                userDatabase.child("database").child(database.getUid()).setValue(database);

                                Toast.makeText(getBaseContext(), "Done...Thanks You" , Toast.LENGTH_SHORT).show();
                                Intent bobo = new Intent(CreateActivity.this, LoginActivity.class);
                                startActivity(bobo);
                                finish();
                                break;
                            } else if (username.equals(user_list.get(x).getUser()))
                                Toast.makeText(getBaseContext(), "FAIL TRY ANOTHER NICKNAME" , Toast.LENGTH_SHORT).show();
                        }
                    } else if (user_list.size() <= 0){

                        User user = new User(username, UID, nickname, name, password, phone, univFull);
                        userDatabase.child("users").child(user.getUser()).setValue(user);

                        Database database = new Database(UID, name, nickname, birthday, phone, email,
                                totalschool, address, motto, univFull,nameparrent , phoneparrent,
                                addressparent , lineID, instagramID, username);
                        userDatabase.child("database").child(database.getUid()).setValue(database);

                        Toast.makeText(getBaseContext(), "Done...Thanks You" , Toast.LENGTH_LONG).show();
                        Intent bobo = new Intent(CreateActivity.this, LoginActivity.class);
                        startActivity(bobo);
                        finish();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    private boolean isBirthdayValid(String birthday) {
        return birthday.contains(",");
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    private boolean isusernameTEJO(String username) {
        //TODO: Replace this with your own logic
        return username.length() >= 6;
    }

    private boolean isusernameValid(String uservalid) {
        //TODO: Replace this with your own logic
        return uservalid.contains(" ");
    }

}
