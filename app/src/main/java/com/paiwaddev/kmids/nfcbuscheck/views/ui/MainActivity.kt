package com.paiwaddev.kmids.nfcbuscheck.views.ui

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.nfc.*
import android.nfc.NdefRecord.createMime
import android.nfc.NfcAdapter.*
import android.nfc.tech.MifareClassic
import android.nfc.tech.NfcA
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paiwaddev.kmids.nfcbuscheck.R
import okhttp3.internal.and


class MainActivity : AppCompatActivity(), NfcAdapter.CreateNdefMessageCallback,
    View.OnClickListener {

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var textView: TextView
    private lateinit var readButton: Button
    private var isReader: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tv_driver_name)
        readButton = findViewById(R.id.button)

        // Check for available NFC Adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        // Register callback
        nfcAdapter?.setNdefPushMessageCallback(this, this)

        readButton.setOnClickListener(this)
    }

    override fun createNdefMessage(event: NfcEvent?): NdefMessage {
        println("event")
        val text = "Beam me up, Android!\n\n" +
                "Beam Time: " + System.currentTimeMillis()
        return NdefMessage(
                arrayOf(
                        createMime("text/plain", text.toByteArray())
                )
        )
    }

    fun getUID(intent: Intent): String {
        val myTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        return myTag.toString()
    }

    override fun onResume() {
        super.onResume()

        /*if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {

            processIntent(intent)
            Toast.makeText(this, getUID(intent), Toast.LENGTH_SHORT).show()

        }*/

        NfcAdapter.getDefaultAdapter(this)?.let { nfcAdapter ->
            val launchIntent = Intent(this, this.javaClass)
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            val pendingIntent = PendingIntent.getActivity(
                    this, 0, launchIntent, PendingIntent.FLAG_CANCEL_CURRENT
            )

            // Define your filters and desired technology types
            val filters = arrayOf(IntentFilter(ACTION_NDEF_DISCOVERED), IntentFilter(ACTION_TAG_DISCOVERED))
            val techTypes = arrayOf(arrayOf(NfcA::class.java.name), arrayOf(MifareClassic::class.java.name))

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
        filters[0]!!.addAction(ACTION_NDEF_DISCOVERED)
        filters[0]!!.addCategory(Intent.CATEGORY_DEFAULT)
        try {
            filters[0]!!.addDataType("text/plain")
        } catch (e: MalformedMimeTypeException) {
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
            /*card = """
            0${card.toLong(16)}
            
            
            """.trimIndent()*/
            card = """
            ${card.toLong(16)}
            
            
            """.trimIndent()
        }
        if (card.length == 9) {
            card = """
            ${card.toLong(16)}
            """.trimIndent()
        }
        return card
    }

    private fun processIntent(intent: Intent) {
        textView = findViewById(R.id.tv_driver_name)
        // only one message sent during the beam

        if (ACTION_TECH_DISCOVERED == intent.action) {
            val tag = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
            //val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            //val tagId: String = byteToHex(tag)
            textView.text = byteToHex(tag!!).toString()
            /*println(tag?.getId().toString())
            println(tag?.techList)


            MifareClassic.get(tag)?.let { _tag ->

            }*/
            val aa = intent.getParcelableExtra<Tag>(EXTRA_TAG)
            println(bytesToHex(aa?.getId()))


            println(getUID(intent))
        }
    }

    fun byteToHex(args: ByteArray): String {
        var risultato = ""
        for (i in args.indices) {
            risultato += Integer.toString((args[i] and 0xff) + 0x100, 16).substring(1)
        }
        return risultato
    }

    override fun onClick(v: View?) {

        isReader = !isReader

        if(isReader) {
            readButton.text = "Stop"
        }else{
            readButton.text = "Read Tag"
        }

    }

}