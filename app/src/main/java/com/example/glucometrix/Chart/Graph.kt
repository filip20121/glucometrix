package com.example.glucometrix.Chart


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.glucometrix.DatabaseHandler
import com.example.glucometrix.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils


/* TODO see below
 */
class Graph : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {
    private var chart: LineChart? = null
    private var seekBarX: SeekBar? = null
    //private var seekBarY: SeekBar? = null
    private var tvX: TextView? = null
    private var tvY: TextView? = null
    private var hourArray = mutableListOf("08:00",
            "10:00", "12:00", "14:00",
            "16:00", "18:00", "20:00",
            "22:00")

    private var glucoArray = mutableListOf("120", "145",
            "101", "93",
            "168", "67",
            "113", "123")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_graph)
        val glucoSize =  DatabaseHandler(this).showNumOfGlucos()
        val hourList = DatabaseHandler(this).showHour()
        title = "Glucose results"
        tvX = findViewById(R.id.tvXMax)
        //tvY = findViewById(R.id.tvYMax)
        seekBarX = findViewById(R.id.seekBar1)
        seekBarX!!.max = glucoSize//hourArray.size
        seekBarX!!.setOnSeekBarChangeListener(this)
        //seekBarY = findViewById(R.id.seekBar2)
        //seekBarY!!.max = 180
        // seekBarY!!.setOnSeekBarChangeListener(this)

        run {
            // // Chart Style // //
            chart = findViewById(R.id.chart1)

            // background color
            chart!!.setBackgroundColor(Color.WHITE)

            // disable description text
            chart!!.description.isEnabled = false

            // enable touch gestures
            chart!!.setTouchEnabled(true)

            // set listeners
            chart!!.setOnChartValueSelectedListener(this)
            chart!!.setDrawGridBackground(false)

            // create marker to display box when values are selected
            val mv = MyMarkerView(this, R.layout.my_marker_view)

            // Set the marker to the chart
            mv.chartView = chart
            chart!!.marker = mv

            // enable scaling and dragging
            chart!!.isDragEnabled = true
            chart!!.setScaleEnabled(true)
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart!!.setPinchZoom(true)
        }
        var xAxis: XAxis
        run {
            // // X-Axis Style // //
            xAxis = chart!!.xAxis

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f)
        }
        var yAxis: YAxis
        run {
            // // Y-Axis Style // //
            yAxis = chart!!.axisLeft

            // disable dual axis (only use LEFT axis)
            chart!!.axisRight.isEnabled = false

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f)

            // axis range
            yAxis.axisMaximum = 400f
            yAxis.axisMinimum = 0f
        }

        xAxis.valueFormatter = IndexAxisValueFormatter(hourList)

        // add data
        seekBarX!!.progress = 1
       // seekBarY!!.progress = 180
        setData(glucoSize)

        // draw points over time
        chart!!.animateX(1500)

        // get the legend (only possible after setting data)
        val l = chart!!.legend

        // draw legend entries as lines
        l.form = LegendForm.LINE
    }

    //TODO - popraw Entry by poprawnie wskazywało godziny pomiarów przy zmienianiu seekBaru


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setData(count: Int) {
        val values: ArrayList<Entry> = ArrayList()
        val glucoList = DatabaseHandler(this).showGlucos()

        for (i in 0 until count) {
            val value = glucoList[i].toFloat() //Random.nextInt(0, 300).toFloat() //
            values.add(Entry(i.toFloat(), value, resources.getDrawable(R.drawable.fade_red)))
            // values.add(Entry(hourArray[i].toFloat(), value, resources.getDrawable(R.drawable.fade_red)))
        }
        val set1: LineDataSet
        if (chart!!.data != null && chart!!.data.dataSetCount > 0) {
            set1 = chart!!.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart!!.data.notifyDataChanged()
            chart!!.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "Your glucose measurements ")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 2f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 10f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart!!.axisLeft.axisMinimum }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart!!.data = data
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tvX!!.text = seekBarX!!.progress.toString()
        //tvY!!.text = seekBarY!!.progress.toString()
        setData(seekBarX!!.progress)//, seekBarY!!.progress.toFloat())

        // redraw
        chart!!.invalidate()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override fun onValueSelected(e: Entry, h: Highlight?) {
        Log.i("Entry selected", e.toString())
        Log.i("LOW HIGH", "low: " + chart!!.lowestVisibleX + ", high: " + chart!!.highestVisibleX)
        Log.i(
            "MIN MAX",
            "xMin: " + chart!!.xChartMin + ", xMax: " + chart!!.xChartMax + ", yMin: " + chart!!.yChartMin + ", yMax: " + chart!!.yChartMax
        )
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }
}