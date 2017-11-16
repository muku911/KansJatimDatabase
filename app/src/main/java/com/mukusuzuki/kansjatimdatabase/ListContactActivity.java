package com.mukusuzuki.kansjatimdatabase;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ListContactActivity extends AppCompatActivity {

    private ListView list_data;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private SearchView searchView;

    private List<User> user_list = new ArrayList<User>();
    private List<User> user_sch = new ArrayList<User>();
    private List<Search> user_popo = new ArrayList<Search>();
    private UserViewAdapter adapter, adapter2;

    private User user, pi;
    private Search searchs, popo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        //control
        list_data = (ListView)findViewById(R.id.dataContact);
        searchView = (SearchView)findViewById(R.id.searchContact);

        list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String RUDD = user_list.get(i).getUid();
                if (RUDD != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("IDM", RUDD);
                    Intent cok = new Intent(ListContactActivity.this, MemberActivity.class);
                    cok.putExtras(bundle);
                    finish();
                    startActivity(cok);
                }

            }
        });

        //firebase
        iniFirebase();
        addValueFirebaseListener();

        CharSequence sequence = searchView.getQuery();
        searchView.setQueryHint("Search....");
        boolean isIconfied = searchView.isIconfiedByDefault();

    }

    private void addValueFirebaseListener() {
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (user_list.size() > 0)
                    user_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user = snapshot.getValue(User.class);
                    user_list.add(user);
                    //
                    user_sch.add(user);
                }
                adapter = new UserViewAdapter(ListContactActivity.this,user_list);
                list_data.setAdapter(adapter);

                find();


            }

            private void find() {
                SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        adapter2 = new UserViewAdapter(ListContactActivity.this,user_sch);
                        adapter2 = adapter;
                        adapter2.filter(s.toString().trim());
                        list_data.setAdapter(adapter2);
                        return true;
                    }
                });

                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        list_data.setAdapter(adapter);
                        return true;
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });
    }

    private void iniFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
