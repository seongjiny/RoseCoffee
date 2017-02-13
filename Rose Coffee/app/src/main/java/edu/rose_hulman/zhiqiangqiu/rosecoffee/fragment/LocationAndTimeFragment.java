package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

public class LocationAndTimeFragment extends Fragment {
    ConfirmAndCheckOutFragment mConfirmFragment;
    public LocationAndTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_and_time, container, false);
        TextView locationTextView = (TextView) view.findViewById(R.id.location_text_view);
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("Where do you want to pick up your order?");
                dialog.show();
            }
        });


        return view;
    }

    public void sendFragment(ConfirmAndCheckOutFragment confirmFragment) {
        mConfirmFragment = confirmFragment;
    }
}
