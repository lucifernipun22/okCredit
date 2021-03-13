package com.example.okcredit.Views.activities
import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.Menu
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController

import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.bumptech.glide.load.engine.Resource
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.OkCreditDAO
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.Data.local.User
import com.example.okcredit.R
import com.example.okcredit.R.id.nav_host_fragment
import com.example.okcredit.Repository.OkCreditRepo
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.Views.adapters.CustomerAdapter
import com.example.okcredit.Views.adapters.ViewPagerFragmentAdapter
import com.example.okcredit.Views.fragments.NewTodoItemEvent
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import com.example.okcredit.Views.values.Const
import com.example.okcredit.Views.values.OkCreditApplication
import com.example.okcredit.Views.values.Prefs.getString
import com.example.okcredit.Views.values.SharedPref
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView

import com.san.app.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.customer_item_layout.view.*
import org.greenrobot.eventbus.EventBus




class HomeActivity : AppCompatActivity(), OnRowItemClicked {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,R.id.nav_account,
            R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_helpandSupport), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onItemClick(model: Customer) {
        TODO("Not yet implemented")
    }

    override fun onItem(model: Supplier) {
        TODO("Not yet implemented")
    }

}