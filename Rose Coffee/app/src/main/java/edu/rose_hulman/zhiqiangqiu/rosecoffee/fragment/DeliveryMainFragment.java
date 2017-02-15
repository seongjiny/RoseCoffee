package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.Order;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeliveryMainFragment.Callback} interface
 * to handle interaction events.
 */
public class DeliveryMainFragment extends Fragment {

    private DatabaseReference mToClaimOrderRef;
    private Context mCallback;
    private DeliveryMainAdapter mAdapter;

    public DeliveryMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToClaimOrderRef = FirebaseDatabase.getInstance().getReference().child("order/toClaim");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_delivery_main,
                container, false);
        //Setup LayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(manager);
        //Setup Adapter
        mAdapter = new DeliveryMainAdapter(mCallback, mToClaimOrderRef);
        view.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            mCallback = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mToClaimOrderRef.removeEventListener(mAdapter.mChildEventListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        mToClaimOrderRef.addChildEventListener(mAdapter.mChildEventListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface Callback {
        void onDeliveryListSelected(Order order, DatabaseReference ref);
    }
}
