package com.example.karolina_matuszczyk_wt_15_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.blongho.country_data.Country
import com.blongho.country_data.World
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import layout.CurrencyDetails
import org.json.JSONArray
import org.json.JSONObject
import kotlin.properties.Delegates

class ThirdActivity : AppCompatActivity() {
    internal lateinit var picker: NumberPicker
    private lateinit var countries:List<Country>
    internal lateinit var adapter: CurrenciesAdapter
    internal lateinit var wynik: EditText
    internal lateinit var money: EditText
    internal lateinit var directionButton: ToggleButton
    internal lateinit var convertButton: Button

    internal lateinit var currencies: Array<CurrencyDetails>
    internal lateinit var currentCurrency: CurrencyDetails


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        wynik = findViewById(R.id.wynikPrzeliczenia)
        money = findViewById(R.id.moneyToCovert)
        directionButton = findViewById(R.id.toggleButton)
        convertButton = findViewById(R.id.convert)
        convertButton.isActivated = false
        picker = findViewById(R.id.numberPicker)


        makeRequest()

        convertButton.setOnClickListener {
            convert()
        }

    }

    private fun convert() {
        if (directionButton.isChecked == true && !money.text.isEmpty()){
            val rate = 1/currentCurrency.currentRate
            val result = rate*money.text.toString().toDouble()
            wynik.setText(result.toString())
        }  else if(directionButton.isChecked == false && !wynik.text.isEmpty()){
            val result = currentCurrency.currentRate*wynik.text.toString().toDouble()
            money.setText(result.toString())
        }

    }


    fun makeRequest() {

        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/tables/A?format=json"
        val currenciesRatesRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    loadData(response, "A")
                    setPicker()
                },
                Response.ErrorListener { error ->
                    val message =getString(R.string.message)
                    Toast.makeText(this@ThirdActivity, message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@ThirdActivity, MainActivity::class.java).apply {
                    }
                    intent.putExtra("error",true)
                    startActivity(intent)
                })
        queue.add(currenciesRatesRequest)
    }


    fun loadData(response: JSONArray?, table:String) {

        response?.let {
            val rates = response.getJSONObject(0).getJSONArray("rates")
            val ratesCount = rates.length()
            val tmpData = arrayOfNulls<CurrencyDetails>(ratesCount)
            for(i in 0 until ratesCount){
                val currencyCode = rates.getJSONObject(i).getString("code")
                val currencyRate = rates.getJSONObject(i).getDouble("mid")
                val flag = CurrenciesSingleton.getFlagForCountries(currencyCode)
                val currencyObject = CurrencyDetails(currencyCode, currencyRate, flag, table, "up")
                tmpData[i] = currencyObject
            }
            currencies = tmpData as Array<CurrencyDetails>
        }
        convertButton.isActivated = true

    }
    private fun setPicker() {
        picker.minValue =0
        currentCurrency = currencies.first()
        picker.maxValue =currencies.size-1
        picker.displayedValues = currencies.map { it.currencyCode}.toTypedArray()
        val valueListener = NumberPicker.OnValueChangeListener{_,_,newVal->
            currentCurrency = currencies[newVal]

        }
        picker.setOnValueChangedListener(valueListener)

    }


}