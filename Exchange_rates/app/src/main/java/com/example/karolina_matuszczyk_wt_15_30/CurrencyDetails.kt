package layout

import com.example.karolina_matuszczyk_wt_15_30.R

data class CurrencyDetails(var currencyCode: String, var currentRate: Double, var flag: Int = 0) {
    init {
        if(currencyCode =="EUR"){
            this.flag = R.drawable.eu
        } else if (currencyCode =="GBP"){
            this.flag = R.drawable.gb

        }
    }
    
}