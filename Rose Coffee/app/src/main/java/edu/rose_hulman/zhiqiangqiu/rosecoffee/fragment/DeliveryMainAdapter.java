package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

/**
 * Created by JerryQiu on 2/12/17.
 */

public class DeliveryMainAdapter extends RecyclerView.Adapter<DeliveryMainAdapter.ViewHolder>{

    private ArrayList<Order>  mOrders;
    private DeliveryMainFragment.Callback mCallback;

    public DeliveryMainAdapter(Context context, DeliveryMainFragment.Callback callback) {
        mCallback = callback;

    }

    @Override
    public DeliveryMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_delivery_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliveryMainAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        final Order order = mOrders.get(position);
        holder.mTitleTextView.setText(order.getCustomerID());
        holder.mTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onDeliveryListSelected(order);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.order_bried);
        }
    }
}
