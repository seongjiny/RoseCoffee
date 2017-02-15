package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

public class ConfirmAndCheckOutFragment extends Fragment {
    View mConfirmLayout=null;
    Order mOrder;
    ProgressBar pb;

    public ConfirmAndCheckOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_confirm_and_check_out, container, false);
        final TextView confirmButton = (TextView) view.findViewById(R.id.confrim_and_pay_text);
        mConfirmLayout = view.findViewById(R.id.confirm_detail_layout);
        pb = (ProgressBar)view.findViewById(R.id.confirm_progress);
        mOrder = ((MainActivity)getActivity()).getOrder();
        if(mOrder.getPending()){
            confirmButton.setText(Constants.PENDING_ORDER_MSG);
            pb.setVisibility(View.VISIBLE);
        }
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOrder.isOrderReady()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(Constants.CONFIRM_PAYMENT_ALERT);
                    builder
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    pb.setVisibility(View.VISIBLE);
                                    confirmButton.setText(Constants.PENDING_ORDER_MSG);
                                    confirmButton.setClickable(false);
                                    mOrder.setPending(true);
                                    sendOrderToDatabase();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel,null);



                    builder.create().show();
                }else{
                    Snackbar snackbar = Snackbar
                            .make(view,Constants.ILLEGAL_ORDER_STATE_ALERT,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        TextView locationView = (TextView)mConfirmLayout.findViewById(R.id.cus_confirm_location);
        TextView timeView = (TextView) mConfirmLayout.findViewById(R.id.cus_confirm_time);
        TextView orderView = (TextView) mConfirmLayout.findViewById(R.id.confirm_order_detail);
        TextView priceView = (TextView) mConfirmLayout.findViewById(R.id.cus_confirm_price);

        locationView.setText(mOrder.getLocation());
        timeView.setText(mOrder.getTime());
        ;
        orderView.setText(String.format(Constants.DRINK_AND_SNACK_STATE,mOrder.getDrinks().size(),mOrder.getSnacks().size()));

        priceView.setText(String.format(Constants.TOTAL_PRICE,mOrder.getTotalPrice()));
        return view;
    }

    private void sendOrderToDatabase() {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_REF_ORDER);
        DatabaseReference parent = orderRef.child(Constants.FIREBASE_REF_TOCLAIM);
        DatabaseReference temp = parent.push();
        final String key = temp.getKey();
        parent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey().toString().equals(key)){
                    reset();

                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        temp.setValue(mOrder);
        ((MainActivity) getActivity()).getToolbar().setVisibility(View.GONE);
    }

    public void updateOrder(){
        TextView orderView = (TextView) mConfirmLayout.findViewById(R.id.confirm_order_detail);
        TextView priceView = (TextView) mConfirmLayout.findViewById(R.id.cus_confirm_price);
        orderView.setText(String.format(Constants.DRINK_AND_SNACK_STATE,mOrder.getDrinks().size(),mOrder.getSnackCount()));
        priceView.setText(String.format(Constants.TOTAL_PRICE,mOrder.getTotalPrice()));
    }
    private void reset() {
        pb.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getToolbar().setVisibility(View.VISIBLE);
        String uid = mOrder.getCustomerID();
        mOrder = new Order();
        mOrder.setCustomerID(uid);
        ((MainActivity) getActivity()).switchToMyDeliveryFragment();
        Snackbar snackbar = Snackbar
                .make(pb,Constants.ORDER_TAKEN_SUCCESSFUL,Snackbar.LENGTH_LONG);
        snackbar.show();

    }



}
