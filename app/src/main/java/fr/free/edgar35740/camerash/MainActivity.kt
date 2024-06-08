package fr.free.edgar35740.camerash

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import kotlin.experimental.and

class MainActivity : AppCompatActivity() {

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val chart by lazy { ChartController(findViewById(R.id.view_graph), 16) }
    private val camera by lazy { findViewById<CameraView>(R.id.camera) }
    private val frameCallback by lazy {
        CameraHasher(Handler(mainLooper)) { digest ->
            val view = findViewById<TextView>(R.id.text_byte)
            if (view != null) view.text = getString(R.string.random_number_preview, 128 + digest[0])

            for (b in digest) {
                chart.pushNumber((b and 0x0f).toInt())
                chart.pushNumber((b and ((0xf0 shr 4).toByte())).toInt())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        camera.setLifecycleOwner(this)
        camera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)
    }

    override fun onResume() {
        super.onResume()
        camera.addFrameProcessor(frameCallback)
        camera.open()
    }

    override fun onPause() {
        camera.close()
        camera.clearFrameProcessors()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("chart", chart.toIntArray())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getIntArray("chart")?.let { saved ->
            chart.fromIntArray(saved)
        }
    }

}
