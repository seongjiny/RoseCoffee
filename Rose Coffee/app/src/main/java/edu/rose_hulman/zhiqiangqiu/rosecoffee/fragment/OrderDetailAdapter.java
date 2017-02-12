package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

/**
 * Created by yoons1 on 2/12/2017.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<Order> mOrders = new ArrayList<>();

    public OrderDetailAdapter(Context context,RecyclerView recyclerView){
        mContext = context;
        mRecyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = mOrders.get(position);
        holder.mOrderNameTextView.setText(order.getOrderID());
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private TextView mOrderNameTextView;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
