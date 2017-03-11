package com.xu.mortgageme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//TODO make save profile.

public class questionActivity extends AppCompatActivity {
    Button ok;
    TextView question;
    TextView answer;
    int count=0;

    //Following are all replies from chatbot, saved as data for methods;
    String reply;
    int amount;
    int paybackyears;
    int income;
    int numjobs;
    String postcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        question = (TextView)findViewById(R.id.questionView);
        answer =(TextView)findViewById(R.id.answerView);
        ok =(Button)findViewById(R.id.buttonOK);

        initializeQuestion();

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                reply =answer.getText().toString();
                checkReplyFormat(reply);

            }
        });



    }

    public void checkReplyFormat(String reply){
        if(count ==0 || count==5){
            updateQuestion(reply);
            count +=1;
        }

        else if (count==1|| count ==2|| count==3||count==4){
            try {
                Integer.parseInt(reply);
                updateQuestion(reply);
                count +=1;

            }catch (NumberFormatException e){
                answer.setText("Invalid entry, please check your input");
            }
        }




    }

    public void initializeQuestion(){
        question.setText("Please enter your name");
    }

    public void updateQuestion(String reply){
        if (count  ==0){
            question.setText("Hello "+reply+ " , thank you for choosing Barclays today");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    question.setText("Please enter the amount you would like to request");
                }
            },3000);

        }
        else if (count ==1){

            amount =Integer.parseInt(reply);
            question.setText("Alright, and what would you prefere as a payback period?");

        }

        else if( count ==2){
            paybackyears=Integer.parseInt(reply);
            question.setText("Alright, you requested " + reply + ", paid back in " + paybackyears + " years");
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable(){
                @Override
                public void run(){
                    question.setText("I just need to ask a few questions about you");
                }
            },3000);
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable(){
                @Override
                public void run(){
                    question.setText("What is your gross annual income, to the nearest thousand?");
                }
            },3000);


        }

        else if(count ==3){
            income = Integer.parseInt(reply);
            question.setText("Okay. How many jobs have you held over the past three years?");

        }

        else if (count ==4){
            numjobs = Integer.parseInt(reply);
            question.setText("Alright. Finally, please give the postcode of the property you're interested");
        }

        else if(count ==5){
            postcode = reply;
            question.setText("Thank you. Let me fetch you a quote. One moment...");
            //TODO runn methods here with all variables, save in sharedprefs
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable(){
                @Override
                public void run(){
                    Intent launchquote = new Intent (questionActivity.this, quoteActivity.class);
                }
            },5000);
        }

    }

}
