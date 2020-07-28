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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class variables extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText editText[]=new EditText[10];
    private DrawerLayout drawer;
    LinearLayout linearLayout;
    TextView textView;
    ArrayList<Integer> list;
    Button button;
    LinearLayout.LayoutParams editTextLayoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variables);
        Toolbar toolbar=findViewById(R.id.toolbar_variables);
        drawer=findViewById(R.id.drawer_layout_variables);
        button=findViewById(R.id.buttonVariables);
        NavigationView navigationView=findViewById(R.id.nav_view_variables);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final Context context=this;
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to add these variables")
                        .setTitle("Confirmation")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                updateVariables();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("varList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=sharedPreferences.getString("list",null);
        if(json==null)
        {
            Toast.makeText(this, "default values set", Toast.LENGTH_SHORT).show();
            list=new ArrayList<Integer>();
            list.add(10000);
            list.add(2000);
            list.add(2000);
            list.add(4000);
            list.add(6000);
            json=gson.toJson(list);
            editor.putString("list",json);
            editor.apply();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else {
            Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
            list = gson.fromJson(json, type);
            linearLayout = findViewById(R.id.linearLayoutVariables);
            linearLayout.setHorizontalGravity(1);
            editTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            editTextLayoutParams.setMargins(0, 25, 0, 0);
            //bank_bal
            textView = new TextView(this);
            textView.setText("bank_bal");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(15);
            textView.setTextColor(Color.parseColor("#000000"));
            editText[0] = new EditText(this);
            editText[0].setText(String.valueOf(list.get(0)));
            editText[0].setTextSize(20);
            editText[0].setGravity(Gravity.CENTER_HORIZONTAL);
            editText[0].setLayoutParams(editTextLayoutParams);
            linearLayout.addView(editText[0]);
            linearLayout.addView(textView);
            //paytm_ball
            textView = new TextView(this);
            textView.setText("paytm_bal");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(15);
            textView.setTextColor(Color.parseColor("#000000"));
            editText[1] = new EditText(this);
            editText[1].setText(String.valueOf(list.get(1)));
            editText[1].setTextSize(20);
            editText[1].setGravity(Gravity.CENTER_HORIZONTAL);
            editText[1].setLayoutParams(editTextLayoutParams);
            linearLayout.addView(editText[1]);
            linearLayout.addView(textView);
            //montly_warning
            textView = new TextView(this);
            textView.setText("monthly_warning");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(15);
            textView.setTextColor(Color.parseColor("#000000"));
            editText[2] = new EditText(this);
            editText[2].setText(String.valueOf(list.get(2)));
            editText[2].setTextSize(20);
            editText[2].setLayoutParams(editTextLayoutParams);
            editText[2].setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(editText[2]);
            linearLayout.addView(textView);
            //montly_limit
            textView = new TextView(this);
            textView.setText("monthly_limit");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(15);
            textView.setTextColor(Color.parseColor("#000000"));
            editText[3] = new EditText(this);
            editText[3].setText(String.valueOf(list.get(3)));
            editText[3].setTextSize(20);
            editText[3].setLayoutParams(editTextLayoutParams);
            editText[3].setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(editText[3]);
            linearLayout.addView(textView);
            //absolute_limit
            textView = new TextView(this);
            textView.setText("absolute_limit");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(15);
            textView.setTextColor(Color.parseColor("#000000"));
            editText[4] = new EditText(this);
            editText[4].setText(String.valueOf(list.get(4)));
            editText[4].setTextSize(20);
            editText[4].setLayoutParams(editTextLayoutParams);
            editText[4].setGravity(Gravity.CENTER_HORIZONTAL);
            linearLayout.addView(editText[4]);
            linearLayout.addView(textView);
        }
    }
    //updates variables when submit button is clicked
    public void updateVariables()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("varList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        list.set(0,Integer.parseInt(editText[0].getText().toString()));
        list.set(1,Integer.parseInt(editText[1].getText().toString()));
        list.set(2,Integer.parseInt(editText[2].getText().toString()));
        list.set(3,Integer.parseInt(editText[3].getText().toString()));
        list.set(4,Integer.parseInt(editText[4].getText().toString()));
        String json=gson.toJson(list);
        editor.putString("list",json);
        editor.apply();
        Toast.makeText(this, "saved variables :)", Toast.LENGTH_SHORT).show();
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