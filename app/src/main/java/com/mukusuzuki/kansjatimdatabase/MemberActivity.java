package com.mukusuzuki.kansjatimdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MemberActivity extends AppCompatActivity {

    private TextView text_nickname, text_name, text_birthday, text_phone, text_email,
            text_address, text_school, text_motto, text_jurusan;
    private TextView text_parrent_name, text_parrent_address, text_parrent_phone
            ,text_instagram ,text_line;

    private ImageButton to_insta, to_line;

    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabase;

    private String lineData, instaData, phoneData, phoneparrentData;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        init();
        addValueFirebaseListener();
    }

    private void addValueFirebaseListener() {
        Bundle bundle;
        bundle = getIntent().getExtras();
        userDatabase.child("database")
                .child(bundle.getString("IDM")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Database database = dataSnapshot.getValue(Database.class);
                text_name.setText(database.getName());
                text_birthday.setText(database.getBirthday());
                text_phone.setText(database.getPhone());
                text_email.setText(database.getEmail());
                text_address.setText(database.getAddress());
                text_school.setText(database.getSchool());
                text_motto.setText(database.getMotto());
                text_jurusan.setText(database.getUniversity());

                text_nickname.setText(database.getNickname());
                text_parrent_name.setText(database.getParrentname());
                text_parrent_address.setText(database.getParrentaddress());
                text_parrent_phone.setText(database.getParrentphone());
                text_instagram.setText(database.getInstagtaminfo());
                text_line.setText(database.getLineinfo());

                lineData = database.getLineinfo();
                instaData = database.getInstagtaminfo();
                phoneData = database.getPhone();
                phoneparrentData = database.getParrentphone();

                //make linkable


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

                //Linkify.addLinks(text_phone, Linkify.ALL);
                //text_phone.setMovementMethod(LinkMovementMethod.getInstance());
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
        text_name = (TextView)findViewById(R.id.tx_NameM_info);
        text_birthday = (TextView)findViewById(R.id.tx_birthdayM_info);
        text_phone = (TextView)findViewById(R.id.tx_PhoneM_info);
        text_email = (TextView)findViewById(R.id.tx_EmailM_info);
        text_address = (TextView)findViewById(R.id.tx_addressM_info);
        text_school = (TextView)findViewById(R.id.tx_GenerationM_info);
        text_motto = (TextView)findViewById(R.id.tx_isi_MottoM_info);
        text_jurusan = (TextView)findViewById(R.id.tx_jurusanM_info);

        text_nickname = (TextView)findViewById(R.id.tx_NicknameM_info);
        text_parrent_name = (TextView)findViewById(R.id.tx_name_parrentM_info);
        text_parrent_address = (TextView)findViewById(R.id.tx_address_parrentM_info);
        text_parrent_phone = (TextView)findViewById(R.id.tx_phone_parrentM_info);
        text_instagram  = (TextView)findViewById(R.id.tx_instaM_info);
        text_line = (TextView)findViewById(R.id.tx_lineM_info);

        to_insta = (ImageButton)findViewById(R.id.icon_instaM);
        to_line = (ImageButton)findViewById(R.id.icon_lineM);
    }

    public void back(View view){
        Intent op = new Intent(MemberActivity.this, ListContactActivity.class);
        startActivity(op);
        finish();
    }

    public void callmeM(View view) {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneData));
        startActivity(call);
    }
    public void callparrentM(View view) {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneparrentData));
        startActivity(call);
    }
}
