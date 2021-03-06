package com.example.rbdb.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.*
import com.example.rbdb.databinding.ActivityMainBinding
import com.example.rbdb.ui.adapters.FragmentAdapter
import com.example.rbdb.ui.arch.AppViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        // Prepopulate database for testing
        viewModel.init(AppDatabase.getDatabase(this))


        // End of db / vm init

        val tabLayout = binding.TabLayout
      
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
        val viewPager = binding.pager

        viewPager.adapter = FragmentAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "ALL"
                }
                1 -> {
                    tab.text = "GROUPS"
                }
                2->{
                    tab.text = "TAGS"
                }
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.search -> {
            val intent = Intent(this, SearchActivity::class.java)
            this.startActivity(intent)
            true
        }

        R.id.more -> {
            Toast.makeText(this, "more button pressed", Toast.LENGTH_SHORT).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val menuItem = menu!!.findItem(R.id.search)
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                Toast.makeText(this@MainActivity, "Search is expanded", Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                Toast.makeText(this@MainActivity, "Search is collapsed", Toast.LENGTH_SHORT).show()
                return true
            }
        })

        val searchItem = menu!!.findItem(R.id.search)
        var searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = "Search here..."

        return true
    }*/
}