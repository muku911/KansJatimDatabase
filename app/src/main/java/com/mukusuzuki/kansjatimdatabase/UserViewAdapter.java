package com.mukusuzuki.kansjatimdatabase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by Muku Suzuki on 7/31/2017.
 */

public class UserViewAdapter extends BaseAdapter {

    Activity activity;
    private List<User> listUsers;
    private ArrayAdapter<User> arraylist;
    private LayoutInflater inflater;

    public Context contextS;
    private ArrayList<User> arraySearch;


    public UserViewAdapter(Activity activity, List<User> listUsers) {
        this.activity = activity;
        this.listUsers = listUsers;

        arraySearch = new ArrayList<User>();
        arraySearch.addAll(listUsers);
    }

    @Override
    public int getCount() {
        return listUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return listUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater  = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item, null);

        TextView user_name = (TextView)itemView.findViewById(R.id.tx_nameView);
        TextView user_phone = (TextView)itemView.findViewById(R.id.tx_phoneView);
        TextView user_university = (TextView)itemView.findViewById(R.id.tx_universityVIew);

        user_name.setText(listUsers.get(i).getName());
        user_phone.setText(listUsers.get(i).getPhone());
        user_university.setText(listUsers.get(i).getUniversity());

        return itemView;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        listUsers.clear();

        if (charText.length() == 0){
            listUsers.addAll(arraySearch);
        }else{
            for (User userDetail : arraySearch){
                if (charText.length() != 0 && userDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    listUsers.add(userDetail);
                } else if (charText.length() != 0 && userDetail.getUniversity().toLowerCase(Locale.getDefault()).contains(charText)){
                    listUsers.add(userDetail);
                }else if (charText.length() != 0 && userDetail.getPhone().toLowerCase(Locale.getDefault()).contains(charText)){
                    listUsers.add(userDetail);
                }
            }
        }

        notifyDataSetChanged();
    }
}
