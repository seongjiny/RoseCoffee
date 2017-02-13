package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import edu.rose_hulman.zhiqiangqiu.rosecoffee.Drink;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MenuItem;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Snack;

/**
 * Created by yoons1 on 2/12/2017.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>{
    private ConfirmAndCheckOutFragment mConfirmFragment;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<MenuItem> mMenuItems = new ArrayList<>();

    public OrderDetailAdapter(Context context,RecyclerView recyclerView,ConfirmAndCheckOutFragment confirmFragment){
        mContext = context;
        mRecyclerView = recyclerView;
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
                .make(mRecyclerView,"Item Removed",Snackbar.LENGTH_LONG)
                .setAction("UNDO",new View.OnClickListener(){
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
        mConfirmFragment.editOrderInformation(mMenuItems);
    }



    public void editMenuItem(int position, MenuItem menuItem){
        MenuItem clearedItem = mMenuItems.get(position);
        mMenuItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mMenuItems.size());
        addMenuItem(position,menuItem);
        mConfirmFragment.editOrderInformation(mMenuItems);
    }
    public void addMenuItem(MenuItem menuItem){
        mMenuItems.add(0, menuItem);
        notifyDataSetChanged();
        mConfirmFragment.editOrderInformation(mMenuItems);
    }
    public void addMenuItem(int position,MenuItem menuItem){
        mMenuItems.add(position, menuItem);
        notifyDataSetChanged();
        mConfirmFragment.editOrderInformation(mMenuItems);
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
        String[] drinkNames = new String[]{"Strawberry Frappuccino","Cappuccino","Cafe Latte"};
        String[] sizeNames = new String[]{"Venti","Grande","Tall"};
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
        String[] snackNames = new String[]{"Muffin","Apple Sauce","Chips"};
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
                Log.d("d","cancel");
                mydialog.dismiss();
            }
        });
        mydialog.show();
    }
}
