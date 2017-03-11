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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class recyclerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;
    ServiceAdapter mAdapter;
    ArrayList<String>names;
    ArrayList<String>types;
    ArrayList<String>numbers;
    SharedPreferences sp;
    String postcode;
    TextView services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.barclayslogo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        names = new ArrayList<>();
        types= new ArrayList<>();
        numbers = new ArrayList<>();
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        postcode = sp.getString("post","M60 7RA");
        services =(TextView)findViewById(R.id.servicesclose);
        services.setText(postcode);
        initializeLists();


        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        //http://stackoverflow.com/questions/24618829/how-to-add-dividers-and-spaces-between-items-in-recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Dummy data, create arraylists

        if (mAdapter == null) {
            mAdapter = new  ServiceAdapter(names,types,numbers);
            recyclerView.setAdapter(mAdapter);
        } else {
            recyclerView.invalidate();
            mAdapter.notifyDataSetChanged();


        }


    }
    public void initializeLists(){
        names.add("Rylands of Manchester");
        names.add("Small Removals Manchester");
        names.add("Movingeverywhere Manchester");
        names.add("Celeste Interior Design");
        names.add("Manchester's Plumbers");
        names.add("Manchester Electric");
        names.add("J.D Electricians");


        types.add("Moving/Relocation service");
        types.add("Moving/Relocation service");
        types.add("Moving/Relocation service");
        types.add("Interior Design service");
        types.add("Plumbers");
        types.add("Electricians");
        types.add("Electricians");

        numbers.add("+4401614510136");
        numbers.add("+4407944079878");
        numbers.add("+4401612388915");

        numbers.add("+47585 619591");
        numbers.add("+441613123229");
        numbers.add("+441618501610");
        numbers.add("+441618659772");

    }

    private class ServiceAdapter  extends RecyclerView.Adapter<ServiceHolder>{
        private ArrayList<String>servicenames;
        private ArrayList<String> servicetype;
        private ArrayList<String> servicenumbers;



        public ServiceAdapter(ArrayList<String>names,ArrayList<String> servicetypes,ArrayList<String>phonnumbers){

            this.servicenames=names;
            this.servicetype=servicetypes;
            this.servicenumbers = phonnumbers;
        }

        @Override
        public ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutinflater = LayoutInflater.from(recyclerActivity.this);
            View view= layoutinflater.inflate(R.layout.row,parent,false);
            return new ServiceHolder (view);
        }

        //Bind datato stockholder depending on position in arraylist
        public void onBindViewHolder(ServiceHolder holder, int position){
            String name = servicenames.get(position);
            String type =servicetype.get(position);
            String number = servicenumbers.get(position);
            holder.bindService(name,type,number);
        }

        @Override
        public int getItemCount (){
            return servicenames.size();
        }
    }


    private class ServiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private String servicename;
        private String serviceType;
        private String servicenumber;
        private TextView nametextView;
        private TextView typetextView;

        public ServiceHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            nametextView =(TextView)itemView.findViewById(R.id.name);
            typetextView= (TextView)itemView.findViewById(R.id.type);
        }

        @Override
        public void onClick(View v){
            if (ContextCompat.checkSelfPermission(recyclerActivity.this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(recyclerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);}
            else{
                Intent phoneIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + servicenumber));


                startActivity(phoneIntent);
            }
            //Animations?


        }
        //Actual binder method, maybe add a current

        public void bindService(String sname, String stype, String snum){
            this.servicename=sname;
            this.serviceType = stype;
            this.servicenumber=snum;

            nametextView.setText(servicename);
            typetextView.setText(serviceType);
        }
    }

}
