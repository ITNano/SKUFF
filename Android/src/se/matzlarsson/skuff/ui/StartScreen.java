package se.matzlarsson.skuff.ui;

import java.util.ArrayList;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.nav.NavDrawerItem;
import se.matzlarsson.skuff.nav.NavDrawerListAdapter;
import se.matzlarsson.skuff.ui.calender.CalenderFragment;
import se.matzlarsson.skuff.ui.contact.ContactFragment;
import se.matzlarsson.skuff.ui.contest.ContestFragment;
import se.matzlarsson.skuff.ui.groups.GroupsFragment;
import se.matzlarsson.skuff.ui.news.NewsFragment;
import se.matzlarsson.skuff.ui.sag.SagFragment;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class StartScreen extends ActionBarActivity implements FragmentDisplayer{

	public static final String FRAGMENT_NEWS = "news";
	public static final String FRAGMENT_CALENDER = "calender";
	public static final String FRAGMENT_GROUPS = "groups";
	public static final String FRAGMENT_CONTEST = "contest";
	public static final String FRAGMENT_SAG = "sag";
	public static final String FRAGMENT_TIP = "tip";
	public static final String FRAGMENT_CONTACT = "contact";
	
	private boolean menuDisabled = false;
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
    //The fragments
    private Fragment[] fragments;
    private String[] fragmentNames;
 
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
 
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
 
    
    public boolean isMenuDisabled(){
    	return menuDisabled;
    }
    
    public void setMenuDisabled(boolean menuDisabled){
    	this.menuDisabled = menuDisabled;
    	mDrawerLayout.setEnabled(!menuDisabled);
    	mDrawerLayout.setDrawerLockMode((menuDisabled?DrawerLayout.LOCK_MODE_LOCKED_CLOSED:DrawerLayout.LOCK_MODE_UNLOCKED));
    	if(menuDisabled){
    		this.getSupportActionBar().hide();
    	}else{
    		this.getSupportActionBar().show();
    	}
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper.start(this);
        
        fragments = new Fragment[7];
        fragmentNames = new String[7];
        fragments[0] = new NewsFragment(this);
        fragmentNames[0] = FRAGMENT_NEWS;
        fragments[1] = new CalenderFragment();
        fragmentNames[1] = FRAGMENT_CALENDER;
        fragments[2] = new GroupsFragment();
        fragmentNames[2] = FRAGMENT_GROUPS;
        fragments[3] = new ContestFragment();
        fragmentNames[3] = FRAGMENT_CONTEST;
        fragments[4] = new SagFragment();
        fragmentNames[4] = FRAGMENT_SAG;
        fragments[5] = new TipFragment();
        fragmentNames[5] = FRAGMENT_TIP;
        fragments[6] = new ContactFragment();
        fragmentNames[6] = FRAGMENT_CONTACT;
        
        setContentView(R.layout.activity_start_screen);
        
        mTitle = mDrawerTitle = getTitle();
 
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        for(int i = 0; i<navMenuTitles.length; i++){
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
        }
        navMenuIcons.recycle();
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                supportInvalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_screen, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        setTitle(mTitle);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
    
    public void setIcon(int resourceIndex) {
        getSupportActionBar().setIcon(resourceIndex);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    
    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }
 
    
    private void displayView(int position) {
    	// fix position to valid value
	    position = Math.max(0, Math.min(fragments.length, position));
	    
	    // update selected item and title, then close the drawer
	    mDrawerList.setItemChecked(position, true);
	    mDrawerList.setSelection(position);
	    setTitle(navMenuTitles[position]);
	    navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
	    setIcon(navMenuIcons.getResourceId(position, -1));
	    navMenuIcons.recycle();
	    mDrawerLayout.closeDrawer(mDrawerList);
	    
	    // update the main content by replacing fragments
	    displayFragment(fragments[position]);
    }
 
    public void displayFragment(Fragment fragment){
        if (fragment != null) {
            if(fragment instanceof Refreshable){
            	((Refreshable)fragment).refresh();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, "currentFragment").commit();
            
        } else {
            Log.e("StartScreen", "Error in creating fragment");
        }
    }
    
	@Override
	public void displayFragment(String s) {
		for(int i = 0; i<fragmentNames.length; i++){
			if(fragmentNames[i].equals(s)){
				displayView(i);
				return;
			}
		}
		Toast.makeText(this, "Hittade inte den efterfrågade skärmen", Toast.LENGTH_SHORT).show();
	}
}