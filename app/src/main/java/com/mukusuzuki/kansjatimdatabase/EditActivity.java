package com.mukusuzuki.kansjatimdatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class EditActivity extends AppCompatActivity {

    private EditText password_update, name_update, nickname_update, birthday_update, phone_update,
            email_update, address_update, school_update, motto_update, jurusan_update, name_parrent_update, phone_parrent_update
            , address_parrent_update, lineID_update, instagramID_update;

    private String name, nickname, birthday, phone, email, smasmp,school;
    private String nameschool,totalschool, address, motto, password, univFull;
    private String instagramID, lineID, username, nameparrent, phoneparrent, addressparent;

    //firebase
    FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    DatabaseReference userDatabase = mydatabase.getReference();


    private List<User> user_list = new ArrayList<User>();


    String UID;

    Bundle bundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        bundle = getIntent().getExtras();

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuItem = getMenuInflater();
        menuItem.inflate(R.menu.menu_edit_profile, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cancel:
                Intent intent = new Intent(EditActivity.this, ProfileActivity.class);
                bundle.putString("ID",bundle.getString("ID"));
                bundle.putString("USERNAME",bundle.getString("USERNAME"));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }

    private void init() {
        //firebase
        FirebaseApp.initializeApp(this);
        //--------------------------------------------------
        password_update = (EditText)findViewById(R.id.password_update);

        name_update = (EditText) findViewById(R.id.fullname_update);
        nickname_update = (EditText) findViewById(R.id.nickname_update);
        birthday_update = (EditText) findViewById(R.id.birthday_update);
        phone_update = (EditText) findViewById(R.id.phone_update);
        email_update = (EditText) findViewById(R.id.email_update);
        address_update = (EditText) findViewById(R.id.address_update);
        motto_update = (EditText) findViewById(R.id.motto_update);
        jurusan_update = (EditText)findViewById(R.id.jurusan_update);

        name_parrent_update = (EditText)findViewById(R.id.name_parrent_update);
        phone_parrent_update = (EditText)findViewById(R.id.phone_parrent_update);
        address_parrent_update = (EditText)findViewById(R.id.address_parrent_update);

        lineID_update = (EditText)findViewById(R.id.line_update);
        instagramID_update = (EditText)findViewById(R.id.instagram_update);

        //SET
        userDatabase.child("database").child(bundle.getString("ID")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Database database = dataSnapshot.getValue(Database.class);

                if (database != null) {
                    name_update.setText(database.getName());
                    nickname_update.setText(database.getNickname());
                    birthday_update.setText(database.getBirthday());
                    phone_update.setText(database.getPhone());
                    email_update.setText(database.getEmail());
                    address_update.setText(database.getAddress());
                    motto_update.setText(database.getMotto());
                    jurusan_update.setText(database.getUniversity());
                    lineID_update.setText(database.getLineinfo());
                    instagramID_update.setText(database.getInstagtaminfo());
                    name_parrent_update.setText(database.getParrentname());
                    phone_parrent_update.setText(database.getParrentphone());
                    address_parrent_update.setText(database.getParrentaddress());
                    totalschool = database.getSchool();
                } else finish();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void saveDataEdit(View view) {
        boolean
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
                password_bool = true,
                jurusan_bool = true;

        name_update.setError(null);
        nickname_update.setError(null);
        birthday_update.setError(null);
        phone_update.setError(null);
        email_update.setError(null);
        address_update.setError(null);
        motto_update.setError(null);
        password_update.setError(null);
        jurusan_update.setError(null);
        //
        instagramID_update.setError(null);
        lineID_update.setError(null);
        name_parrent_update.setError(null);
        phone_parrent_update.setError(null);
        address_parrent_update.setError(null);


        instagramID = instagramID_update.getText().toString();
        lineID = lineID_update.getText().toString();
        nameparrent = name_parrent_update.getText().toString();
        phoneparrent = phone_parrent_update.getText().toString();
        addressparent = address_parrent_update.getText().toString();

        name = name_update.getText().toString();
        nickname = nickname_update.getText().toString();
        birthday = birthday_update.getText().toString();
        phone = phone_update.getText().toString();
        email = email_update.getText().toString();
        address = address_update.getText().toString();
        motto = motto_update.getText().toString();
        password = password_update.getText().toString();
        univFull = jurusan_update.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(instagramID)) {
            instagramID_update.setError(getString(R.string.error_field_required));
            focusView = instagramID_update;
            cancel = true;
            instagram_bool = false;
        }else if (isusernameValid(instagramID)) {
            instagramID_update.setError(getString(R.string.error_nickname_struck));
            focusView = instagramID_update;
            cancel = true;
            instagram_bool = false;
        }

        if (TextUtils.isEmpty(lineID)) {
            lineID_update.setError(getString(R.string.error_field_required));
            focusView = lineID_update;
            cancel = true;
            lineinfo_bool = false;
        }else if (isusernameValid(lineID)) {
            lineID_update.setError(getString(R.string.error_nickname_struck));
            focusView = lineID_update;
            cancel = true;
            lineinfo_bool = false;
        }

        if (TextUtils.isEmpty(nameparrent)) {
            name_parrent_update.setError(getString(R.string.error_field_required));
            focusView = name_parrent_update;
            cancel = true;
            nameparrent_bool = false;
        }
        if (TextUtils.isEmpty(phoneparrent)) {
            phone_parrent_update.setError(getString(R.string.error_field_required));
            focusView = phone_parrent_update;
            cancel = true;
            phoneparrent_bool = false;
        }
        if (TextUtils.isEmpty(addressparent)) {
            address_parrent_update.setError(getString(R.string.error_field_required));
            focusView = address_parrent_update;
            cancel = true;
            addressparrent_bool = false;
        }

        if (TextUtils.isEmpty(name)) {
            name_update.setError(getString(R.string.error_field_required));
            focusView = name_update;
            cancel = true;
            name_bool = false;
        }

        if (TextUtils.isEmpty(nickname)) {
            nickname_update.setError(getString(R.string.error_field_required));
            focusView = nickname_update;
            cancel = true;
            nickname_bool = false;
        }

        if (TextUtils.isEmpty(univFull)) {
            jurusan_update.setError(getString(R.string.error_invalid_jurusan));
            focusView = jurusan_update;
            cancel = true;
            jurusan_bool = false;
        }


        if (TextUtils.isEmpty(birthday)) {
            birthday_update.setError(getString(R.string.error_field_required));
            focusView = birthday_update;
            cancel = true;
            birthday_bool = false;
        }else if (!isBirthdayValid(birthday)) {
            birthday_update.setError(getString(R.string.error_invalid_birthday));
            focusView = birthday_update;
            cancel = true;
            birthday_bool = false;
        }

        if (TextUtils.isEmpty(phone)) {
            phone_update.setError(getString(R.string.error_field_required));
            focusView = phone_update;
            cancel = true;
            phone_bool = false;
        }

        if (TextUtils.isEmpty(email)) {
            email_update.setError(getString(R.string.error_field_required));
            focusView = email_update;
            cancel = true;
            email_bool = false;
        } else if (!isEmailValid(email)) {
            email_update.setError(getString(R.string.error_invalid_email));
            focusView = email_update;
            cancel = true;
            email_bool = false;
        }

        if (TextUtils.isEmpty(password)) {
            password_update.setError(getString(R.string.error_field_required));
            focusView = password_update;
            cancel = true;
            password_bool = false;
        } else if (!isPasswordValid(password)) {
            password_update.setError(getString(R.string.error_invalid_password));
            focusView = password_update;
            cancel = true;
            password_bool = false;
        }

        if (TextUtils.isEmpty(address)) {
            address_update.setError(getString(R.string.error_field_required));
            focusView = address_update;
            cancel = true;
            address_bool = false;
        }

        if (TextUtils.isEmpty(motto)) {
            motto_update.setError(getString(R.string.error_field_required));
            focusView = motto_update;
            cancel = true;
            motto_bool = false;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        if (lineinfo_bool && instagram_bool && nameparrent_bool && phoneparrent_bool && addressparrent_bool &&
                jurusan_bool && password_bool && name_bool && birthday_bool && address_bool && email_bool
                && motto_bool && nickname_bool && phone_bool) {
            UID = bundle.getString("ID");
            username = bundle.getString("USERNAME");

            User user = new User(username,UID, nickname, name, password, phone, univFull);
            userDatabase.child("users").child(username).setValue(user);

            Database database = new Database(UID, name, nickname, birthday, phone, email, totalschool,
                    address, motto, univFull,nameparrent , phoneparrent, addressparent , lineID, instagramID, username );
            userDatabase.child("database").child(UID).setValue(database);

            Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
            Intent ZZ = new Intent(EditActivity.this, MainActivity.class);
            ZZ.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            ZZ.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ZZ);
            finish();
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

    private boolean isusernameValid(String uservalid) {
        //TODO: Replace this with your own logic
        return uservalid.contains(" ");
    }

}
