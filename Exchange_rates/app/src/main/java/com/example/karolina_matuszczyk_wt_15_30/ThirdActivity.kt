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
    internal lateinit var fromPLN: Button
    internal lateinit var toPLN: Button
    internal var rates by Delegates.notNull<Double>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        wynik = findViewById(R.id.wynikPrzeliczenia)
        money = findViewById(R.id.moneyToCovert)
        fromPLN = findViewById(R.id.fromPLN)
        toPLN = findViewById(R.id.toPLN)

        World.init(applicationContext)
        adapter = CurrenciesAdapter(CurrenciesSingleton.getData(), this)
        CurrenciesSingleton.prepereSingleton(applicationContext)
        val pos = intent.getIntExtra("positionInArray", 0)

        picker = findViewById(R.id.numberPicker)
        countries = World.getAllCountries().distinctBy { it.currency.code }
        var countryCodes = arrayOfNulls<String>(countries.size)

        for(i in 0 until countries.size){

            countryCodes[i] = countries.get(i).currency.code
        }
        picker.minValue =0
        picker.maxValue = countries.size-1
        picker.displayedValues = countryCodes



        fromPLN.setOnClickListener {
            if(countryCodes[picker.value] != "BMD" && countryCodes[picker.value] != "SSD" && countryCodes[picker.value] != "XBT" &&
                    countryCodes[picker.value] != "KYD" && countryCodes[picker.value] != "PLN" && countryCodes[picker.value] != "FKP"){
                if(countryCodes[picker.value] == "NOK" || countryCodes[picker.value] == "CAD" || countryCodes[picker.value] == "CZK" ||
                        countryCodes[picker.value] == "HUF" || countryCodes[picker.value] == "CHF" || countryCodes[picker.value] == "EEK" ||
                        countryCodes[picker.value] == "SEK" || countryCodes[picker.value] == "DKK" || countryCodes[picker.value] == "JPY" ||
                        countryCodes[picker.value] == "USD" || countryCodes[picker.value] == "GBP" || countryCodes[picker.value] == "EUR"){

                    makeRequestA(countryCodes[picker.value])

                }
                else{
                    makeRequest(countryCodes[picker.value])
                }
            }
        }

        toPLN.setOnClickListener {
            if(countryCodes[picker.value] != "BMD" && countryCodes[picker.value] != "SSD" && countryCodes[picker.value] != "XBT" &&
                    countryCodes[picker.value] != "KYD" && countryCodes[picker.value] != "PLN" && countryCodes[picker.value] != "FKP"){
                if(countryCodes[picker.value] == "NOK" || countryCodes[picker.value] == "CAD" || countryCodes[picker.value] == "CZK" ||
                        countryCodes[picker.value] == "HUF" || countryCodes[picker.value] == "CHF" || countryCodes[picker.value] == "EEK" ||
                        countryCodes[picker.value] == "SEK" || countryCodes[picker.value] == "DKK" || countryCodes[picker.value] == "JPY"
                        || countryCodes[picker.value] == "USD" || countryCodes[picker.value] == "GBP" || countryCodes[picker.value] == "EUR"){
                    makeRequestA2(countryCodes[picker.value])

                }
                else{
                    makeRequest2(countryCodes[picker.value])
                }
            }
        }



    }

    private fun makeRequest(s: String?){
        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/rates/B/%s/".format(s)
        val currenciesRatesRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    println("Sukces!")
                    rates = response.getJSONArray("rates").getJSONObject(0).getDouble("mid")
                    //loadDetails(response)
                    setData(rates)
                },
                Response.ErrorListener { error ->
                    val message =getString(R.string.message)
                    Toast.makeText(this@ThirdActivity, message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@ThirdActivity, MainActivity::class.java).apply {
                    }
                    startActivity(intent)
                })
        queue.add(currenciesRatesRequest)
    }
    private fun makeRequestA(s: String?){
        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/rates/A/%s/".format(s)
        val currenciesRatesRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    println("Sukces!")
                    rates = response.getJSONArray("rates").getJSONObject(0).getDouble("mid")
                    //loadDetails(response)
                    setData(rates)
                },
                Response.ErrorListener { error ->
                    val message =getString(R.string.message)
                    Toast.makeText(this@ThirdActivity, message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@ThirdActivity, MainActivity::class.java).apply {
                    }
                    startActivity(intent)
                })
        queue.add(currenciesRatesRequest)
    }

    private fun makeRequest2(s: String?){
        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/rates/B/%s/".format(s)
        val currenciesRatesRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    println("Sukces!")
                    rates = response.getJSONArray("rates").getJSONObject(0).getDouble("mid")
                    //loadDetails(response)
                    setData2(rates)
                },
                Response.ErrorListener { error ->
                    val message =getString(R.string.message)
                    Toast.makeText(this@ThirdActivity, message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@ThirdActivity, MainActivity::class.java).apply {
                    }
                    startActivity(intent)
                })
        queue.add(currenciesRatesRequest)
    }

    private fun makeRequestA2(s: String?){
        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/rates/A/%s/".format(s)
        val currenciesRatesRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    println("Sukces!")
                    rates = response.getJSONArray("rates").getJSONObject(0).getDouble("mid")
                    //loadDetails(response)
                    setData2(rates)
                },
                Response.ErrorListener { error ->
                    val message =getString(R.string.message)
                    Toast.makeText(this@ThirdActivity, message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@ThirdActivity, MainActivity::class.java).apply {
                    }
                    startActivity(intent)
                })
        queue.add(currenciesRatesRequest)
    }

    private fun setData2(rates: Double) {
        if (!wynik.text.isEmpty()) {
            var ile = wynik.getText().toString().toDouble()
            var result = ile.times(rates)
            money.setText(result.toString())
        }


    }


    private fun setData(rates: Double) {
        if (!money.text.isEmpty()){
            var ile = money.getText().toString().toDouble()
            var result = ile.div(rates)
            wynik.setText(result.toString())
        }

        //wynik.text = result.toString()

    }


//    private fun loadDetails(response: JSONObject?) {
//        response?.let {
//            val rates = response.getJSONArray("rates").getJSONObject(0).getDouble("mid")
//        }
//
//    }

}