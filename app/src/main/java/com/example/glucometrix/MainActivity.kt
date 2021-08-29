package com.example.glucometrix

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.glucometrix.Chart.Graph
import com.example.glucometrix.articles.Articles
import com.example.glucometrix.calendar.Calendar
import com.example.glucometrix.indicators.Indicators
import com.example.glucometrix.maps.MapsActivity

class MainActivity : AppCompatActivity() {
    private val DESC: String = "DESC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val cardGraphs = findViewById<CardView>(R.id.card_view_graph)
        val cardResults = findViewById<CardView>(R.id.card_view_results)
        val cardMeasures = findViewById<CardView>(R.id.card_view_measures)
        val cardArticles = findViewById<CardView>(R.id.card_view_articles)
        val cardMap = findViewById<CardView>(R.id.map_view)
        val cardCalendar = findViewById<CardView>(R.id.card_view_calendar)

        cardCalendar.setOnClickListener{
            val thread = Thread{
                startActivity(Intent(this, Calendar::class.java))
            }
            thread.start()
        }
        cardMap.setOnClickListener{
            val thread = Thread{
                startActivity(Intent(this, MapsActivity::class.java))
            }
            thread.start()
        }
        cardGraphs.setOnClickListener(){
            val thread = Thread{
                startActivity(Intent(this, Graph::class.java))
            }
            thread.start()
        }
        cardResults.setOnClickListener(){
            val thread = Thread{
                startActivity(Intent(this, GlucoResults::class.java))
            }
            thread.start()
        }
        cardMeasures.setOnClickListener{
            val thread = Thread{
                startActivity(Intent(this, Indicators::class.java))
            }
            thread.start()
        }
        cardArticles.setOnClickListener(){
            val thread = Thread{
                startActivity(Intent(this, Articles::class.java))
            }
            thread.start()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_glucose -> {
            val thread = Thread{
                //val bundle = 3
                //intent.putExtra(DESC, bundle);
                startActivity(Intent(this, AddGluco::class.java))
            }
            thread.start()
            true
        }

        R.id.action_account -> {
            val thread = Thread{
                //val bundle = 3
                //intent.putExtra(DESC, bundle);
                startActivity(Intent(this, Account::class.java))
            }
            thread.start()
            true
        }
        R.id.action_logout -> {
            val thread = Thread{
                //val bundle = 3
                //intent.putExtra(DESC, bundle);
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {

    }

}