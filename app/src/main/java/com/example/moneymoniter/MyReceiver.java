package com.example.moneymoniter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.jar.Attributes;

public class MyReceiver extends BroadcastReceiver {
    public static final String pdu_type="pdus";
    ArrayList<String> NameList;
    String strMessage="";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle=intent.getExtras();
        SmsMessage[] msgs;
        String format=bundle.getString("format");
        Object[] pdus=(Object[]) bundle.get(pdu_type);
        SharedPreferences sharedPreferences=context.getSharedPreferences("BankNameList",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=sharedPreferences.getString("NameList",null);
        if (json==null)
            Toast.makeText(context, "o h n o", Toast.LENGTH_SHORT).show();
        Type type=new TypeToken<ArrayList<String>>(){}.getType();
        NameList=gson.fromJson(json,type);
        if(pdus!=null)
        {
            msgs=new SmsMessage[pdus.length];
            for(int i=0;i<msgs.length;i++)
            {
                msgs[i]=SmsMessage.createFromPdu((byte []) pdus[i],format);
                strMessage+="sms from" + msgs[i].getOriginatingAddress();
                strMessage+=":"+ msgs[i].getMessageBody()+"\n";
                datas data=new datas();
                String body=msgs[i].getMessageBody().toString();
                data.date= Calendar.getInstance().getTime();
                int startingPos=0;
                int endingPos=0;
                double amount=0;
                boolean flag=false;
                int startingPosx=0;
                int temp;
                String sender=msgs[i].getOriginatingAddress();
                data.setSender(msgs[i].getOriginatingAddress().toString());
                //for loop for checking if one of namelist array elements matches the sender
                if(!body.contains("requested"))
                {
                    if (body.contains("Rs") || body.contains("INR"))
                    {
                        for (int k = 0; k < NameList.size(); k++)
                        {
                            //its necessary to be "contains" as bank and upi senders generally send
                            // sms with multiple diff sender names ;JDSBIUPI,ADSBIUPI,SBI-IINB
                            // so we check if the sender contains a key like SBI hence generalizing all these threads
                            if (sender.contains(NameList.get(k)))
                            {
                                //we extract the amount by scanning position of Rs and iteratively check for numbers after Rs as number always follow after Rs or INR
                                if (body.contains("Rs"))
                                    startingPosx = body.indexOf("Rs");
                                else if (body.contains("INR"))
                                    startingPosx = body.indexOf("INR");
                                for (int j = startingPosx; j < body.length(); j++)
                                {
                                    if (Character.isDigit(body.charAt(j)))
                                    {
                                        startingPos = j;
                                        endingPos = j;
                                        while (Character.isDigit(body.charAt(endingPos)))
                                        {
                                            // System.out.println(body.charAt(endingPos)+" "+endingPos);
                                            endingPos++;
                                            if (endingPos >= body.length())
                                                break;
                                        }
                                        endingPos--;
                                        break;
                                    }
                                }
                                //to convert from starting pos to ending pos the string into a double
                                for (int j = endingPos; j >= startingPos; j--)
                                    amount += ((double) (body.charAt(j) - '0')) * (Math.pow(10, endingPos - j));
                                //this is for wallet functionality where it checks if the sender is the source of the wallet
                                //and if the amount is a angel number like 111,222 etc...
                                //and if the debited keyword is present as it will add this amount to paytm_bal and subtract it from bank_bal
                                if (sender.contains(NameList.get(1)) && body.contains("debited"))
                                {
                                    temp = (int) amount;
                                    int angel=temp%10;
                                    data.isperfect=true;
                                    while (temp != 0)
                                    {
                                        if(angel!=temp%10)
                                        {
                                            data.isperfect=false;
                                            break;
                                        }
                                        temp=temp/10;
                                    }
                                }
                                data.debited = false;
                                if (body.contains("Paid"))
                                    data.debited = true;
                                else if (body.contains("debited"))
                                    data.debited = true;
                                else if (body.contains("transferred to"))
                                    data.debited = true;
                                else if (body.contains("credited"))
                                    data.debited = false;
                                else
                                    break;
                                data.setAmount(amount);
                                MainActivity.GetDataFromBroadcastReceiver(data, context);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
