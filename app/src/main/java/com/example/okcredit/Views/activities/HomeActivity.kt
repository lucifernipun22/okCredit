package com.example.okcredit.Views.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.request.RequestOptions
import com.example.okcredit.Data.local.*
import com.example.okcredit.Navigation.AccountFragment
import com.example.okcredit.Navigation.AccountFragmentActivity
import com.example.okcredit.R
import com.example.okcredit.Repository.OkCreditRepo
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.Views.adapters.CustomerAdapter
import com.example.okcredit.Views.adapters.ViewPagerFragmentAdapter
import com.example.okcredit.Views.fragments.NewTodoItemEvent
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import com.example.okcredit.Views.values.Const
import com.example.okcredit.Views.values.OkCreditApplication
import com.example.okcredit.Views.values.Prefs
import com.example.okcredit.Views.values.Prefs.getString
import com.example.okcredit.Views.values.SharedPref
import com.example.okcredit.databinding.ActivityHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.san.app.activity.BaseActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.customer_item_layout.view.*
import org.greenrobot.eventbus.EventBus


class HomeActivity : BaseActivity(), OnRowItemClicked, NavigationView.OnNavigationItemSelectedListener {

    lateinit var okCreditDao: OkCreditDAO
    lateinit var customerViewModel: CustomerViewModel
    lateinit var okCreditRepository: OkCreditRepo
    var radioButtonClick: Int? = null
    lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var customerList: MutableList<Customer>
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var pagerAdapter: ViewPagerFragmentAdapter
    private lateinit var user: User
    private lateinit var db: OkCreditDatabase
    private var disposable: CompositeDisposable? = null
    private val tag: String = "HomeActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setViewPagerAdapter()
        initializations()
        initViews()

        etMaterialSearch.setOnClickListener {
            startActivity()
        }

//        btnAddCustomer.setOnClickListener {
//            intent = Intent(this, AddCustomerActivity::class.java)
//            startActivity(intent)
//        }

//        btnAddFilter.setOnClickListener {
//            bottomSheetDialog()
//        }
        drawerSetup()

    }


    private fun initViews() {
        customerList = mutableListOf()

    }

     private fun drawerSetup() {
        mDrawerToggle = object : ActionBarDrawerToggle(
            this@HomeActivity,
            navDrawer,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {

            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.BLUE
                }
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.GREEN
                }
                super.onDrawerOpened(drawerView)

                //UpdateHeaderDetails()

                invalidateOptionsMenu()
            }
        }

        val headerView = navigationView.inflateHeaderView(R.layout.nav_header_main)
        val llHeaderMain = headerView.findViewById(R.id.llHeaderMain) as LinearLayout
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.nav_account)
        etMaterialSearch.disableSearch()
        llHeaderMain.setOnClickListener {

        }
    }

    private fun startActivity() {
        val intent = Intent(this, FindCustomerActivity::class.java)
        startActivity(intent)
    }

    private fun initializations() {
        val myapplication = application as OkCreditApplication
        okCreditDao = myapplication.okCreditDAO
        okCreditRepository = myapplication.repository
        val viewModelFactory = CustomerViewModelFactory(okCreditRepository)
        customerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CustomerViewModel::class.java)
    }


    private fun setViewPagerAdapter() {
        pagerAdapter = ViewPagerFragmentAdapter(supportFragmentManager)
        home_viewPager.setAdapter(pagerAdapter)
        tabLayout.setupWithViewPager(home_viewPager)
    }

    override fun onItemClick(model: Customer) {

    }

    override fun onItem(model: Supplier) {

    }
override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                gotoHome()
            }
            R.id.nav_account -> {
                account()
            }
            R.id.nav_gallery -> {
                gallery()
            }
            R.id.nav_slideshow -> {
                slideShow()
            }
            R.id.nav_helpandSupport -> {
                helpSupport()

            }

            R.id.nav_sign_out -> {
                showLogoutDialog()
            }
        }
        navDrawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun account() {

    }
    override fun onBackPressed() {
        if (navDrawer.isDrawerOpen(GravityCompat.START)) {
            navDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    private fun gallery() {
        val shareBody = "Udhaar ka saara Hisaab apne Phone me - Install kabson for FREE"
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(
            Intent.createChooser(sharingIntent, resources.getString(R.string.share_using))
        )
    }

    private fun slideShow() {
        TODO("Not yet implemented")
    }

    private fun helpSupport() {
        TODO("Not yet implemented")
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.logout)
            .setMessage(R.string.logoutMsg)
            .setPositiveButton(R.string.logout) { dialog, _ ->
                Prefs.save("phone", "")
                Prefs.save("locale", "en")
                dialog.dismiss()
                gotoInitScreen()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        builder.create()
        builder.show()
    }
    private fun gotoHome() {
        TODO("Not yet implemented")
    }
    private fun gotoInitScreen() {
        val intent = Intent(this, LanguageSelect::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}








