package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Drink;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MenuDrink;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Snack;

public class OrderDetailFragment extends Fragment {
    private OrderDetailAdapter mAdapter;
    private HashMap<String, MenuDrink> mAllDrinks = new HashMap<>();
    private HashMap<String, Double> mAllSnacks = new HashMap<>();
    private ConfirmAndCheckOutFragment mConfirmFragment;
    private Order mOrders;

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
        mOrders = ((MainActivity)getActivity()).getOrder();
        mAllDrinks = ((MainActivity)getActivity()).getDrinks();
        mAllSnacks = ((MainActivity)getActivity()).getSnacks();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.order_detail_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new OrderDetailAdapter(((MainActivity)getActivity()),recyclerView,mOrders,mConfirmFragment);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    private final void showAddDrinkDialog() {
        final Dialog mydialog = new Dialog(getActivity());
        mydialog.setContentView(R.layout.dialog_add_drink);
        final Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_name_spinner);
        final Spinner sizeSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_size_spinner);

        List<String> list = new ArrayList(mAllDrinks.keySet());
        String[] drinkNames = new String[list.size()];
        drinkNames = list.toArray(drinkNames);
        String[] sizeNames = Constants.SIZE_NAMES;
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,drinkNames);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,sizeNames);
        final EditText editComment = (EditText)mydialog.findViewById(R.id.add_drink_edit_text);
        nameSpinner.setAdapter(adapter1);
        sizeSpinner.setAdapter(adapter2);
        Button button = (Button) mydialog.findViewById(R.id.add_drink_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drink drink = new Drink(
                        nameSpinner.getSelectedItem().toString(),
                        sizeSpinner.getSelectedItem().toString(),
                        editComment.getText()+"");
                mAdapter.addMenuItem(drink);
                mydialog.dismiss();
            }
        });
        Button buttonCancel = (Button) mydialog.findViewById(R.id.add_drink_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        mydialog.show();
    }

    public final void showAddSnackDialog(){
        final Dialog mydialog = new Dialog(getActivity());
        mydialog.setContentView(R.layout.dialog_add_snack);
        final Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_snack_name_spinner);
        List<String> list = new ArrayList(mAllSnacks.keySet());
        String[] snackNames = new String[list.size()];
        snackNames = list.toArray(snackNames);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,snackNames);
        nameSpinner.setAdapter(adapter1);
        Button button = (Button) mydialog.findViewById(R.id.add_snack_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snack snack = new Snack(nameSpinner.getSelectedItem().toString());
                mAdapter.addMenuItem(snack);
                mydialog.dismiss();
            }
        });
        Button buttonCancel = (Button) mydialog.findViewById(R.id.add_snack_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        mydialog.show();
    }
    public void receiveConfirmFragment(ConfirmAndCheckOutFragment fragment){
        mConfirmFragment = fragment;
    }

}
