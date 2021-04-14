// Karolina Matuszczyk

// zrobione:
// obsługa obu tabel NBP, kursy walut w recyclerView, strzałki symbolizujące wzrost lub spadek kursu
// po wyborze waluty przejście do nowego activity z kursami: dzisiejszym i wczorajszym oraz dwoma wykresami
// kurs złota z wykresem
// przelicznik walut: konwersja w obie strony PLN <-> inna waluta, przelicznik działa dla kursów z tabeli A


package com.example.karolina_matuszczyk_wt_15_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import layout.CurrencyDetails
import org.json.JSONArray
import java.net.ResponseCache

class MainActivity : AppCompatActivity() {
    internal lateinit var button: Button
    internal lateinit var button2: Button
    internal lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CurrenciesSingleton.prepereSingleton(applicationContext)

        if (intent.getBooleanExtra("error", true)) {
            Toast.makeText(this@MainActivity, "Problem z internetem", Toast.LENGTH_LONG).show()
        }

        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        button.setOnClickListener{
            val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
            }
            startActivity(intent)
        }
        button2.setOnClickListener{
            val intent = Intent(this@MainActivity, ThirdActivity::class.java).apply {
            }
            startActivity(intent)
        }
        button3.setOnClickListener{
            val intent = Intent(this@MainActivity, FourthActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }

}