package com.example.jmscha14.harcourtschappel2;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFrag extends Fragment {

    Button reset = null;
    TextView clicks = null;


    public ControlFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_control, container, false);

        this.reset = (Button) root.findViewById(R.id.restart_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inti = new Intent(getActivity(),MainActivity.class);
                getActivity().startActivity(inti);
            }
        });


        this.clicks = (TextView) root.findViewById(R.id.number_of_clicks);

        return root;
    }



}
