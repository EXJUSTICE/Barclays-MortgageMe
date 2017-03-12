package com.xu.mortgageme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class quoteActivity extends AppCompatActivity {
    Button call;
    Button reset;
    Button save;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    TextView interestView;
    String name;
    int amount;
    int paybackyears;
    int income;
    int numjobs;
    String postcode;
    double interest;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;
    boolean shown =false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.barclayslogo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        call  = (Button)findViewById(R.id.btncall);
        reset = (Button)findViewById(R.id.btnreset);
        save = (Button)findViewById(R.id.btnsave);
        interestView= (TextView)findViewById(R.id.amountView);
        //Code for saving profiles
        Intent intent =getIntent();
        name=intent.getStringExtra("name");
        paybackyears=intent.getIntExtra("payback",0);
        income =intent.getIntExtra("income",0);
        numjobs=intent.getIntExtra("numjobs",0);
        interest= intent.getDoubleExtra("interest",0D);
        postcode = intent.getStringExtra("post");

        sp = getSharedPreferences("decisions", Context.MODE_PRIVATE);
        edit = sp.edit();

        //String rawinterest =Double.toString(interest)+"%"

        String.format("%.2f", interest);


            interestView.setText(String.format("%.2f", interest)+"%");

        call.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (ContextCompat.checkSelfPermission(quoteActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(quoteActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);}
                else{
                    Intent phoneIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+44)800400100"));


                    startActivity(phoneIntent);
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(shown ==false){

                    SharedPreferences sp = getSharedPreferences("user",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sp.edit();
                    editor.putString("post",postcode);
                    editor.commit();

                    DialogFragment df = new DialogFragment();

                    df.show(getSupportFragmentManager(),"Nearby Services");



                }else{
                    Intent goHome = new Intent(quoteActivity.this, MainActivity.class);
                    startActivity(goHome);
                }

                shown =true;


            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.putString("name",name);
                edit.putInt("payback",paybackyears);
                edit.putInt("income",income);
                edit.putInt("numjobs",numjobs);
                edit.commit();
                Toast.makeText(quoteActivity.this,"Profile saved",Toast.LENGTH_LONG).show();


            }
        });

    }








}
