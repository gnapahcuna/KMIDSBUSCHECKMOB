package com.paiwaddev.kmids.nfcbuscheck.views.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.paiwaddev.kmids.kmidsmobile.view.custom.SigleAlertDialog
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.viewModel.LoginViewModel
import com.paiwaddev.kmids.nfcbuscheck.views.custom.DoubleAlertDialog
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(),DoubleAlertDialog.ItemListener {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        ///setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home);
                }

                R.id.navigation_dashboard -> {
                    navController.navigate(R.id.navigation_dashboard);
                }
                R.id.navigation_notifications -> {
                    val alert = DoubleAlertDialog.newInstance(
                        getString(R.string.title_notifications), getString(
                            R.string.text_ok_button
                        ), getString(R.string.text_cancel_button)
                    )
                    alert.show(supportFragmentManager, SigleAlertDialog.TAG)
                    alert.setListener(this)
                }
            }
            true
        }
    }

    private var COUNT_BACK: Int = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            COUNT_BACK++
            if (COUNT_BACK == 3) {
                finishAffinity()
                System.exit(0)
            }
            if (COUNT_BACK == 2)
                Toast.makeText(
                    this,
                    resources.getString(R.string.title_confirm_logout),
                    Toast.LENGTH_SHORT
                ).show()
            true
        } else super.onKeyDown(keyCode, event)
    }


    override fun onItemClicked() {

        //Logout
        loginViewModel.onLogout().observe(this,{
            if(it){
                startActivity(Intent(this, WelcomeActivity::class.java))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }
        })
    }

}