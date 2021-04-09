package com.example.karolina_matuszczyk_wt_15_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.json.JSONArray
import org.json.JSONObject
import kotlin.properties.Delegates

class FourthActivity : AppCompatActivity() {
    internal lateinit var todayGoldRate: TextView
    internal lateinit var lineChart: LineChart
    internal lateinit var adapter: CurrenciesAdapter
    internal var rates by Delegates.notNull<Double>()
    internal lateinit var historicRates: Array<Pair<String,Double>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)
        adapter = CurrenciesAdapter(CurrenciesSingleton.getData(), this)
        CurrenciesSingleton.prepereSingleton(applicationContext)

        todayGoldRate =findViewById(R.id.todayGoldRate)
        lineChart=findViewById(R.id.chartGold)
        //makeRequest()
        getData()
    }

    fun makeRequest(){
        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/cenyzlota?format=json"
        val currenciesRatesRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                println("Sukces!")
                rates = response.getJSONObject(0).getDouble("cena")
                println(rates)
                setData(rates)

            },
            Response.ErrorListener { error ->
                val message =getString(R.string.message)
                Toast.makeText(this@FourthActivity, message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@FourthActivity, MainActivity::class.java).apply {
                }
                startActivity(intent)
            })
        queue.add(currenciesRatesRequest)
    }

    private fun setData(rates: Double) {
        todayGoldRate.text =getString(R.string.todayGoldRateText, rates.toFloat())

    }

    private fun getData(){
        println("getData!")

        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/cenyzlota/last/30/"
        val historicalRatesRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                println("response z gatData")
                loadDetails(response)
                setData2()
            },
            Response.ErrorListener { error ->
                println("error")
                println(error.message)

                val message =getString(R.string.message)
                Toast.makeText(this@FourthActivity, message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@FourthActivity, SecondActivity::class.java).apply {
                }
                startActivity(intent)
            })
        queue.add(historicalRatesRequest)
    }
    private fun loadDetails(response: JSONArray?) {
        response?.let{
            val rates = response
            val rateCount =rates.length()
            val tmpData = arrayOfNulls<Pair<String,Double>>(rateCount)

            for(i in 0 until rateCount){
                val date = rates.getJSONObject(i).getString("data")
                val currencyRate = rates.getJSONObject(i).getDouble("cena")
                tmpData[i] = Pair(date, currencyRate)
            }
            this.historicRates = tmpData as Array<Pair<String, Double>>
        }

    }

    private fun setData2(){
        todayGoldRate.text =getString(R.string.todayGoldRateText, historicRates.last().second)

        var entries = ArrayList<Entry>()
        for((index, element) in historicRates.withIndex()){
            entries.add(Entry(index.toFloat(), element.second.toFloat()))
        }
        val lineData = LineData(LineDataSet(entries, "Kurs"))
        lineChart.data = lineData
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(historicRates.map { it.first }.toTypedArray())
        lineChart.invalidate()
        val desctiption = Description()
        desctiption.text = "Kurs złota z ostatnich 30 dni"
        lineChart.description = desctiption

        lineChart.legend.isEnabled = false
        lineChart.xAxis.labelRotationAngle = -90f
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.extraBottomOffset = 45f

    }
}