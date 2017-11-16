package com.mukusuzuki.kansjatimdatabase;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView text_nickname, text_nicknametop, text_name, text_birthday, text_phone, text_email,
            text_address, text_school, text_motto, text_jurusan;
    private TextView text_user, text_id, text_parrent_name, text_parrent_address, text_parrent_phone, text_instagram, text_line;
    private ImageButton to_insta, to_line;

    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabase;

    private String getFromLogin_UID, getFromLogin_USERNAME;
    private String lineData, instaData, phoneData, phoneparrentData;

    private boolean deleteAccount, editAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        addValueFirebaseListener();
    }

    @Override
    public void onBackPressed() {

    }

    private void addValueFirebaseListener() {
        userDatabase.child("database").child(getFromLogin_UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Database database = dataSnapshot.getValue(Database.class);


                text_nickname.setText(database.getNickname());
                text_nicknametop.setText(database.getNickname());
                text_name.setText(database.getName());
                text_birthday.setText(database.getBirthday());
                text_phone.setText(database.getPhone());
                text_email.setText(database.getEmail());
                text_address.setText(database.getAddress());
                text_school.setText(database.getSchool());
                text_motto.setText(database.getMotto());
                text_jurusan.setText(database.getUniversity());

                text_user.setText(database.getUser());
                text_id.setText(database.getUid());
                text_parrent_name.setText(database.getParrentname());
                text_parrent_address.setText(database.getParrentaddress());
                text_parrent_phone.setText(database.getParrentphone());
                text_instagram.setText(database.getInstagtaminfo());
                text_line.setText(database.getLineinfo());

                //make linkable

                lineData = database.getLineinfo();
                instaData = database.getInstagtaminfo();
                phoneData = database.getPhone();
                phoneparrentData = database.getParrentphone();

                text_phone.setPaintFlags(text_phone.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                text_parrent_phone.setPaintFlags(text_phone.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

                to_insta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/" + instaData));
                        startActivity(browserIntent);
                    }
                });

                to_line.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://line.me/R/ti/p/~" + lineData));
                        startActivity(browserIntent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        //FIREBASE
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDatabase = firebaseDatabase.getReference();

        //UI
        text_nickname = (TextView) findViewById(R.id.tx_Nickname_info_profile);
        text_nicknametop = (TextView) findViewById(R.id.tx_Nicknametop_info_profile);
        text_name = (TextView) findViewById(R.id.tx_Name_info_profile);
        text_birthday = (TextView) findViewById(R.id.tx_birthday_info_profile);
        text_phone = (TextView) findViewById(R.id.tx_Phone_info_profile);
        text_email = (TextView) findViewById(R.id.tx_Email_info_profile);
        text_address = (TextView) findViewById(R.id.tx_address_info_profile);
        text_school = (TextView) findViewById(R.id.tx_Generation_info_profile);
        text_motto = (TextView) findViewById(R.id.tx_isi_Motto_info_profile);
        text_jurusan = (TextView) findViewById(R.id.tx_jurusan_info_profile);

        text_user = (TextView) findViewById(R.id.tx_User_info_profile);
        text_id = (TextView) findViewById(R.id.tx_id_info_profile);
        text_parrent_name = (TextView) findViewById(R.id.tx_name_parrent_info_profile);
        text_parrent_address = (TextView) findViewById(R.id.tx_address_parrent_info_profile);
        text_parrent_phone = (TextView) findViewById(R.id.tx_phone_parrent_info_profile);
        text_instagram = (TextView) findViewById(R.id.tx_insta_info_profile);
        text_line = (TextView) findViewById(R.id.tx_line_info_profile);

        to_insta = (ImageButton) findViewById(R.id.icon_insta_info_profile);
        to_line = (ImageButton) findViewById(R.id.icon_line_info_profile);

        //Data
        Bundle bundle = getIntent().getExtras();
        getFromLogin_UID = bundle.getString("ID");
        getFromLogin_USERNAME = bundle.getString("USERNAME");

        //boolean
        deleteAccount = false;
        editAccount = false;
    }

    public void editContact(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure to Change Your Profile ?");
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent W = new Intent(ProfileActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", getFromLogin_UID);
                bundle.putString("USERNAME", getFromLogin_USERNAME);
                W.putExtras(bundle);
                startActivity(W);
                finish();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void callme(View view) {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneData));
        startActivity(call);
    }
    public void callparrent(View view) {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneparrentData));
        startActivity(call);
    }

    public void viewMember(View view){
        Intent i = new Intent(ProfileActivity.this, ListContactActivity.class);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuItem = getMenuInflater();
        menuItem.inflate(R.menu.menu_menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are You Sure to Change Your Profile ?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent W = new Intent(ProfileActivity.this, EditActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", getFromLogin_UID);
                        bundle.putString("USERNAME", getFromLogin_USERNAME);
                        W.putExtras(bundle);
                        startActivity(W);
                        finish();
                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.delete:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setMessage("Are You Sure to Delete Your Profile ?");
                builder2.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userDatabase.child("users").child(getFromLogin_USERNAME).removeValue();
                        userDatabase.child("database").child(getFromLogin_UID).removeValue();
                        Intent A = new Intent(ProfileActivity.this, LoginActivity.class);
                        A.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        A.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(A);
                        finish();
                    }
                });
                builder2.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog2 = builder2.create();
                alertDialog2.show();
                break;
            case R.id.exit_aplication:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setMessage("Are You Sure to Exit?");
                builder3.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder3.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog3 = builder3.create();
                alertDialog3.show();
                break;
            case R.id.logout:
                AlertDialog.Builder asd = new AlertDialog.Builder(this);
                asd.setMessage("Are You Sure to Logout?");
                asd.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent opo = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(opo);
                        finish();
                    }
                });
                asd.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ddd = asd.create();
                ddd.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
