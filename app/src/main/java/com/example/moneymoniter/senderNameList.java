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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.jar.Attributes;

public class senderNameList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText first;
    EditText second;
    EditText third;
    EditText fourth;
    Button button;
    ArrayList<String> nameList;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_name_list);
        Toolbar toolbar=findViewById(R.id.toolbar_senderNameList);
        drawer=findViewById(R.id.drawer_layout_senderNameList);
        first=findViewById(R.id.firstEditText);
        second=findViewById(R.id.secondEditText);
        third=findViewById(R.id.thirdEditText);
        button=findViewById(R.id.buttonSenderNameList);
        fourth=findViewById(R.id.fourthEditText);
        NavigationView navigationView=findViewById(R.id.nav_view_senderNameList);
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
                builder.setMessage("Are you sure you want to add senderNameList")
                        .setTitle("Confirmation")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                updateNameList();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("BankNameList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=sharedPreferences.getString("NameList",null);
        if(json==null)
        {
            nameList=new ArrayList<String>();
            nameList.add(first.getText().toString());
            nameList.add(second.getText().toString());
            nameList.add(third.getText().toString());
            nameList.add(fourth.getText().toString());
            json=gson.toJson(nameList);
            editor.putString("NameList",json);
            editor.apply();
            Toast.makeText(this, "default values set", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Type type=new TypeToken<ArrayList<String>>(){}.getType();
            nameList=gson.fromJson(json,type);
            first.setText(nameList.get(0));
            second.setText(nameList.get(1));
            third.setText(nameList.get(2));
            fourth.setText(nameList.get(3));
        }
    }
    //update the sender name list when submit button is clicked
    public void updateNameList()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("BankNameList",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=sharedPreferences.getString("NameList",null);
        if(json==null)
            Toast.makeText(this, "this is not supposed to happen", Toast.LENGTH_SHORT).show();
        else
        {
            nameList.set(0,first.getText().toString());
            nameList.set(1,second.getText().toString());
            nameList.set(2,third.getText().toString());
            nameList.set(3,fourth.getText().toString());
            json=gson.toJson(nameList);
            editor.putString("NameList",json);
            editor.apply();
            Toast.makeText(this, "saved senderNameList :)", Toast.LENGTH_SHORT).show();
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