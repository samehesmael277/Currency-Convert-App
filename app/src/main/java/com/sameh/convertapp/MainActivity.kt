package com.sameh.convertapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val EGY = "Egyption pound"
    private val AMERECA = "Amerecan dolalr"
    private val AED = "AED"
    private val GBP = "GBP"
    private val EURO = "euro"

    private lateinit var con_btn: Button
    private lateinit var amount: TextInputEditText
    private lateinit var finally_result : TextInputEditText
    private lateinit var toDropDown : AutoCompleteTextView
    private lateinit var fromDropDown: AutoCompleteTextView
    private lateinit var toolbarAction: Toolbar

    private val myMapItems = mapOf(
        AMERECA to 1.0,
        EGY to 23.15,
        AED to 3.67,
        GBP to 0.86,
        EURO to 1.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialization()
        createAdaptor()
        toolbarAction.inflateMenu(R.menu.menu_action)
        toolbarAction.setOnMenuItemClickListener { item_toolbar ->

            when (item_toolbar.itemId) {
                R.id.share -> {
                    val textMessage = "${amount.text.toString()}  ${fromDropDown.text.toString()}" +
                            " is equal ${finally_result.text.toString()}  ${toDropDown.text.toString()} "
                    val myIntShare = Intent(Intent.ACTION_SEND)
                    myIntShare.type = "text/plain"
                    myIntShare.putExtra(Intent.EXTRA_TEXT, textMessage)
                    startActivity(myIntShare)
                }
                R.id.settings -> {
                    Toast.makeText(this, "its from settings", Toast.LENGTH_SHORT).show()
                }
                R.id.contact_facebook -> {
                    Toast.makeText(this, "its from facebook", Toast.LENGTH_SHORT).show()
                }
                R.id.instgram -> {
                    Toast.makeText(this, "its from instgram", Toast.LENGTH_SHORT).show()
                }
                R.id.whatsapp -> {
                    Toast.makeText(this, "its from whatsapp", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        amount.addTextChangedListener {
            calcResult()
        }
        fromDropDown.setOnItemClickListener { adapterView, view, i, l ->
            calcResult()
        }
        toDropDown.setOnItemClickListener { adapterView, view, i, l ->
            calcResult()
        }

    }


    private fun calcResult() {
        if (amount.text.toString().isNotEmpty()) {
            val amountC = amount.text.toString().toDouble()
            val fromCurrency = myMapItems[fromDropDown.text.toString()]
            val toCurrency = myMapItems[toDropDown.text.toString()]
            val res = amountC.times(toCurrency!!.div(fromCurrency!!))
            finally_result.setText(res.toString())
        }
        else {
            amount.setError("field is not null")
//                var snack = Snackbar.make(fromDropDown,"amount is not null",Snackbar.LENGTH_SHORT)
//                snack.show()
//                snack.setAction("show") {
//                    Toast.makeText(this, "you should enter number", Toast.LENGTH_SHORT).show()
//                }
        }
    }

    private fun initialization() {
        con_btn = findViewById(R.id.convert_button)
        amount = findViewById(R.id.amount_failed)
        finally_result = findViewById(R.id.resutl_place)
        toDropDown = findViewById(R.id.to_drop_down)
        fromDropDown = findViewById(R.id.fromCurrencey)
        toolbarAction = findViewById(R.id.toolbaraction)
    }

    private fun createAdaptor() {
        val list_adaptor = listOf(EGY, AMERECA, AED, GBP, EURO)
        val APAPTOR = ArrayAdapter(this, R.layout.drop_down, list_adaptor)
        toDropDown.setAdapter(APAPTOR)
        fromDropDown.setAdapter(APAPTOR)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
//        val MenuInflater: MenuInflater = menuInflater
//        MenuInflater.inflate(R.menu.menu_action, menu)
        return true
    }


    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        checkIfIdEqualItemMenuBarAndTakeActions(item)
        return true
    }
   */

}

