package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.AboutUsFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.AccountInformationFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.CustomerMainFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.DeliveryMainFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.LoginFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.SettingFragment;
import edu.rosehulman.rosefire.Rosefire;
import edu.rosehulman.rosefire.RosefireResult;

import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.CUSTOMER_KEY;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.EMAIL_KEY;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.NAME_KEY;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.PREFS;
import static edu.rose_hulman.zhiqiangqiu.rosecoffee.Constants.UID_KEY;

/*
** Author: Seongjin Yoon and Zhiqiang Qiu
**
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnLoginListener, DeliveryMainFragment.Callback {

    public static final String FIREBASE_PATH = "FIREBASE_PATH";
    private static final int RC_ROSEFIRE_LOGIN = 1;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private User mUser = null;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private OnCompleteListener mOnCompleteListener;
    private Order mOrder;
    private DatabaseReference mRef;
    private HashMap<String,MenuDrink> mMenuDrinks = new HashMap<>();
    private HashMap<String,Double> mMenuSnacks = new HashMap<>();
//    private SharedPreferences mSharedPreferences;
//    private SharedPreferenceUtils mSharedPreferenceUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mSharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
//        mSharedPreferenceUtils = new SharedPreferenceUtils();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mAuth = FirebaseAuth.getInstance();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        //Setup Drawer Toggle of the Toolbar
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mRef = FirebaseDatabase.getInstance().getReference();
        importDrinks();
        SharedPreferences prefs = getSharedPreferences(PREFS,MODE_PRIVATE);
        mUser = new User();
        mUser.setUid(prefs.getString(UID_KEY, null));
        mUser.setEmail(prefs.getString(EMAIL_KEY,null));
        mUser.setIsCustomer(prefs.getBoolean(CUSTOMER_KEY,true));
        mUser.setName(prefs.getString(NAME_KEY,null));

        //To identify if the user is already log in
        initializeListeners();
    }

    public User getUser() {
        return mUser;
    }

    private void initializeListeners() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("ddd", "Auth with user :" + user);

                    switchToMyDeliveryFragment();
//                    mUser = new User();
//                    ref.child("users").child(mUser.getUid())
//                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    mUser = dataSnapshot.getValue(User.class);
//                                    if (mUser.isCustomer())
//
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {}
//                            });

                } else {
                    Log.d("ddd", "Go to Login page");
                    switchToLoginPage();
                }
            }
        };
        mOnCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (!task.isSuccessful()) {
                    showLoginError("Login failed");
                }
            }
        };
    }

    private void switchToLoginPage() {
        mToolbar.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new LoginFragment(),"Login");
        ft.commit();
    }

    private void switchToMyDeliveryFragment() {
        mToolbar.setVisibility(View.VISIBLE);
        Fragment initialFragment;
        if (mUser.isCustomer()) {
            Log.d("ddd", "Init Jump to Customer Main");
            initialFragment = new CustomerMainFragment();
            mOrder = new Order();
            mOrder.setCustomer(mUser.getUid());
        } else {
            Log.d("ddd", "Init Jump to Delivery Main");
            initialFragment = new DeliveryMainFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, initialFragment,"myDelivery");
        ft.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_ROSEFIRE_LOGIN) {
            RosefireResult result = Rosefire.getSignInResultFromIntent(data);
            if (result.isSuccessful()) {
                mAuth.signInWithCustomToken(result.getToken())
                        .addOnCompleteListener(this, mOnCompleteListener);
                DatabaseReference userRef = mRef.child("users/"+result.getUsername());
                userRef.keepSynced(true);
                mUser = new User();
                mUser.setUid(result.getUsername());
                mUser.setName(result.getName());
                mUser.setEmail(result.getEmail());
                mUser.setIsCustomer(true);

                mToolbar.setVisibility(View.VISIBLE);
            } else {
                showLoginError("Rosefire sign in failed.");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (mUser != null) {
            editor.putString(UID_KEY, mUser.getUid());
            editor.putString(EMAIL_KEY, mUser.getEmail());
            editor.putString(NAME_KEY, mUser.getName());
            editor.putBoolean(CUSTOMER_KEY, mUser.isCustomer());
        }
        editor.commit();
    }

    @Override
    public void onRosefireLogin() {
        mToolbar.setVisibility(View.GONE);
        Intent signInIntent = Rosefire.getSignInIntent(this, getString(R.string.rosefire_key));
        startActivityForResult(signInIntent, RC_ROSEFIRE_LOGIN);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_logout:
                mAuth.signOut();
                mUser=new User();
                switchToLoginPage();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {

            case R.id.nav_my_delivery:
                if (mUser.isCustomer()) {
                    Log.d("ddd", "Nav jump to CustomerMain");
                    ft.replace(R.id.content_main,
                            new CustomerMainFragment()).commit();
                } else {
                    Log.d("ddd", "Nav jump to DeliveryMain");
                    ft.replace(R.id.content_main,
                            new DeliveryMainFragment()).commit();
                }
                break;
            case R.id.nav_account_info:
                ft.replace(R.id.content_main,
                        new AccountInformationFragment()).commit();
                break;
            case R.id.nav_about_us:
                ft.replace(R.id.content_main,
                        new AboutUsFragment()).commit();
                break;
            case R.id.nav_setting:
                ft.replace(R.id.content_main,
                        new SettingFragment()).commit();
                break;
            default:
                Log.d("ddd", "Nav bar item seleted error");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    private void showLoginError(String message) {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().
                findFragmentByTag("Login");
        loginFragment.onLoginError(message);
    }

    @Override
    public void onDeliveryListSelected(String key, HashMap<String,Object> map) {
        mRef.child("order/claimed").child(key).setValue(map);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, new DeliveryMainFragment()).commit();
    }

    public Order getOrder(){
        return mOrder;
    }
    public HashMap<String,MenuDrink> getDrinks(){
        return mMenuDrinks;
    }
    public HashMap<String,Double> getSnacks(){
        return mMenuSnacks;
    }
    public void importDrinks(){
        DatabaseReference drinkRef = mRef.child("menu").child("drink");
        drinkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    MenuDrink newDrink = new MenuDrink(
                            d.getKey().toString(),
                            Double.parseDouble(d.child("large").getValue().toString()),
                            Double.parseDouble(d.child("medium").getValue().toString()),
                            Double.parseDouble(d.child("small").getValue().toString())
                            );
                    mMenuDrinks.put(d.getKey().toString(),newDrink);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference snackRef = mRef.child("menu").child("snack");
        snackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    mMenuSnacks.put(d.getKey().toString(),Double.parseDouble(d.child("price").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
