package com.blogspot.e_kanivets.moneytracker.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blogspot.e_kanivets.moneytracker.R;
import com.blogspot.e_kanivets.moneytracker.activity.base.BaseActivity;
import com.blogspot.e_kanivets.moneytracker.fragment.AccountsFragment;
import com.blogspot.e_kanivets.moneytracker.fragment.ExchangeRatesFragment;
import com.blogspot.e_kanivets.moneytracker.fragment.ExportFragment;
import com.blogspot.e_kanivets.moneytracker.fragment.RecordsFragment;
import com.blogspot.e_kanivets.moneytracker.util.PrefUtils;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @SuppressWarnings("unused")
    private static final String TAG = "MainActivity";

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_nav_drawer;
    }

    @Override
    protected boolean initData() {
        PrefUtils.addLaunchCount();
        return true;
    }

    @Override
    protected void initViews() {
        super.initViews();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            initToolbar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = new Fragment();

        switch (item.getItemId()) {
            case R.id.nav_records:
                fragment = RecordsFragment.newInstance();
                break;

            case R.id.nav_accounts:
                fragment = AccountsFragment.newInstance();
                break;

            case R.id.nav_rates:
                fragment = ExchangeRatesFragment.newInstance();
                break;

            case R.id.nav_export:
                fragment = ExportFragment.newInstance();
                break;

            default:
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}