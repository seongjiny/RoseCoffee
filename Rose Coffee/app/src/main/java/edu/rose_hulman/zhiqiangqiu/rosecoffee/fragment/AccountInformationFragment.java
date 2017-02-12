package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInformationFragment extends Fragment {

    private User mUser;
    private Switch mIsDeliverySwitch;

    public AccountInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_information, container, false);
        mIsDeliverySwitch = (Switch) view.findViewById(R.id.is_delivering_switch);

        mUser = ((MainActivity) getActivity()).getUser();
        Log.d("ddd", mUser.getName());
        mIsDeliverySwitch.setChecked(mUser.isCustomer());
        mIsDeliverySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be true if the switch is in the On position
                if (mUser.isCustomer()) {
                    if (isChecked) {
                        Log.d("ddd", "User was customer. Should be changed to delivering. Actually changed to: customer");
                    } else {
                        Log.d("ddd", "User was customer. Should be changed to delivering. Actually changed to: delivery");
                    }
                }else {
                    if (isChecked) {
                        Log.d("ddd", "User was delivering. Should be changed to customer. Actually changed to: customer");
                    }else {
                        Log.d("ddd", "User was delivering. Should be changed to customer. Actually changed to: delivering");
                    }
                }
                mUser.setIsCustomer(isChecked);
            }
        });

        return view;
    }
}
