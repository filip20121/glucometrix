package com.example.glucometrix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class Whiteboard : AppCompatActivity() {
    var manager = supportFragmentManager

    private val DESC: String = "DESC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whiteboard)

        val intent = intent
      /*  when (intent.getStringExtra(DESC)?.toInt()) {
            1 -> {
                showGraph()
            }
            2 -> {
                showGluco()
            }
            3 -> showMeasures()
        }*/
        //showGluco()
    }

    private fun showMeasures() {
        TODO("Not yet implemented")
    }

    private fun showGraph() {
        TODO("Not yet implemented")
    }

    private fun showGluco() {
        val transaction = manager.beginTransaction()
        val fragment = GlucoTableList()

        transaction.replace(R.id.resultsFragmHolder, fragment)

        transaction.addToBackStack(null)
        transaction.commit()
    }
}