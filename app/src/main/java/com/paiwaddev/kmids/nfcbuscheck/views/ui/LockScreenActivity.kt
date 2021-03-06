package com.paiwaddev.kmids.nfcbuscheck.views.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.paiwaddev.kmids.kmidsmobile.view.custom.SigleAlertDialog
import com.paiwaddev.kmids.nfcbuscheck.R
import com.paiwaddev.kmids.nfcbuscheck.views.custom.ProgressBuilder
import com.paiwaddev.kmids.nfcbuscheck.viewModel.PINViewModel
import com.paiwaddev.kmids.nfcbuscheck.viewModel.SettingViewModel
import org.koin.android.ext.android.inject

class LockScreenActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: PINViewModel

    private lateinit var settingViewModel: SettingViewModel


    private var pinNums: ArrayList<Button> = ArrayList()
    private var pins: ArrayList<View> = ArrayList()

    private lateinit var pinNum0: Button
    private lateinit var pinNum1: Button
    private lateinit var pinNum2: Button
    private lateinit var pinNum3: Button
    private lateinit var pinNum4: Button
    private lateinit var pinNum5: Button
    private lateinit var pinNum6: Button
    private lateinit var pinNum7: Button
    private lateinit var pinNum8: Button
    private lateinit var pinNum9: Button
    private lateinit var pinFingerScan: ImageView
    private lateinit var pinDel: ImageView

    private lateinit var pin1: View
    private lateinit var pin2: View
    private lateinit var pin3: View
    private lateinit var pin4: View

    private lateinit var btnClose: ImageView
    private lateinit var btnForgotPassword: TextView

    private val dialog: ProgressBuilder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)

        viewModel =
                ViewModelProvider(this).get(PINViewModel::class.java)

        viewModel.getContext(this)

        settingViewModel =
                ViewModelProvider(this).get(SettingViewModel::class.java)


        initView()
        addPins()

        checkOpenTouchID()

        val PIN =intent.getStringExtra("PIN")
        //ตรวจสอบเมื่อกด PIN ครบ 4 ตัว
        viewModel.mPIN.observe(this,{_pin ->
            if(_pin.equals(PIN)){
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                finish()
            }else{
                SigleAlertDialog.newInstance(getString(R.string.text_error_lock_pin), getString(R.string.text_ok_button), LockScreenActivity()).show(supportFragmentManager, SigleAlertDialog.TAG)
            }
        })

        //รับ index เมื่อกด PIN
        viewModel.mIndexPIN.observe(this,{index ->
            pins.get(index).isPressed = true
        })

        //รหัสไม่ถูก
        viewModel.mLoginFail.observe(this,{fail ->
            pins.forEach { it.isPressed = false }
        })

        //เมื่อกด PIN ให้ไป set ค่าที่ viewModel
        pinNums.forEach { num ->
            num.setOnClickListener {
                viewModel.onChekPin(num,pins.size)
            }
        }

        pinDel.setOnClickListener(this)

        btnClose.setOnClickListener(this)

        pinFingerScan.setOnClickListener(this)

        btnForgotPassword.setOnClickListener(this)
    }

    fun checkOpenTouchID(){
        val sharepref = this.getSharedPreferences("USER_KMIDS", Context.MODE_PRIVATE)
        settingViewModel.onLoadIsTouchID(sharepref).observe(this,{
            if(it) {
                onFingerPrintScan()
            }else{
                pinFingerScan.setColorFilter(Color.GRAY)
                pinFingerScan.isEnabled = false
            }
        })
    }

    fun initView() {
        pinNum0 = findViewById(R.id.pin0)
        pinNum1 = findViewById(R.id.pin1)
        pinNum2 = findViewById(R.id.pin2)
        pinNum3 = findViewById(R.id.pin3)
        pinNum4 = findViewById(R.id.pin4)
        pinNum5 = findViewById(R.id.pin5)
        pinNum6 = findViewById(R.id.pin6)
        pinNum7 = findViewById(R.id.pin7)
        pinNum8 = findViewById(R.id.pin8)
        pinNum9 = findViewById(R.id.pin9)
        pinFingerScan = findViewById(R.id.pinFgp)
        pinDel = findViewById(R.id.del)

        pin1 = findViewById(R.id.pass1)
        pin2 = findViewById(R.id.pass2)
        pin3 = findViewById(R.id.pass3)
        pin4 = findViewById(R.id.pass4)

        btnClose = findViewById(R.id.btn_close)
        btnForgotPassword = findViewById(R.id.btn_forgot)

    }

    fun addPins() {
        pinNums.let {
            it.add(pinNum0)
            it.add(pinNum1)
            it.add(pinNum2)
            it.add(pinNum3)
            it.add(pinNum4)
            it.add(pinNum5)
            it.add(pinNum6)
            it.add(pinNum7)
            it.add(pinNum8)
            it.add(pinNum9)
        }

        pins.let {
            it.add(pin1)
            it.add(pin2)
            it.add(pin3)
            it.add(pin4)
        }
    }

    private fun onLoginSuccess(){

        startActivity(Intent(this, HomeActivity::class.java))
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        finish()
    }

    private fun instanceOfBiometricPrompt(): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(this)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext, "$errorCode :: $errString",   Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed",   Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onLoginSuccess()
            }
        }

        return BiometricPrompt(this, executor, callback)
    }

    private fun getPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
                .setTitle(resources.getString(R.string.finger_title))
                .setSubtitle(resources.getString(R.string.finger_sub_title))
                //.setDescription("My App is using Android biometric authentication")
                //.setDeviceCredentialAllowed(true)
                .setNegativeButtonText("ยกเลิก")
                .build()
    }

    private fun onFingerPrintScan(){
        val biometricManager = BiometricManager.from(applicationContext)
        val biometricPrompt = instanceOfBiometricPrompt()
        val promptInfo = getPromptInfo()
        val canAuthenticate = biometricManager.canAuthenticate()

        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            println("could not authenticate because: $canAuthenticate")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.del -> {
                viewModel.onDelPin(pins)
            }

            R.id.btn_close ->{
                finish()
            }

            R.id.pinFgp ->{
                onFingerPrintScan()
            }

            R.id.btn_forgot -> {
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}