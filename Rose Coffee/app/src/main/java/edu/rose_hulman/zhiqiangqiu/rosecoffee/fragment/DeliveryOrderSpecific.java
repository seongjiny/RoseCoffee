package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Drink;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

import static android.content.Context.MODE_PRIVATE;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.DELIVERED_ORDER_ALERT_CONTENT;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.DELIVERED_ORDER_ALERT_TITLE;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.PREFS;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryOrderSpecific extends Fragment {

    private MainActivity mCallback;
    private DatabaseReference mRef;

    public DeliveryOrderSpecific() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRef  = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_REF_ORDER).child(Constants.FIREBASE_REF_CLAIMED);
        mCallback = (MainActivity)getActivity();
        SharedPreferences prefs = mCallback.getSharedPreferences(PREFS,MODE_PRIVATE);
        String key = prefs.getString(Constants.ORDER_KEY, "");
        final TextView locationTextView = (TextView) mCallback.findViewById(R.id.delivery_detail_location);
        final TextView timeTextView = (TextView) mCallback.findViewById(R.id.delivery_detail_time);
        final TextView customerTextView = (TextView) mCallback.findViewById(R.id.delivery_detail_customer);
        final TextView orderTextView = (TextView) mCallback.findViewById(R.id.delivery_detail_order);

        mRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                locationTextView.setText(order.getLocation());
                timeTextView.setText(order.getTime());
                customerTextView.setText(order.getCustomerID());
                String orderText = "";
                for (Drink d: order.getDrinks()) {
                    String s = d.getName() + " " + d.getSize();
                    if (d.getComment().equals("")) {
                        s += "-" + d.getComment();
                    }
                    orderText += s + Constants.NEW_LINE;
                }
                for (String s: order.getSnacks()) {
                    orderText += s + Constants.NEW_LINE;
                }
                orderTextView.setText(orderText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_order_specific, container, false);

        Button finishButton = (Button) mCallback.findViewById(R.id.delivery_finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mCallback)
                        .setTitle(DELIVERED_ORDER_ALERT_TITLE)
                        .setMessage(DELIVERED_ORDER_ALERT_CONTENT)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mCallback.onDeliveryFinished();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        }).show();
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FinishOrder) {
            mCallback = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface FinishOrder {
        void onDeliveryFinished();
    }

}
