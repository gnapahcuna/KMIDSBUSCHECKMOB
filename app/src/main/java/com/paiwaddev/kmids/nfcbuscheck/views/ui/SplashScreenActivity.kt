package com.paiwaddev.kmids.nfcbuscheck.views.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import androidx.lifecycle.ViewModelProvider
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.viewModel.PINViewModel

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var viewModel: PINViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel =
            ViewModelProvider(this).get(PINViewModel::class.java)

        viewModel.getContext(this)


        Handler().postDelayed(Runnable {
            viewModel.onLoadPIN().observe(this,{ PIN ->
                println(PIN.isEmpty())
                if(PIN.isEmpty()){

                    startActivity(Intent(this, WelcomeActivity::class.java))
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                    finish()

                }else{

                    val intent = Intent(this, LockScreenActivity::class.java)
                    intent.putExtra("PIN",PIN)
                    startActivity(intent)
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                    finish()
                }

            })

        }, 3000)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    companion object{
        var HOME_SCREEN = "com.paiwaddev.kmids.kmidsmobile.home"
        var WELCOME_SCREEN = "com.paiwaddev.kmids.kmidsmobile.welcome"
        var LOCK_SCREEN = "com.paiwaddev.kmids.kmidsmobile.lock"
        var KEYS = "SCREENS"
    }
}