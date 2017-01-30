package edu.rose_hulman.zhiqiangqiu.rosecoffee;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.AboutUsFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.AccountInformationFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.CustomerMainFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.LoginFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.MyDeliveryFragment;
import edu.rose_hulman.zhiqiangqiu.rosecoffee.fragment.SettingFragment;

/*
** Author: Seongjin Yoon and Zhiqiang Qiu
**
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String FIREBASE_PATH = "FIREBASE_PATH";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private OnCompleteListener mOnCompleteListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeListeners();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new CustomerMainFragment()).commit();

        //Setup Drawer Toggle of the Toolbar
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initializeListeners() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("ddd", "Auth with user"+ user);
                    switchToMyDeliveryFragment("users/" + user.getUid());
                } else {
                    mFragmentTransaction.replace(R.id.main, new LoginFragment(), "Login").commit();
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

    private void switchToMyDeliveryFragment(String path) {
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child(path);
        Fragment myDeliveryFragment = new MyDeliveryFragment();
        Bundle args = new Bundle();
        args.putString(FIREBASE_PATH, path);
        myDeliveryFragment.setArguments(args);
        mFragmentTransaction.replace(R.id.main, myDeliveryFragment, "MyDelivery").commit();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_my_delivery:
                mFragmentTransaction.replace(R.id.containerView,
                        new CustomerMainFragment()).commit();
                break;
            case R.id.nav_account_info:
                mFragmentTransaction.replace(R.id.containerView,
                        new AccountInformationFragment()).commit();
                break;
            case R.id.nav_about_us:
                mFragmentTransaction.replace(R.id.containerView,
                        new AboutUsFragment()).commit();
                break;
            case R.id.nav_setting:
                mFragmentTransaction.replace(R.id.containerView,
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
