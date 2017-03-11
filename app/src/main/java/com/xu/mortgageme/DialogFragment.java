package com.xu.mortgageme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Omistaja on 11/03/2017.
 */

public class DialogFragment extends android.support.v4.app.DialogFragment {
    TextView dt;
    String postcode;
    Button ok;

    public DialogFragment(){

    }
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle);

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

    setStyle(STYLE_NO_TITLE, R.style.DialogStyle);
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        postcode = sp.getString("post", "W1T 4QB");
        View dialogView = inflater.inflate(R.layout.fragment_dialog, container, false);

        ok =(Button)dialogView.findViewById(R.id.btnOK);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchRecycler= new Intent(getActivity(),recyclerActivity.class);

                startActivity(launchRecycler);
                dismiss();

            }
        });


        return dialogView;


    }
}

