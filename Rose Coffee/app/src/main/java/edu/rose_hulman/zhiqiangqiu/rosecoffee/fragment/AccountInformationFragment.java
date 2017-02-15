package edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.MainActivity;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.R;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.User;

import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.ACCOUNT_INFO_EDIT_EMAIL_CONTENT;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.ACCOUNT_INFO_EDIT_NAME_CONTENT;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.ACCOUNT_INFO_EDIT_TITLE;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.FIREBASE_REF_CUSTOMER;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.FIREBASE_REF_EMAIL;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.FIREBASE_REF_NAME;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.FIREBASE_REF_USERS;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInformationFragment extends Fragment {

    private User mUser;
    private Context mContext;
    private Switch mIsDeliverySwitch;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private ImageButton mNameImageButton;
    private ImageButton mEmailImageButton;
    private DatabaseReference mUserRef;

    public AccountInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_information, container, false);
        mContext = getContext();
        mIsDeliverySwitch = (Switch) view.findViewById(R.id.is_delivering_switch);

        mUser = ((MainActivity) getActivity()).getUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child(FIREBASE_REF_USERS).child(mUser.getUid()+"");

        mIsDeliverySwitch.setChecked(mUser.isCustomer());
        mIsDeliverySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be true if the switch is in the On position
                mUser.setIsCustomer(isChecked);
                mUserRef.child(FIREBASE_REF_CUSTOMER).setValue(isChecked);
            }
        });

        mNameTextView = (TextView) view.findViewById(R.id.firstname_content_textview);
        mNameImageButton = (ImageButton) view.findViewById(R.id.name_edit_imagebutton);
        mEmailTextView = (TextView) view.findViewById(R.id.email_content_textview);
        mEmailImageButton = (ImageButton) view.findViewById(R.id.email_edit_imagebutton);

        mNameTextView.setText(mUser.getName());
        mEmailTextView.setText(mUser.getEmail());
        mNameImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                final EditText edittext = new EditText(mContext);
                alert.setMessage(ACCOUNT_INFO_EDIT_NAME_CONTENT);
                alert.setTitle(ACCOUNT_INFO_EDIT_TITLE);

                alert.setView(edittext);

                alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String YouEditTextValue = edittext.getText().toString();
                        mNameTextView.setText(YouEditTextValue);
                        mUserRef.child(FIREBASE_REF_NAME).setValue(YouEditTextValue);
                    }
                });

                alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });
                alert.show();
            }
        });
        mEmailImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                final EditText edittext = new EditText(mContext);
                alert.setMessage(ACCOUNT_INFO_EDIT_EMAIL_CONTENT);
                alert.setTitle(ACCOUNT_INFO_EDIT_TITLE);

                alert.setView(edittext);

                alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String YouEditTextValue = edittext.getText().toString();
                        mEmailTextView.setText(YouEditTextValue);
                        mUserRef.child(FIREBASE_REF_EMAIL).setValue(YouEditTextValue);

                    }
                });

                alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });
                alert.show();
            }
        });



        return view;
    }
}
