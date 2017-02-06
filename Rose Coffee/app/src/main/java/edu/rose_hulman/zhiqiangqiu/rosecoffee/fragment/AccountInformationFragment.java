package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
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
        mIsDeliverySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be true if the switch is in the On position
                if (isChecked) {
                    mUser.setmIsCustomer(true);
                } else {

                }
            }
        });



        return view;
    }

    public final void showDialog(String name){
        final Dialog mydialog = new Dialog(getActivity());
        mydialog.setTitle("Edit "+name);
        mydialog.setContentView(R.layout.dialog_edit_account_info);
        Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_name_spinner);
        Spinner sizeSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_size_spinner);
        String[] drinkNames = new String[]{"Strawberry Frappuccino","Cappuccino","Cafe Latte"};
        String[] sizeNames = new String[]{"Venti","Grande","Tall"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,drinkNames);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,sizeNames);
        nameSpinner.setAdapter(adapter1);
        sizeSpinner.setAdapter(adapter2);
        mydialog.show();
    }



}
