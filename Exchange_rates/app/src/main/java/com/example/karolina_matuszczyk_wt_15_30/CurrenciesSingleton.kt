package com.example.karolina_matuszczyk_wt_15_30

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley.newRequestQueue
import com.blongho.country_data.Country
import com.blongho.country_data.World
import layout.CurrencyDetails
import org.json.JSONArray
import java.util.*

object CurrenciesSingleton {
    private lateinit var queue: RequestQueue
    private var data: Array<CurrencyDetails>?=null
    private lateinit var countries:List<Country>
    fun prepereSingleton(context: Context) {
        queue = newRequestQueue(context)
        World.init(context)
        countries = World.getAllCountries().distinctBy { it.currency.code }

    }
    fun getQueue(): RequestQueue{
        return queue
    }

    fun loadData(response: JSONArray?) {
        response?.let {
            val rates = response.getJSONObject(0).getJSONArray("rates")
            val ratesCount = rates.length()
            val tmpData = arrayOfNulls<CurrencyDetails>(ratesCount)
            for(i in 0 until ratesCount){
                val currencyCode = rates.getJSONObject(i).getString("code")
                val currencyRate = rates.getJSONObject(i).getDouble("mid")
                val flag = countries.find { it.currency.code ==currencyCode }?.flagResource ?:World.getWorldFlag()
                val currencyObject = CurrencyDetails(currencyCode, currencyRate, flag)

                tmpData[i] = currencyObject
            }
            data = tmpData as Array<CurrencyDetails>
        }
    }
    fun getData():Array<CurrencyDetails>{
        return data ?: emptyArray()
    }

}