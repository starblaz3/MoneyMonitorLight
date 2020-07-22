package com.example.moneymoniter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GetItem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static ArrayList<datas> datalist;
    private DrawerLayout drawer;
    EditText editText[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_item);
        Toolbar toolbar=findViewById(R.id.toolbar_GetItem);
        drawer=findViewById(R.id.drawer_layout_GetItem);
        NavigationView navigationView=findViewById(R.id.nav_view_GetItem);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linearLayout);
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("datalist",null);
        if(json==null)
            Toast.makeText(this, "no SMS detected lol", Toast.LENGTH_SHORT).show();
        else
        {
            boolean flag=true,flag1=true;
            Type type = new TypeToken<ArrayList<datas>>() {}.getType();
            datalist = gson.fromJson(json, type);
            editText=new EditText[datalist.size()];
            Date today=Calendar.getInstance().getTime();
            for (int i = datalist.size()-1; i >= 0; i--)
            {
                //checks if the date is today and adds a textview to categorize the list items
                if(DateUtils.isToday(datalist.get(i).getDate().getTime()) && flag)
                {
                    TextView textView1=new TextView(this);
                    textView1.setText("Todays expenditure list:");
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,40,10,15);
                    textView1.setLayoutParams(params);
                    textView1.setTextSize(30);
                    linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                    textView1.setGravity(Gravity.CENTER_HORIZONTAL);
                    textView1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    View view = new View(this);
                    LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
                    params1.setMargins(0,10,0,40);
                    view.setLayoutParams(params1);
                    view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                    linearLayout.addView(textView1);
                    linearLayout.addView(view);
                    flag=false;
                }
                else if(!DateUtils.isToday(datalist.get(i).getDate().getTime())&&flag1)
                {
                    TextView textView1=new TextView(this);
                    textView1.setText("Latter expenditure list:");
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,40,10,15);
                    textView1.setLayoutParams(params);
                    textView1.setTextSize(30);
                    linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                    textView1.setGravity(Gravity.CENTER_HORIZONTAL);
                    textView1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    View view = new View(this);
                    LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
                    params1.setMargins(0,10,0,40);
                    view.setLayoutParams(params1);
                    view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                    linearLayout.addView(textView1);
                    linearLayout.addView(view);
                    flag1=false;
                }
                Spinner spinner=new Spinner(this);
                TextView textView = new TextView(this);
                editText[i] = new EditText(this);
                editText[i].setTextSize(25);
                editText[i].setText(datalist.get(i).item);
                editText[i].setHint(datalist.get(i).sender);
                textView.setText("spent  :" + String.valueOf((int) datalist.get(i).amount));
                textView.setTextSize(20);
                linearLayout.addView(editText[i]);
                linearLayout.addView(textView);
            }
        }
    }
    //updates shared preferences when submit button is clicked
    public void updateSharedPreferences(View view)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String itemx;
        String json=sharedPreferences.getString("datalist",null);
        if(json==null)
            Toast.makeText(this, "Feature only works if SMS received since app launch", Toast.LENGTH_SHORT).show();
        else
        {
            Type type = new TypeToken<ArrayList<datas>>() {}.getType();
            datalist = gson.fromJson(json, type);
            for (int i = datalist.size()-1; i >= 0; i--)
            {
                itemx = editText[i].getText().toString();
                datalist.get(i).item = itemx;
            }
            json = gson.toJson(datalist);
            editor.putString("datalist", json);
            editor.apply();
        }
    }
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
        super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.mainMenu:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.fullList:
                Intent intent1=new Intent(this,fullList.class);
                startActivity(intent1);
                break;
            case R.id.getItem:
                Intent intent2=new Intent(this,GetItem.class);
                startActivity(intent2);
                break;
            case R.id.variables:
                Intent intent3=new Intent(this,variables.class);
                startActivity(intent3);
                break;
            case R.id.deleteList:
                Intent intent4=new Intent(this,deleteList.class);
                startActivity(intent4);
                break;
            case R.id.senderNameList:
                Intent intent5=new Intent(this,senderNameList.class);
                startActivity(intent5);
                break;
            case R.id.instructions:
                Intent intent6=new Intent(this,instructions.class);
                startActivity(intent6);
                break;
            case R.id.addData:
                Intent intent7=new Intent(this,addData.class);
                startActivity(intent7);
                break;
        }
        return true;
    }
}