package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.CLAIM_CANCEL_MESSAGE;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.CLAIM_ORDER_ALERT_CONTENT;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.CLAIM_ORDER_ALERT_TITLE;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.MULIPLE_SNACKS;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.ONE_DRINK;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.ONE_DRINK_ONE_SNACK;

/**
 * Created by JerryQiu on 2/12/17.
 */

public class DeliveryMainAdapter extends RecyclerView.Adapter<DeliveryMainAdapter.OrderBriefView> {

    private final LayoutInflater mInflater;
    private ArrayList<Order> mOrders;
    private DatabaseReference mToClaimOrderRef;
    private MainActivity mCallback;
    public ChildEventListener mChildEventListener;

    public DeliveryMainAdapter(Context context, DatabaseReference toClaimOrderRef) {
        mCallback = (MainActivity) context;
        mInflater = LayoutInflater.from(context);
        mToClaimOrderRef = toClaimOrderRef;
        mOrders = new ArrayList<>();
        mToClaimOrderRef.addChildEventListener(new DeliveryChildEventListener());
        mChildEventListener = new DeliveryChildEventListener();
    }

    class DeliveryChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            Order order = dataSnapshot.getValue(Order.class);
            order.setOrderID(dataSnapshot.getKey());
            for (Order o: mOrders) {
                if (order.getOrderID().equals(o.getOrderID())) {
                    Log.d("ddd", "Order " +order.getOrderID() + " already exist, skip");
                    return;
                }
            }

            Log.d("ddd", "order: " + order.getOrderID() + "current orders count: " + mOrders.size());
            mOrders.add(order);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //Don't care
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d("ddd", "A toCliam order has been removed when user inspecting delivery main list");
            String key = dataSnapshot.getKey();
            remove(key);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //I am not sure about this!!!
            String key = dataSnapshot.getKey();
            remove(key);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.d("ddd", "DeliveryMain adapter observe Canceled");
        }
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

    @Override
    public DeliveryMainAdapter.OrderBriefView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_delivery_detail, parent, false);
        return new OrderBriefView(view);
    }

    @Override
    public void onBindViewHolder(OrderBriefView holder, int position) {
        final Order order = mOrders.get(position);
        int drinkNumber = order.getDrinkCount();
        int snackNumber = order.getSnackCount();
        String briefText = "";
        //For this part, I really don't want to make more constant String... Seems so unnecessary...
        switch (drinkNumber) {
            case 0:
                switch (snackNumber) {
                    case 0:
//                        Log.d("ddd", "No drink nor snack");
                        break;
                    case 1:
                        briefText = ONE_DRINK;
                        break;
                    default:
                        briefText = snackNumber+ MULIPLE_SNACKS;
                }
                break;
            case 1:
                switch (snackNumber) {
                    case 1:
                        briefText = ONE_DRINK_ONE_SNACK;
                        break;
                    default:
                        briefText = "1 drink, " + snackNumber + MULIPLE_SNACKS;
                }
                break;
            default:
                switch (snackNumber) {
                    case 0:
                        briefText = drinkNumber + " drinks";
                        break;
                    case 1:
                        briefText = drinkNumber + "drinks, 1 snack";
                        break;
                    default:
                        briefText = drinkNumber+ " drinks, " + snackNumber + MULIPLE_SNACKS;
                }
        }
        holder.mBriefTextView.setText(briefText);
        holder.mLocationTextView.setText(order.getLocation());
        holder.mTimeTextView.setText(order.getTime());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mCallback)
                        .setTitle(CLAIM_ORDER_ALERT_TITLE)
                        .setMessage(CLAIM_ORDER_ALERT_CONTENT)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mToClaimOrderRef.child(order.getOrderID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //If succeed, jump to next page.
                                        Order order = dataSnapshot.getValue(Order.class);
                                        order.setOrderID(dataSnapshot.getKey());
                                        mToClaimOrderRef.child(order.getOrderID()).removeValue();
                                        order.setDelivery(mCallback.getUser().getUid());
                                        mCallback.onDeliveryListSelected(order, mToClaimOrderRef);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        //If failed, snackbar show up.
                                        Snackbar snackbar = Snackbar.make(mCallback.
                                                findViewById(R.id.delivery_main_recycler_view),
                                                CLAIM_CANCEL_MESSAGE,
                                                Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public void clear() {
        mOrders.clear();
    }

    public class OrderBriefView extends RecyclerView.ViewHolder {
        View mCardView;
        TextView mBriefTextView;
        TextView mLocationTextView;
        TextView mTimeTextView;

        public OrderBriefView(View itemView) {
            super(itemView);
            mCardView = itemView;
            mBriefTextView = (TextView) itemView.findViewById(R.id.delivery_main_order_brief);
            mLocationTextView = (TextView) itemView.findViewById(R.id.delivery_main_order_location);
            mTimeTextView = (TextView) itemView.findViewById(R.id.delivery_main_order_time);
        }
    }

    public Order get(int position) {
        return mOrders.get(position);
    }
}
