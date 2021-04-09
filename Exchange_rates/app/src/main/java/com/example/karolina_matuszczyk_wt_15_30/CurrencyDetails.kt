package layout

import com.example.karolina_matuszczyk_wt_15_30.R

data class CurrencyDetails(var currencyCode: String, var currentRate: Double, var flag: Int = 0, var table:String, var arrow:String) {
    init {
        if(currencyCode =="EUR"){
            this.flag = R.drawable.eu
        } else if (currencyCode =="GBP"){
            this.flag = R.drawable.gb
        } else if (currencyCode =="USD"){
            this.flag = R.drawable.us
        }
    }
    
}