package com.xu.mortgageme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
//TODO saveprofile methods need more work

public class questionActivity extends AppCompatActivity {
    Button ok;
    TextView question;
    EditText answer;
    TextView seekbarView;
    TextView amountView;
    int count=0;
    boolean questions;
    SharedPreferences sp;
    double interest;

    //Following are all replies from chatbot, saved as data for methods;

    String reply;
    String name;
    int amount;
    int deposit;
    int paybackyears;
    int income;
    int numjobs;
    String postcode;
    SharedPreferences user;
    SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.barclayslogo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sb = (SeekBar)findViewById(R.id.seekBar);
        sb.setVisibility(View.INVISIBLE);
        seekbarView= (TextView)findViewById(R.id.seekbarValue);
        seekbarView.setVisibility((View.INVISIBLE));
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        sp = getSharedPreferences("mode", Context.MODE_PRIVATE);
        questions=sp.getBoolean("questions",false);
        amountView = (TextView)findViewById(R.id.amountview) ;
        amountView.setVisibility(View.INVISIBLE);

        question = (TextView)findViewById(R.id.questionView);
        answer =(EditText)findViewById(R.id.answerView);
        ok =(Button)findViewById(R.id.buttonOK);
        user= getSharedPreferences("decisions", Context.MODE_PRIVATE);


        initializeQuestion();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekbarView.setText( String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });


        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                reply =answer.getText().toString();
                checkReplyFormat(reply);

            }
        });

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (answer.isFocused()){


                    }
            }
        });



    }

    public void checkReplyFormat(String reply){
        if (answer.getVisibility()==View.VISIBLE){
            if(count ==0 || count==6){
                updateQuestion(reply);
                count +=1;
            }

            else if (count==1|| count ==2|| count==3||count==4||count ==5){
                try {
                    Integer.parseInt(reply);
                    updateQuestion(reply);
                    count +=1;


                }catch (NumberFormatException e){
                    question.setText("Invalid entry, please check your input");
                    question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                }
            }
        }else if (answer.getVisibility()==View.INVISIBLE&& count==1){
            String seekreply = seekbarView.getText().toString();
            amount =Integer.parseInt(seekbarView.getText().toString());

            updateQuestion(seekreply);
            count  +=1;
        }else if (answer.getVisibility()==View.INVISIBLE&& count==2){
            String seekreply = seekbarView.getText().toString();
            deposit =Integer.parseInt(seekbarView.getText().toString());

            updateQuestion(seekreply);
            count  +=1;
        }





    }

    public void initializeQuestion(){

        if (questions==false){
            question.setText("Hello. To start out, please enter your name");
            question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
            answer.getText().clear();
        }
        if(questions ==true){
            //Load all the user values from Preferences
            question.setText("Welcome back " + name+"!");
            question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
            answer.setVisibility(View.INVISIBLE);
            seekbarView.setVisibility(View.VISIBLE);
            sb.setVisibility(View.VISIBLE);
            amountView.setVisibility(View.VISIBLE);
            answer.getText().clear();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    question.setText("Please enter the amount you would like to request.");
                    question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));

                }
            }, 3000);

            name = user.getString("name","Error");

            income =user.getInt("income",0);
            numjobs=user.getInt("numjobs",0);



        }
    }

    public void updateQuestion(String reply) {
        if (questions == false) {
            if (count == 0) {
                name = reply;
                question.setText("Hello " + reply + " , thank you for choosing Barclays today");
                question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                answer.getText().clear();
                answer.setVisibility(View.INVISIBLE);
                seekbarView.setVisibility(View.VISIBLE);
                sb.setVisibility(View.VISIBLE);
                amountView.setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        question.setText("Please select the amount you would like to request");
                        question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                    }
                }, 3000);

            } else if (count == 1) {
                amount = Integer.parseInt(reply);
                question.setText("Please select the amount of deposit you are prepared to pay");
                question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                answer.getText().clear();
                answer.setVisibility(View.INVISIBLE);
                seekbarView.setVisibility(View.VISIBLE);
                sb.setVisibility(View.VISIBLE);
                amountView.setVisibility(View.VISIBLE);


            } else if (count == 2) {
                answer.setVisibility(View.VISIBLE);
                seekbarView.setVisibility(View.INVISIBLE);
                sb.setVisibility(View.INVISIBLE);
                amountView.setVisibility(View.INVISIBLE);
                deposit = Integer.parseInt(reply);

                question.setText("Alright, and what would you prefer as a payback period (in years)?");
                answer.getText().clear();
                question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));

            } else if (count == 3) {
                paybackyears = Integer.parseInt(reply);
                question.setText("Alright, you requested £" + amount + ", with a deposit of £"+ deposit+" and a payback time of  " + paybackyears + " years");
                answer.getText().clear();
                question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        question.setText("I just need to ask a few questions about you");
                        question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                    }
                }, 3000);
                final Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        question.setText("What is your gross annual income, to the nearest thousand?");
                        question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                    }
                }, 3000);


            } else if (count == 4) {
                income = Integer.parseInt(reply);
                question.setText("Okay. How many jobs have you held over the past three years?");
                answer.getText().clear();
                question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
            } else if (count == 5) {
                numjobs = Integer.parseInt(reply);
                question.setText("Alright. Finally, please give the postcode of the property you're interested");
                answer.getText().clear();
                question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
            } else if (count ==6){
                postcode = reply;
                question.setText("Thank you. Let me fetch you a quote. One moment...");
                answer.getText().clear();
                question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                //TODO runn methods here with all variables, save in sharedprefs
                Calculations calc = new Calculations(income,numjobs,paybackyears,amount,deposit);
                interest = calc.getInterest();
                final Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent launchquote = new Intent(questionActivity.this, quoteActivity.class);
                        launchquote.putExtra("name", name);
                        launchquote.putExtra("payback", paybackyears);
                        launchquote.putExtra("income", income);
                        launchquote.putExtra("numjobs", numjobs);
                        launchquote.putExtra("interest", interest);
                        launchquote.putExtra("post",postcode);
                        startActivity(launchquote);
                    }
                }, 5000);
            }

        } else if (questions == true) {
            //So we are using the saved profile


            } else if (count == 0) {
            amount = Integer.parseInt(reply);
            question.setText("Hello " + reply + " , thank you for choosing Barclays today");
            question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
            answer.getText().clear();
            answer.setVisibility(View.INVISIBLE);
            seekbarView.setVisibility(View.VISIBLE);
            sb.setVisibility(View.VISIBLE);
            amountView.setVisibility(View.VISIBLE);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    question.setText("Please select the amount of deposit you are prepared to pay");
                    question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
                }
            }, 3000);


            } else if (count == 1) {
            answer.setVisibility(View.VISIBLE);
            seekbarView.setVisibility(View.INVISIBLE);
            sb.setVisibility(View.INVISIBLE);
            amountView.setVisibility(View.INVISIBLE);

            amount = Integer.parseInt(reply);
            question.setText("Alright, and what would you prefer as a payback period (in years)?");
            answer.getText().clear();
            question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));

                question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));
            } else if (count == 2) {
                    paybackyears = Integer.parseInt(reply);
                    question.setText("Alright. Finally, please give the postcode of the property you're interested");
                    answer.getText().clear();
            }else if(count ==3){
            postcode = reply;
            question.setText("Thank you. Let me fetch you a quote. One moment...");
            answer.getText().clear();
            question.setAnimation(AnimationUtils.loadAnimation(questionActivity.this,android.R.anim.slide_in_left));

            //TODO begin run of Zenia's code, start with Constructor
            //int annual_income, int num_of_jobs, int duration, int loan
            Calculations calc = new Calculations(income,numjobs,paybackyears,amount,deposit);
            interest = calc.getInterest();

            question.setText(Double.toString(interest));

            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent launchquote = new Intent(questionActivity.this, quoteActivity.class);
                    launchquote.putExtra("name", name);
                    launchquote.putExtra("payback", paybackyears);
                    launchquote.putExtra("income", income);
                    launchquote.putExtra("numjobs", numjobs);
                    launchquote.putExtra("interest",interest);
                    launchquote.putExtra("post",postcode);
                    startActivity(launchquote);
                }
            }, 5000);
        }
        }
    }


