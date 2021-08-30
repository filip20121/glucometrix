package com.example.glucometrix

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.glucometrix.dataClass.GlucoseData
import java.util.*

class AddGluco : AppCompatActivity() {

    @SuppressLint("ShowToast", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gluco)

        val addButton: Button = findViewById(R.id.AddButton)
        val glucoseText: EditText = findViewById(R.id.editGlucoseText)
        val spinner: Spinner = findViewById(R.id.desc_spinner)
        val confirm: TextView = findViewById(R.id.addConfirmation)

        addButton.setOnClickListener{
            val descText = spinner.selectedItem.toString()
            val success = DatabaseHandler(applicationContext).addGlucose(GlucoseData(Calendar.getInstance().time.toString(), glucoseText.text.toString(), descText))
            if(success != -1L){
                confirm.text = "Pomyślnie dodano"
            }else{
                confirm.text = "Wystąpił błąd. Pomiar nie został dodany"
            }
        }

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.desc_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }
    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {
        var desc: String = ""

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            val spinner: Spinner = findViewById(R.id.desc_spinner)
            spinner.onItemSelectedListener = this
            desc = spinner.selectedItem.toString()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }
}