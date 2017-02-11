package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import android.content.Intent;
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

import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.AboutUsFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.AccountInformationFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.CustomerMainFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.LoginFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.MyDeliveryFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.SettingFragment;
import edu.rosehulman.rosefire.Rosefire;
import edu.rosehulman.rosefire.RosefireResult;

/*
** Author: Seongjin Yoon and Zhiqiang Qiu
**
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnLoginListener {

    public static final String FIREBASE_PATH = "FIREBASE_PATH";
    private static final int RC_ROSEFIRE_LOGIN = 1;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private User mUser=null;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private OnCompleteListener mOnCompleteListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    Log.d("ddd", "Auth with user " + user);
                    if(mUser==null){
                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference ref = db.getReference();
                        mUser = new User();
                        ref.child("users").child(user.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        mUser = dataSnapshot.getValue(User.class);
                                        Log.d("USER",mUser.getName()+"");
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                        Log.d("USER2",mUser.getName()+"");
                    }
                    switchToMyDeliveryFragment("users/" + user.getUid());

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

    private void switchToMyDeliveryFragment(String path) {
        mToolbar.setVisibility(View.VISIBLE);
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child(path);
//        user.addListenerForSingleValueEvent();
        Fragment myDeliveryFragment = new MyDeliveryFragment();
        Bundle args = new Bundle();
        args.putString(FIREBASE_PATH, path);
        myDeliveryFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, myDeliveryFragment,"myDelivery");
        ft.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUser = new User();
        if (requestCode == RC_ROSEFIRE_LOGIN) {
            RosefireResult result = Rosefire.getSignInResultFromIntent(data);
            if (result.isSuccessful()) {
                mAuth.signInWithCustomToken(result.getToken()).addOnCompleteListener(this, mOnCompleteListener);
                mUser.setUid(result.getUsername());
                mUser.setName(result.getName());
                mUser.setEmail(result.getEmail());
                mUser.setIsCustomer(true);
                mUser.setPhone("");
                DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users/"+result.getUsername());
                user.setValue(mUser);
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
    public void onRosefireLogin() {
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
                Log.d("FF",mUser+"");
                if(mUser!=null){
                    Log.d("FF", mUser.getName()+"");
                }
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
                ft.replace(R.id.content_main,
                        new CustomerMainFragment()).commit();
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
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("Login");
        loginFragment.onLoginError(message);
    }
}
