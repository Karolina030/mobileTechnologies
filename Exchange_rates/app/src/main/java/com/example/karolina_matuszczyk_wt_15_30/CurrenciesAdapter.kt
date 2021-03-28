package com.example.karolina_matuszczyk_wt_15_30

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import layout.CurrencyDetails

class CurrenciesAdapter(var dataSet: Array<CurrencyDetails>) : RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val currencyCodeTextView: TextView
        val rateTextView: TextView
        val flagView: ImageView

        init{
            currencyCodeTextView = view.findViewById(R.id.currencyCode)
            rateTextView = view.findViewById(R.id.rate)
            flagView = view.findViewById(R.id.flag)

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

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        
        val currency = dataSet[position]
        viewHolder.currencyCodeTextView.text = currency.currencyCode
        viewHolder.rateTextView.text = currency.currentRate.toString()
        viewHolder.flagView.setImageResource(currency.flag)
        viewHolder.itemView.setOnClickListener{goToDetails(position)}
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun goToDetails(position: Int){
        TODO("Not emplemented")
    }




}
