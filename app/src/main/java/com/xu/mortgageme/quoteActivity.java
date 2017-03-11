package com.xu.mortgageme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class quoteActivity extends AppCompatActivity {
    Button call;
    Button reset;
    SharedPreferences sp;
    Float interest;
    TextView interestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        call  = (Button)findViewById(R.id.btncall);
        reset = (Button)findViewById(R.id.btnreset);
        interestView= (TextView)findViewById(R.id.amountView);

        sp = getSharedPreferences("decisions", Context.MODE_PRIVATE);
        interest =sp.getFloat("interest",100F);
        if (interest == 100F){
            interestView.setText("Error 404");
        }else{
            interestView.setText(Float.toString(interest)+"%");
        }
        call.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent phoneIntent=new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:+44800 400100"));
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent goHome = new Intent(quoteActivity.this, MainActivity.class);
                startActivity(goHome);

            }
        });




    }

}
