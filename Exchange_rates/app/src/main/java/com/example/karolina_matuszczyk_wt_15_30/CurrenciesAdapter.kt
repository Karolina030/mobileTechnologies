package com.example.karolina_matuszczyk_wt_15_30

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import layout.CurrencyDetails

class CurrenciesAdapter(var dataSet: Array<CurrencyDetails>, val context:Context) : RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val currencyCodeTextView: TextView
        val rateTextView: TextView
        val flagView: ImageView
        val arrow: ImageView

        init{
            currencyCodeTextView = view.findViewById(R.id.currencyCode)
            rateTextView = view.findViewById(R.id.rate)
            flagView = view.findViewById(R.id.flag)
            arrow = view.findViewById(R.id.arrow)

        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.currency_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val currency = dataSet[position]
        viewHolder.currencyCodeTextView.text = currency.currencyCode
        viewHolder.rateTextView.text = currency.currentRate.toString()
        viewHolder.flagView.setImageResource(currency.flag)
        if(currency.arrow=="up"){
            viewHolder.arrow.setImageResource(R.drawable.red_arrow)
        } else{
            viewHolder.arrow.setImageResource(R.drawable.green_arrow)
        }

        viewHolder.itemView.setOnClickListener{goToDetails(position)}
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun goToDetails(position: Int){
        val intent = Intent(context, HistoricRatesDetailsActivity::class.java).apply {
            putExtra("positionInArray", position)
        }
        context.startActivity(intent)
    }




}
