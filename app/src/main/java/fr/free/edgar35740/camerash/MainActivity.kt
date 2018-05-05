package fr.free.edgar35740.camerash

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.experimental.and

class MainActivity : AppCompatActivity(), PermissionListener {

    private val chart by lazy { ChartController(view_graph, 16) }
    private val frameCallback by lazy {
        CameraHasher(Handler(mainLooper)) { digest ->
            val view = findViewById<TextView>(R.id.text_byte)
            if (view != null) view.text = (128 + digest[0]).toString()

            for (b in digest) {
                chart.pushNumber((b and 0x0f).toInt())
                chart.pushNumber((b and (0xf0 shr 4)).toInt())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        TedPermission.with(this)
                .setPermissionListener(this)
                .setDeniedMessage(resources.getString(R.string.permission_error))
                .setPermissions(Manifest.permission.CAMERA)
                .check()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(content, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun startCamera() {
        if (!camera.isCameraOpened && permissionGranted) {
            camera.autoFocus = true
            camera.addCallback(frameCallback)
            camera.scanning = true
            camera.start()
        }
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onPause() {
        if (camera.isCameraOpened) {
            camera.stop()
            camera.removeCallback(frameCallback)
        }
        super.onPause()
    }

    override fun onPermissionGranted() {
        startCamera()
        showSnackbar(resources.getString(R.string.permission_granted))
    }

    override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
        finish()
    }

    private val permissionGranted
        get () = TedPermission.isGranted(this, Manifest.permission.CAMERA)

}
