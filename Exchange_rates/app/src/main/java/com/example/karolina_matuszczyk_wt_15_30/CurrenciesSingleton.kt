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
    private lateinit var arrow: String

    private var data: Array<CurrencyDetails>?=null

    private lateinit var countries:List<Country>
    fun prepereSingleton(context: Context) {
        queue = newRequestQueue(context)
        World.init(context)
        countries = World.getAllCountries().distinctBy { it.currency.code }
        data = emptyArray()
    }
    fun getQueue(): RequestQueue{
        return queue
    }

    fun loadData(response: JSONArray?, table:String) {

        response?.let {
            val rates = response.getJSONObject(0).getJSONArray("rates")
            val rates2 = response.getJSONObject(1).getJSONArray("rates")
            val ratesCount = rates.length()
            val tmpData = arrayOfNulls<CurrencyDetails>(ratesCount)
            for(i in 0 until ratesCount){
                val currencyCode = rates.getJSONObject(i).getString("code")
                val currencyRate = rates.getJSONObject(i).getDouble("mid")
                val currencyRate2 = rates2.getJSONObject(i).getDouble("mid")

                val flag = getFlagForCountries(currencyCode)

                if ((currencyRate2-currencyRate)>0.0){
                    arrow = "down"
                }else{
                    arrow = "up"

                }

                val currencyObject = CurrencyDetails(currencyCode, currencyRate, flag, table, arrow)
                tmpData[i] = currencyObject
            }
            var tmp = tmpData as Array<CurrencyDetails>
            data = data?.plus(tmp)
        }
    }

    fun getFlagForCountries(currencyCode:String):Int {
        if(currencyCode =="EUR"){
            return R.drawable.eu
        } else if (currencyCode =="GBP"){
           return R.drawable.gb
        } else if (currencyCode =="USD"){
           return R.drawable.us
        }else if (currencyCode =="CHF"){
            return R.drawable.ch
        }else if (currencyCode =="HDK"){
            return R.drawable.hk
        } else {
            return countries.find { it.currency.code ==currencyCode }?.flagResource ?:World.getWorldFlag()
        }
    }

    fun getData():Array<CurrencyDetails>{
        return data ?: emptyArray()
    }

}