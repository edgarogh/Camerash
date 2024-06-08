package fr.free.edgar35740.camerash

import androidx.core.content.ContextCompat

import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.LineGraphSeries

class ChartController(private val view: GraphView, range: Int) {

    private val data = Array(range) { i -> DataPoint(i, 0) }
    private val chartData = LineGraphSeries(data)
    private var max = 0f

    init {
        view.addSeries(chartData)
    }

    fun pushNumber(number: Int) {
        if (number !in 0..data.size) return
        data[number]++
        max = max.coerceAtLeast(data[number].y.toFloat())
        chartData.isDrawDataPoints = true
        chartData.setAnimated(true)
        chartData.color = ContextCompat.getColor(view.context, R.color.colorAccent)
        view.viewport.isYAxisBoundsManual = true
        view.viewport.isXAxisBoundsManual = true
        view.viewport.setMaxY(max.toDouble())
        view.viewport.setMaxX((data.size - 1).toDouble())
        view.removeAllSeries()
        view.addSeries(chartData)
    }

    fun toIntArray() = data.map { it.y }.toIntArray()

    fun fromIntArray(state: IntArray) {
        assert(state.size == data.size)
        for (pair in data.asSequence().zip(state.asSequence())) {
            pair.first.y = pair.second
        }
    }

}
