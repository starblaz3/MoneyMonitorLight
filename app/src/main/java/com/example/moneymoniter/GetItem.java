package com.example.moneymoniter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    EditText editText1[];
    Button button;
    EditText editText2[];
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_item);
        Toolbar toolbar=findViewById(R.id.toolbar_GetItem);
        drawer=findViewById(R.id.drawer_layout_GetItem);
        button=findViewById(R.id.getItemButton);
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
            editText1=new EditText[datalist.size()];
            editText2=new EditText[datalist.size()];
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
                else if(!DateUtils.isToday(datalist.get(i).getDate().getTime()) && flag1)
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
                LinearLayout.LayoutParams paramsy=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsy.setMargins(0,0,0,60);
                editText[i] = new EditText(this);
                editText1[i]=new EditText(this);
                editText2[i]=new EditText(this);
                editText[i].setTextSize(25);
                editText[i].setText(datalist.get(i).item);
                editText[i].setHint(datalist.get(i).sender);
                editText1[i].setText(String.valueOf(datalist.get(i).isperfect));
                editText1[i].setLayoutParams(paramsy);
                editText1[i].setHint("UPI to wallet?");
                editText1[i].setGravity(Gravity.LEFT);
                editText2[i].setText( String.valueOf((int) datalist.get(i).amount));
                editText2[i].setHint("amount spent:");
                editText2[i].setTextSize(20);
                linearLayout.addView(editText[i]);
                linearLayout.addView(editText2[i]);
                linearLayout.addView(editText1[i]);
            }
            final Context context=this;
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setMessage("are you sure ?")
                            .setTitle("Confirmation")
                            .setPositiveButton("submit", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    updateSharedPreferences();
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            });
        }
    }


    //updates shared preferences when submit button is clicked
    public void updateSharedPreferences()
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
                if(!editText1[i].getText().toString().toLowerCase().contains("false") && !editText1[i].getText().toString().toLowerCase().contains("true"))
                {
                    editText1[i].setError("needs to be either true or false!");
                    return;
                }
                itemx = editText[i].getText().toString();
                datalist.get(i).item = itemx;
                System.out.println("ok");
                datalist.get(i).amount=Double.parseDouble(editText2[i].getText().toString());
                datalist.get(i).isperfect=Boolean.parseBoolean(editText1[i].getText().toString());
            }
            json = gson.toJson(datalist);
            editor.putString("datalist", json);
            editor.apply();
            Toast.makeText(this, "saved new entries :)", Toast.LENGTH_SHORT).show();
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