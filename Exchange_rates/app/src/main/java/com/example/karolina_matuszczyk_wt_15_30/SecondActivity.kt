package com.example.karolina_matuszczyk_wt_15_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.Volley.newRequestQueue
import layout.CurrencyDetails
import org.json.JSONArray

class SecondActivity : AppCompatActivity() {
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adapter: CurrenciesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        recyclerView = findViewById(R.id.recyclerID)
    //    recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

       // recyclerView.adapter = CurrenciesAdapter(dataSet)
        adapter = CurrenciesAdapter(CurrenciesSingleton.getData(), this)
        recyclerView.adapter = adapter

        //to dać do main Activity???
        CurrenciesSingleton.prepereSingleton(applicationContext)

        //do usunięcia
        makeRequest()
        makeRequest2()

    }

    fun makeRequest() {

        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/tables/A/last/2/?format=json"
        val currenciesRatesRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    println("success!!!")

                    CurrenciesSingleton.loadData(response, "A")
                    adapter.dataSet = CurrenciesSingleton.getData()
                    adapter.notifyDataSetChanged()


                },
                Response.ErrorListener { error ->
//                    val message =getString(R.string.message)
//                    Toast.makeText(this@SecondActivity, message, Toast.LENGTH_LONG).show()
//                    val intent = Intent(this@SecondActivity, MainActivity::class.java).apply {
//                    }
//                    startActivity(intent)
                })
        queue.add(currenciesRatesRequest)
    }


    fun makeRequest2(){

        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/tables/B/last/2/?format=json"
        val currenciesRatesRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    println("success!!!")
                    CurrenciesSingleton.loadData(response, "B")
                    adapter.dataSet = CurrenciesSingleton.getData()
                    adapter.notifyDataSetChanged()


                },
                Response.ErrorListener { error ->
//                    val message =getString(R.string.message)
//                    Toast.makeText(this@SecondActivity, message, Toast.LENGTH_LONG).show()
//                    val intent = Intent(this@SecondActivity, MainActivity::class.java).apply {
//                    }
//                    startActivity(intent)
                })
        queue.add(currenciesRatesRequest)



//        val queue = CurrenciesSingleton.getQueue()
//        val url = "http://api.nbp.pl/api/exchangerates/tables/A?format=json"
//        val currenciesRatesRequest = JsonArrayRequest(
//            Request.Method.GET, url, null,
//            Response.Listener { response ->
//                println("Sukces SecondActivity!")
//                CurrenciesSingleton.loadData(response, "A", "down")
//                adapter.dataSet = CurrenciesSingleton.getData()
//                adapter.notifyDataSetChanged()
//
//            },
//            Response.ErrorListener { error ->
//                val message =getString(R.string.message)
//                Toast.makeText(this@SecondActivity, message, Toast.LENGTH_LONG).show()
//                val intent = Intent(this@SecondActivity, MainActivity::class.java).apply {
//                }
//                startActivity(intent)
//            })
//        queue.add(currenciesRatesRequest)
    }

//    private fun loadData(response: JSONArray?):Array<CurrencyDetails> {
//        response?.let {
//            val rates = response.getJSONObject(0).getJSONArray("rates")
//            val ratesCount = rates.length()
//            val tmpData = arrayOfNulls<CurrencyDetails>(ratesCount)
//            for(i in 0 until ratesCount){
//                val currencyCode = rates.getJSONObject(i).getString("code")
//                val currencyRate = rates.getJSONObject(i).getDouble("mid")
//                val currencyObject = CurrencyDetails(currencyCode, currencyRate)
//                tmpData[i] = currencyObject
//            }
//            return tmpData as Array<CurrencyDetails>
//        }
//        return emptyArray()
//    }

}