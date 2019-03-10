package assistant.stacking.star

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import assistant.stacking.star.notifications.fragment_notifications

class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        mDrawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        setupDrawerContent(navigationView)

        // Setting Parcel Selection as Default

        navigationView.menu.getItem(0).isChecked = true
        title = navigationView.menu.getItem(0).title

        val openingFragment: Fragment?
        val fragmentClass: Class<*>
        val fragmentManager = supportFragmentManager

        fragmentClass = ListFragment::class.java
        openingFragment = fragmentClass.newInstance() as Fragment
        fragmentManager.beginTransaction().replace(R.id.content_frame, openingFragment).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }


    fun selectDrawerItem(menuItem: MenuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        var fragment: Fragment?
        val fragmentClass: Class<*>
        when (menuItem.itemId) {
            R.id.parcel_selection -> fragmentClass = ListFragment::class.java
            R.id.van_layout -> fragmentClass = VanLayout::class.java
            R.id.manage_warehouse -> fragmentClass = ManageWarehouseFragment::class.java
            R.id.notifications -> fragmentClass= fragment_notifications::class.java
            else -> fragmentClass = ListFragment::class.java
        }

        Log.d("MainActivity", "")
        Log.d("MainActivity", "Setting fragment class")


        // Insert the fragment by replacing any existing fragment
        val fragmentManager = supportFragmentManager
        fragment = fragmentClass.newInstance() as Fragment
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit()

        // Highlight the selected item has been done by NavigationView
        menuItem.isChecked = true
        // Set action bar title
        title = menuItem.title
        // Close the navigation drawer
        mDrawerLayout.closeDrawers()
    }
}
