package com.example.glucometrix.articles

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.glucometrix.Account
import com.example.glucometrix.AddGluco
import com.example.glucometrix.Login
import com.example.glucometrix.R

class Articles : AppCompatActivity() {
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val cardInsulinoodpornosc = findViewById<CardView>(R.id.card_view_insulinoodpornosc)
        val cardDiet = findViewById<CardView>(R.id.card_view_diet)
        val cardPowiklania = findViewById<CardView>(R.id.card_view_powiklania)
        val cardPrzykazania = findViewById<CardView>(R.id.card_view_przykazania)

        cardInsulinoodpornosc.setOnClickListener(){
            val thread = Thread{
                val transaction = manager.beginTransaction()
                val fragment = WebPageFragment("https://www.cukrzyca.pl/cukrzyca-typu-2/wskazniki-do-oceny-wystepowania-insulinoopornosci/")

                transaction.replace(R.id.WebPage, fragment)

                transaction.addToBackStack(null)
                transaction.commit()
            }
            thread.start()
        }
        cardDiet.setOnClickListener(){
            val thread = Thread{
                val transaction = manager.beginTransaction()
                val fragment = WebPageFragment("https://www.medme.pl/diety-lecznicze/cukrzyca/co-moze-jesc-cukrzyk.html")

                transaction.replace(R.id.WebPage, fragment)

                transaction.addToBackStack(null)
                transaction.commit()
            }
            thread.start()
        }
        cardPowiklania.setOnClickListener(){
            val thread = Thread{
                val transaction = manager.beginTransaction()
                val fragment = WebPageFragment("https://www.poradnikzdrowie.pl/zdrowie/cukrzyca/powiklania-cukrzycy-wczesne-ostre-i-pozne-przewlekle-aa-bVsR-jQiF-c6WT.html")

                transaction.replace(R.id.WebPage, fragment)

                transaction.addToBackStack(null)
                transaction.commit()
            }
            thread.start()
        }
        cardPrzykazania.setOnClickListener(){
            val thread = Thread{
                val transaction = manager.beginTransaction()
                val fragment = WebPageFragment("https://pacjent.gov.pl/jak-zyc-z-choroba/jak-zyc-z-cukrzyca")
                transaction.replace(R.id.WebPage, fragment)

                transaction.addToBackStack(null)
                transaction.commit()
            }
            thread.start()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_glucose -> {
            val thread = Thread{
                startActivity(Intent(this, AddGluco::class.java))
            }
            thread.start()
            true
        }

        R.id.action_account -> {
            val thread = Thread{
                startActivity(Intent(this, Account::class.java))
            }
            thread.start()
            true
        }
        R.id.action_logout -> {
            val thread = Thread{
                startActivity(Intent(this, Login::class.java))
                finish()
            }
            thread.start()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        title = "Artykuły"
        return super.onCreateOptionsMenu(menu)
    }
}