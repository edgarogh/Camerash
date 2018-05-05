package fr.free.edgar35740.camerash

import com.jjoe64.graphview.series.DataPointInterface

class DataPoint(private var x: Int, private var y: Int) : DataPointInterface {

    override fun getX(): Double {
        return x.toDouble()
    }

    override fun getY(): Double {
        return y.toDouble()
    }

    operator fun inc(): DataPoint {
        y++
        return this
    }

}
