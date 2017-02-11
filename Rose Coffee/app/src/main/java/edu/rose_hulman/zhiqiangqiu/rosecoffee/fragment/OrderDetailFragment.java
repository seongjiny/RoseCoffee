package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

public class OrderDetailFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        RelativeLayout addDrinkButton = (RelativeLayout) view.findViewById(R.id.order_detail_add_drink_layout);
        addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDrinkDialog();
            }
        });
        RelativeLayout addSnackButton = (RelativeLayout) view.findViewById(R.id.order_detail_add_snack_layout);
        addSnackButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showAddSnackDialog();
            }
        });
        return view;
    }

    private final void showAddDrinkDialog() {
        final Dialog mydialog = new Dialog(getActivity());
        mydialog.setContentView(R.layout.dialog_add_drink);
        Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_name_spinner);
        Spinner sizeSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_size_spinner);
        String[] drinkNames = new String[]{"Strawberry Frappuccino","Cappuccino","Cafe Latte"};
        String[] sizeNames = new String[]{"Venti","Grande","Tall"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,drinkNames);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,sizeNames);

        nameSpinner.setAdapter(adapter1);
        sizeSpinner.setAdapter(adapter2);
        Button button = (Button) mydialog.findViewById(R.id.add_drink_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("dd","ok");
                mydialog.dismiss();
            }
        });
        Button buttonCancel = (Button) mydialog.findViewById(R.id.add_drink_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("d","cancel");
                mydialog.dismiss();
            }
        });
        mydialog.show();
    }

    public final void showAddSnackDialog(){
        final Dialog mydialog = new Dialog(getActivity());
        mydialog.setContentView(R.layout.dialog_add_snack);
        final Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_snack_name_spinner);
        String[] drinkNames = new String[]{"Muffin","Apple Sauce","Chips"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,drinkNames);
        nameSpinner.setAdapter(adapter1);
        Button button = (Button) mydialog.findViewById(R.id.add_snack_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AA",nameSpinner.getSelectedItem()+"");
                mydialog.dismiss();
            }
        });
        Button buttonCancel = (Button) mydialog.findViewById(R.id.add_snack_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("d","cancel");
                mydialog.dismiss();
            }
        });
        mydialog.show();
    }

    public void onClick(View v){
        showAddDrinkDialog();
    }
}
