package com.paiwaddev.kmids.nfcbuscheck

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.nfc.*
import android.nfc.tech.MifareClassic
import android.nfc.tech.NfcA
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.paiwaddev.kmids.kmidsmobile.view.custom.SigleAlertDialog
import com.paiwaddev.kmids.nfcbuscheck.data.model.Journey
import com.paiwaddev.kmids.nfcbuscheck.viewModel.SendNotificationsViewModel
import com.paiwaddev.kmids.nfcbuscheck.views.StartTransportFragment
import com.paiwaddev.kmids.nfcbuscheck.views.custom.PersonInfoAlertDialog
import com.paiwaddev.kmids.nfcbuscheck.views.custom.ProgressBuilder
import com.paiwaddev.kmids.nfcbuscheck.views.ui.HomeActivity
import com.paiwaddev.kmids.nfcbuscheck.views.ui.home.HomeViewModel
import com.paiwaddev.kmids.nfcbuscheck.views.ui.home.end.EndTransportFragment
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NFCReaderActivity : AppCompatActivity(), NfcAdapter.CreateNdefMessageCallback {

    private val homeViewModel: HomeViewModel by viewModel()
    private val sendNotificationsViewModel: SendNotificationsViewModel by viewModel()

    private var nfcAdapter: NfcAdapter? = null
    private var IsEndTransport: Boolean = false
    private lateinit var sharePrefUser: SharedPreferences
    private var mMobileUserID: Int = 0
    private var mItemsJourney = emptyList<Journey>()

    private lateinit var dialog: ProgressBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_reader)

        sharePrefUser = getSharedPreferences("USER_BUSCHECK", Context.MODE_PRIVATE)
        mMobileUserID = sharePrefUser.getInt("MobileUserID", 0)


        homeViewModel.errorMessage().observe(this, {
            if(it!=null){
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })


        homeViewModel.isLoading().observe(this, {
            dialog = get { parametersOf(this) }
            if (it) {
                try {
                    dialog.showProgressDialog()
                } catch (e: Exception) {
                    println("e :" + e.message)
                }
            } else {
                dialog.dismissProgressDialog()
            }
        })


        val start: Button = findViewById(R.id.btn_start)
        val back: ConstraintLayout = findViewById(R.id.layout_back)
        val frame_back: FrameLayout = findViewById(R.id.fram_image_back)

        homeViewModel.countScan().observe(this,{
            if(it <= 0){
                start.isEnabled = false
                back.isEnabled = true;
                start.setBackgroundResource(R.drawable.round_button_empty)
                frame_back.setBackgroundResource(R.drawable.round_button_next)
            }else{
                start.isEnabled = true
                back.isEnabled = false
                start.setBackgroundResource(R.drawable.round_button_next)
                frame_back.setBackgroundResource(R.drawable.round_button_empty)
            }
        })

        val fragMan = supportFragmentManager
        val fragTran = fragMan.beginTransaction()
        fragTran.add(R.id.frameLayout, StartTransportFragment())
        fragTran.commit()


        start.setOnClickListener {

            this.IsEndTransport = !IsEndTransport
            System.out.println(IsEndTransport)

            //start
            if (IsEndTransport) {
                homeViewModel.getJourney(mMobileUserID).observe(this, { journey ->

                    mItemsJourney = journey.startjourney

                    sendNotificationsViewModel.sendNotification(SendNotificationsViewModel.IS_JOURNEY,journey.startjourney).observe(this,{response ->
                        println(response)

                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.frameLayout, EndTransportFragment(journey))
                            commit()
                        }
                    })

                })
            } else {//end
                homeViewModel.getUpdateArrive(mMobileUserID).observe(this, { arrive ->
                    if (arrive.Isstatus != "Success") {
                        Toast.makeText(this, arrive.Isstatus, Toast.LENGTH_LONG).show()
                    } else {

                        sendNotificationsViewModel.sendNotification(SendNotificationsViewModel.IS_ARRIVE, mItemsJourney).observe(this,{response ->
                            println(response)

                            homeViewModel.resetCountingScan()

                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                            finish()
                        })

                    }
                })
            }
        }


        back.setOnClickListener {
            homeViewModel.clearError()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            finish()
        }
    }


    override fun createNdefMessage(event: NfcEvent?): NdefMessage {
        println("event")
        val text = "Beam me up, Android!\n\n" +
                "Beam Time: " + System.currentTimeMillis()
        return NdefMessage(
            arrayOf(
                NdefRecord.createMime("text/plain", text.toByteArray())
            )
        )
    }

    fun getUID(intent: Intent): String {
        val myTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        return myTag.toString()
    }

    override fun onResume() {
        super.onResume()


        NfcAdapter.getDefaultAdapter(this)?.let { nfcAdapter ->
            val launchIntent = Intent(this, this.javaClass)
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            val pendingIntent = PendingIntent.getActivity(
                this, 0, launchIntent, PendingIntent.FLAG_CANCEL_CURRENT
            )

            // Define your filters and desired technology types
            val filters = arrayOf(
                IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED), IntentFilter(
                    NfcAdapter.ACTION_TAG_DISCOVERED
                )
            )
            val techTypes = arrayOf(
                arrayOf(NfcA::class.java.name),
                arrayOf(MifareClassic::class.java.name)
            )

            nfcAdapter.enableForegroundDispatch(
                this, pendingIntent, filters, techTypes
            )
        }
    }

    fun setupForegroundDispatch(activity: Activity, adapter: NfcAdapter) {
        val intent = Intent(activity.applicationContext, activity.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 0, intent, 0)
        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()

        // Notice that this is the same filter as in our manifest.
        filters[0] = IntentFilter()
        filters[0]!!.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filters[0]!!.addCategory(Intent.CATEGORY_DEFAULT)
        try {
            filters[0]!!.addDataType("text/plain")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("Check your mime type.")
        }
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        processIntent(intent)
    }

    fun bytesToHex(bytes: ByteArray?): String {
        val ret = StringBuilder()
        if (bytes != null) {
            for (b in bytes) {
                ret.append(String.format("%02X", b.toInt() and 0xFF))
            }
        }
        val result = StringBuilder()
        var i = 0
        while (i <= ret.length - 2) {
            result.append(StringBuilder(ret.substring(i, i + 2)).reverse())
            i = i + 2
        }
        var card = result.reverse().toString()
        if (card.length == 8) {
            card = """
            ${card.toLong(16)}


            """.trimIndent()
        }
        if (card.length == 10) {
            card = """
            ${card.toLong(16)}
            
            
            """.trimIndent()
        }
        return card
    }

    private fun processIntent(intent: Intent) {
        // only one message sent during the beam

        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            println(bytesToHex(tag?.getId()).trimEnd())

            val destinationID: Int = getIntent().getIntExtra("BusDestinationID", 0)

            val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.frameLayout)

            sendNotificationsViewModel.errorMessage().observe(this,{err->
                println("err : "+err)
            })

            if(currentFragment is StartTransportFragment){
                homeViewModel.getPersonInfo(
                    bytesToHex(tag?.getId()).trimEnd(),
                    destinationID,
                    mMobileUserID
                ).observe(this, { person ->
                    person?.let {
                        if(person.PersonID!=0) {
                            val alert = PersonInfoAlertDialog.newInstance(person)
                            alert.show(supportFragmentManager, SigleAlertDialog.TAG)

                            sendNotificationsViewModel.sendNotification(person.PersonID,person.Name,SendNotificationsViewModel.IS_SCAN).observe(this,{response ->
                                println(response)
                            })
                        }else{
                            Toast.makeText(this,"${bytesToHex(tag?.getId()).trimEnd()}, ไม่พบข้อมูล\nPersonID: ${person.PersonID}",Toast.LENGTH_SHORT).show()
                        }

                        homeViewModel.getPersonInfo(
                                bytesToHex(tag?.getId()).trimEnd(),
                                destinationID,
                                mMobileUserID
                        ).removeObservers(this)
                    }
                })
            }


        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}
enum class STATE(var value: Int){
    START(0),
    COMPLETE(1),
    FINISH(2)
}