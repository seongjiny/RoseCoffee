package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment OrderDetailFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static OrderDetailFragment newInstance(String param1, String param2) {
//        OrderDetailFragment fragment = new OrderDetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

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

        return view;
    }

    private final void showAddDrinkDialog() {
        final Dialog mydialog = new Dialog(getActivity());
        mydialog.setContentView(R.layout.dialog_add_drink);
        Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_name_spinner);
        Spinner sizeSpinner = (Spinner) mydialog.findViewById(R.id.add_drink_size_spinner);
        String[] drinkNames = new String[]{"Strawberry Frappuccino","Cappuccino","Cafe Latte"};
        String[] sizeNames = new String[]{"Venti","Grande","Tall"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,drinkNames);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,sizeNames);

        nameSpinner.setAdapter(adapter1);
        sizeSpinner.setAdapter(adapter2);
        Button button = (Button) mydialog.findViewById(R.id.add_drink_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("dd","ok");
                mydialog.dismiss();
            }
        });
        Button buttonCancel = (Button) mydialog.findViewById(R.id.add_drink_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("d","cancel");
                mydialog.dismiss();
            }
        });

        mydialog.show();
    }

    public final void showAddSnackDialog(){
        final Dialog mydialog = new Dialog(getActivity());
        mydialog.setContentView(R.layout.dialog_add_snack);
        final Spinner nameSpinner = (Spinner) mydialog.findViewById(R.id.add_snack_name_spinner);
        String[] drinkNames = new String[]{"Muffin","Apple Sauce","Chips"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,drinkNames);
        nameSpinner.setAdapter(adapter1);
        Button button = (Button) mydialog.findViewById(R.id.add_snack_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AA",nameSpinner.getSelectedItem()+"");
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

    public void onClick(View v){
        showAddDrinkDialog();
    }
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
