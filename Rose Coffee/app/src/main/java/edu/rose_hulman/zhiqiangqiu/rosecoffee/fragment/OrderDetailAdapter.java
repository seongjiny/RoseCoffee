package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Drink;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MenuDrink;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MenuItem;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Snack;

/**
 * Created by yoons1 on 2/12/2017.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<MenuItem> mMenuItems = new ArrayList<>();
    private Order mOrder;
    private HashMap<String, Double> mAllSnacks;
    HashMap<String, MenuDrink> mAllDrinks;
    private ConfirmAndCheckOutFragment mConfirmFragment;
    public OrderDetailAdapter(Context context,RecyclerView recyclerView,Order order,ConfirmAndCheckOutFragment confirmFragment){
        mContext = context;
        mRecyclerView = recyclerView;
        mOrder = order;
        mAllSnacks = ((MainActivity)mContext).getSnacks();
        mAllDrinks = ((MainActivity)mContext).getDrinks();
        mConfirmFragment = confirmFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuItem menuItem = mMenuItems.get(position);
        holder.mMenuNameTextView.setText(menuItem.getName());
    }
    private void undo(final MenuItem clearedItem,final int position) {

        Snackbar snackbar = Snackbar
                .make(mRecyclerView, Constants.ITEM_REMOVED,Snackbar.LENGTH_LONG)
                .setAction(Constants.UNDO,new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        addMenuItem(position,clearedItem);
                    }
                });
        snackbar.show();
    }
    public void removeMenuItem(int position){
        MenuItem clearedItem = mMenuItems.get(position);
        undo(clearedItem,position);
        mMenuItems.remove(position);
        notifyDataSetChanged();
        updateOrder();
    }

    private void updateOrder() {
        ArrayList<Drink> newDrinks = new ArrayList<>();
        ArrayList<String> newSnacks = new ArrayList<>();
        double totalPrice = 0.0;
        for(MenuItem m:mMenuItems){
            if(m instanceof Drink){
                newDrinks.add((Drink)m);
                MenuDrink menuDrink = mAllDrinks.get(m.getName());
                if(m.getSize().equals(Constants.SIZE_LARGE)){
                    totalPrice+=menuDrink.getPriceLarge();
                }else if(m.getSize().equals(Constants.SIZE_MEDIUM)){
                    totalPrice+=menuDrink.getPriceMedium();
                }else{
                    totalPrice+=menuDrink.getPriceSmall();
                }
            }else{
                newSnacks.add(m.getName());
                totalPrice+=mAllSnacks.get(m.getName());
            }
        }
        totalPrice= Double.parseDouble(String.format("%,02f",totalPrice));
        mOrder.setDrinks(newDrinks);
        mOrder.setSnacks(newSnacks);
        mOrder.setTotalPrice(totalPrice);
        mConfirmFragment.updateOrder();
    }


    public void editMenuItem(int position, MenuItem menuItem){
        mMenuItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mMenuItems.size());
        addMenuItem(position,menuItem);
        //above line will call updateOrder();
    }
    public void addMenuItem(MenuItem menuItem){
        mMenuItems.add(0, menuItem);
        notifyDataSetChanged();
        updateOrder();
    }
    public void addMenuItem(int position,MenuItem menuItem){
        mMenuItems.add(position, menuItem);
        notifyDataSetChanged();
        updateOrder();
    }
    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        private TextView mMenuNameTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mMenuItems.get(getAdapterPosition()) instanceof Drink){
                        editDrink(getAdapterPosition());
                    }else{
                        editSnack(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    removeMenuItem(getAdapterPosition());
                    return false;
                }
            });
            mMenuNameTextView = (TextView) itemView.findViewById(R.id.menu_item_text_view);
        }

        @Override
        public void onClick(View v) {
            if(mMenuItems.get(getAdapterPosition()) instanceof Drink){
                editDrink(getAdapterPosition());
            }else{
                editSnack(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            removeMenuItem(getAdapterPosition());
            return false;
        }
    }
    public void editDrink(final int position){
        MenuItem currentItem = mMenuItems.get(position);
        final Dialog mydialog = new Dialog(mContext);
        mydialog.setContentView(R.layout.dialog_add_drink);
        final Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_name_spinner);
        final Spinner sizeSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_size_spinner);
        List<String> list = new ArrayList(mAllDrinks.keySet());
        String[] drinkNames = new String[list.size()];
        drinkNames = list.toArray(drinkNames);
        String[] sizeNames = Constants.SIZE_NAMES;
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,drinkNames);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,sizeNames);

        nameSpinner.setAdapter(adapter1);
        sizeSpinner.setAdapter(adapter2);
        int drinkIndex = Arrays.asList(drinkNames).indexOf(currentItem.getName());
        nameSpinner.setSelection(drinkIndex);
        int sizeIndex = Arrays.asList(sizeNames).indexOf(currentItem.getSize());
        sizeSpinner.setSelection(sizeIndex);
        final EditText editComment = (EditText)mydialog.findViewById(R.id.add_drink_edit_text);
        editComment.setText(currentItem.getComment());


        Button buttonOK = (Button) mydialog.findViewById(R.id.add_drink_ok);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drink drink = new Drink(
                        nameSpinner.getSelectedItem().toString(),
                        sizeSpinner.getSelectedItem().toString(),
                        editComment.getText()+"");
                editMenuItem(position,drink);
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
    public void editSnack(final int position){
        final Dialog mydialog = new Dialog(mContext);
        mydialog.setContentView(R.layout.dialog_add_snack);
        final Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_snack_name_spinner);

        List<String> list = new ArrayList(mAllSnacks.keySet());
        String[] snackNames = new String[list.size()];
        snackNames = list.toArray(snackNames);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,snackNames);
        nameSpinner.setAdapter(adapter1);
        int snackIndex= Arrays.asList(snackNames).indexOf(mMenuItems.get(position).getName());
        nameSpinner.setSelection(snackIndex);

        Button buttonOK = (Button) mydialog.findViewById(R.id.add_snack_ok);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snack snack = new Snack(nameSpinner.getSelectedItem().toString());
                editMenuItem(position,snack);
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
}
