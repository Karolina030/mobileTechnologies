package com.example.karolina_matuszczyk_wt_15_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import layout.CurrencyDetails
import org.json.JSONObject

class HistoricRatesDetailsActivity : AppCompatActivity() {
    internal lateinit var todayRate: TextView
    internal lateinit var yesterdayRate: TextView
    internal lateinit var lineChart: LineChart
    internal lateinit var lineChart2: LineChart

    internal lateinit var currency: CurrencyDetails
    internal lateinit var historicRates: Array<Pair<String,Double>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic_rates_details)
        todayRate =findViewById(R.id.todayRate)
        yesterdayRate=findViewById(R.id.yesterdayRate)
        lineChart=findViewById(R.id.chart)
        lineChart2=findViewById(R.id.chart2)
        val pos = intent.getIntExtra("positionInArray", 0)
        currency = CurrenciesSingleton.getData()[pos]
        getData()
        getData2()

    }

    private fun setData(){
        todayRate.text =getString(R.string.todayRateText, historicRates.last().second)
        yesterdayRate.text =getString(R.string.yesterdayRateText, historicRates[historicRates.size-2].second)
        var entries = ArrayList<Entry>()
        for((index, element) in historicRates.withIndex()){
            entries.add(Entry(index.toFloat(), element.second.toFloat()))
        }
        val lineData =LineData(LineDataSet(entries, "Kurs"))
        lineChart.data = lineData
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(historicRates.map { it.first }.toTypedArray())
        lineChart.invalidate()
        val desctiption = Description()
        desctiption.text = "Kurs %s z ostatnich 30 dni".format(currency.currencyCode)
        lineChart.description = desctiption


        lineChart.legend.isEnabled = false
        lineChart.xAxis.labelRotationAngle = -90f
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.extraBottomOffset = 45f

    }

    private fun setData2(){
        todayRate.text =getString(R.string.todayRateText, historicRates.last().second)
        yesterdayRate.text =getString(R.string.yesterdayRateText, historicRates[historicRates.size-2].second)
        var entries = ArrayList<Entry>()
        for((index, element) in historicRates.withIndex()){
            entries.add(Entry(index.toFloat(), element.second.toFloat()))
        }
        val lineData =LineData(LineDataSet(entries, "Kurs"))
        lineChart2.data = lineData
        lineChart2.xAxis.valueFormatter = IndexAxisValueFormatter(historicRates.map { it.first }.toTypedArray())
        lineChart2.invalidate()
        val desctiption = Description()
        desctiption.text = "Kurs %s z ostatnich 7 dni".format(currency.currencyCode)
        lineChart2.description = desctiption
        lineChart2.legend.isEnabled = false
        lineChart2.xAxis.labelRotationAngle = -90f
        lineChart2.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart2.extraBottomOffset = 45f

    }

    private fun getData(){
        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/rates/${currency.table}/%s/last/30/".format(currency.currencyCode)
        val historicalRatesRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                println("success!!!")
                loadDetails(response)
                setData()
            },
            Response.ErrorListener { error ->
                val message =getString(R.string.message)
                Toast.makeText(this@HistoricRatesDetailsActivity, message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@HistoricRatesDetailsActivity, SecondActivity::class.java).apply {
                }
                startActivity(intent)
            })
        queue.add(historicalRatesRequest)
    }
    private fun getData2(){
        val queue = CurrenciesSingleton.getQueue()
        val url = "http://api.nbp.pl/api/exchangerates/rates/${currency.table}/%s/last/7/".format(currency.currencyCode)
        val historicalRatesRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    println("success!!!")
                    loadDetails(response)
                    setData2()
                },
                Response.ErrorListener { error ->
                    val message =getString(R.string.message)
                    Toast.makeText(this@HistoricRatesDetailsActivity, message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@HistoricRatesDetailsActivity, SecondActivity::class.java).apply {
                    }
                    startActivity(intent)
                })
        queue.add(historicalRatesRequest)
    }

    private fun loadDetails(response: JSONObject?) {
        response?.let{
            val rates = response.getJSONArray("rates")
            val rateCount =rates.length()
            val tmpData = arrayOfNulls<Pair<String,Double>>(rateCount)

            for(i in 0 until rateCount){
                val date = rates.getJSONObject(i).getString("effectiveDate")
                val currencyRate = rates.getJSONObject(i).getDouble("mid")
                tmpData[i] = Pair(date, currencyRate)
            }
            this.historicRates = tmpData as Array<Pair<String, Double>>
        }

    }
}