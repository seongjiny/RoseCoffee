package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

/**
 * Created by JerryQiu on 2/12/17.
 */

public class DeliveryMainAdapter extends RecyclerView.Adapter<DeliveryMainAdapter.OrderBriefView>
        implements ChildEventListener {

    private final LayoutInflater mInflater;
    private ArrayList<Order> mOrders;
    private DatabaseReference mToClaimOrderRef;
    private MainActivity mCallback;

    public DeliveryMainAdapter(Context context, DatabaseReference toClaimOrderRef) {
        mCallback = (MainActivity) context;
        mInflater = LayoutInflater.from(context);
        mToClaimOrderRef = toClaimOrderRef;
    }

    @Override
    public DeliveryMainAdapter.OrderBriefView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_delivery_detail, parent, false);
        return new OrderBriefView(view);
    }

    @Override
    public void onBindViewHolder(OrderBriefView holder, int position) {
        final Order order = mOrders.get(position);
        holder.mTitleTextView.setText(order.getCustomerID());
        holder.mTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onDeliveryListSelected(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public void firebasePush(Order order) {
        mToClaimOrderRef.push().setValue(order);
    }

    public void firebaseUpdate(Order order) {
        mToClaimOrderRef.child(order.getOrderID()).setValue(order);
    }

    public void firebaseRemove(Order order) {
        mToClaimOrderRef.child(order.getOrderID()).removeValue();
    }

    public void insert(Order password, int position) {
        mOrders.add(position, password);
        notifyItemInserted(position);
    }

    public void clear() {
        mOrders.clear();
    }

    public class OrderBriefView extends RecyclerView.ViewHolder {
        TextView mTitleTextView;

        public OrderBriefView(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.order_bried);
        }
    }

    public Order get(int position) {
        return mOrders.get(position);
    }

    public Order hide(int position) {
        Order order = mOrders.remove(position);
        notifyItemRemoved(position);
        return order;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        if (key.equals("order/toClaim")) {
            Order order = dataSnapshot.getValue(Order.class);
            order.setOrderID(key);
            //TODO: Add more here
            mOrders.add(0, order);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String previousChild) {
//        String service = dataSnapshot.child("service").getValue(String.class);
//        String username = dataSnapshot.child("username").getValue(String.class);
//        String password = dataSnapshot.child("password").getValue(String.class);
        int i;
        for (i = 0; i < mOrders.size(); i++) {
            Order pw = mOrders.get(i);
            if (dataSnapshot.getKey().equals(pw.getOrderID())) {
//                pw.setService(service);
//                pw.setUsername(username);
//                pw.setPassword(password);
                notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        remove(key);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        //Do nothing
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e("ddd", "Cancelled, error: " + databaseError.getMessage());
    }

    private Order remove(String key) {
        for (int i = 0; i < mOrders.size(); i++) {
            Order pw = mOrders.get(i);
            if (key.equals(pw.getOrderID())) {
                mOrders.remove(i);
                notifyItemRemoved(i);
                return pw;
            }
        }
        return null;
    }
}
