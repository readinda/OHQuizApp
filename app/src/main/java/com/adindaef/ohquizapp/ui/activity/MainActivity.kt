package com.adindaef.ohquizapp.ui.activity

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
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.adindaef.ohquizapp.R
import com.adindaef.ohquizapp.ui.fragment.CategoryFragment
import com.adindaef.ohquizapp.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.adindaef.ohquizapp.model.Question


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
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
                toolbar.title = "Home"
            }

            R.id.nav_one -> {

                val bundle = Bundle()
                val myMessage = Question.DIFFICULTY_FIRST_CLASS
                bundle.putString("message", myMessage)

                val cf = CategoryFragment()
                cf.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.container, cf).commit()
                supportFragmentManager.popBackStack()

                toolbar.title = Question.DIFFICULTY_FIRST_CLASS

            }

            R.id.nav_two -> {

                val bundle = Bundle()
                val myMessage = Question.DIFFICULTY_SECOND_GRADE
                bundle.putString("message", myMessage)

                val cf = CategoryFragment()
                cf.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.container, cf).commit()
                supportFragmentManager.popBackStack()

                toolbar.title = Question.DIFFICULTY_SECOND_GRADE

            }

            R.id.nav_three -> {

                val bundle = Bundle()
                val myMessage = Question.DIFFICULTY_THIRD_GRADE
                bundle.putString("message", myMessage)

                val cf = CategoryFragment()
                cf.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.container, cf).commit()
                supportFragmentManager.popBackStack()

                toolbar.title = Question.DIFFICULTY_THIRD_GRADE
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}