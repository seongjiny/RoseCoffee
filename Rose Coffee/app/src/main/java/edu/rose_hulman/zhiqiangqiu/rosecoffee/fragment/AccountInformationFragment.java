package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInformationFragment extends Fragment {



    public AccountInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_information, container, false);
    }

    public final void showDialog(){
        final Dialog mydialog = new Dialog(getActivity());
        mydialog.setTitle("testing");
        mydialog.setContentView(R.layout.dialog_add_drink);
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
