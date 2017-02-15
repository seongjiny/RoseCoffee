package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

public class LocationAndTimeFragment extends Fragment {
    Order mOrder;
    public LocationAndTimeFragment() {
        // Required empty public constructor
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location_and_time, container, false);
        final TextView locationTextView = (TextView) rootView.findViewById(R.id.location_text_view);
        mOrder = ((MainActivity)getActivity()).getOrder();
        String location="";
        String time ="";
        if((location =mOrder.getLocation())!=null){
            locationTextView.setText(mOrder.getLocation());
        }
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_location,null,false);
                builder.setView(view);
                final EditText editLocation = (EditText) view.findViewById(R.id.location_name);
                builder
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String locationName = editLocation.getText().toString();
                                locationTextView.setText(locationName);
                                mOrder.setLocation(locationName);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel,null);



                builder.create().show();
            }
        });

        TimePicker timePicker = (TimePicker) rootView.findViewById(R.id.cus_main_time_picker);
        if((time = mOrder.getTime())!=null){
            int index = time.indexOf(":");
            int hour = Integer.parseInt(time.substring(0,index));
            int minute = Integer.parseInt(time.substring(index+1));
            timePicker.setHour(hour);
            timePicker.setMinute(minute);

        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mOrder.setTime(hourOfDay+":"+minute);
            }
        });


        return rootView;
    }

}
