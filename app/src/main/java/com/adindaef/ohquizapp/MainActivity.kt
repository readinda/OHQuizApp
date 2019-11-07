package com.adindaef.ohquizapp

import android.content.ClipData
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.adindaef.ohquizapp.ui.category.CategoryFragment
import com.adindaef.ohquizapp.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_one,
                R.id.nav_two,
                R.id.nav_three
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {

            R.id.nav_home -> {

                val cf = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, cf).commit()
                supportFragmentManager.popBackStack()

                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                toolbar.setTitle("Home")
            }

            R.id.nav_one -> {

                val bundle = Bundle()
                val myMessage = "First Class"
                bundle.putString("message", myMessage)

                val cf = CategoryFragment()
                cf.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.container, cf).commit()
                supportFragmentManager.popBackStack()

                toolbar.setTitle("First Class")

                Toast.makeText(this, "First Class", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_two -> {

                val bundle = Bundle()
                val myMessage = "Second grade"
                bundle.putString("message", myMessage)

                val cf = CategoryFragment()
                cf.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.container, cf).commit()
                supportFragmentManager.popBackStack()

                toolbar.setTitle("Second grade")

                Toast.makeText(this, "Second grade", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_three -> {

                val bundle = Bundle()
                val myMessage = "Third grade"
                bundle.putString("message", myMessage)

                val cf = CategoryFragment()
                cf.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.container, cf).commit()
                supportFragmentManager.popBackStack()

                toolbar.setTitle("Third grade")

                Toast.makeText(this, "Third grade", Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}