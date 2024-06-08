package fr.free.edgar35740.camerash

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.cameraview.CameraView
import com.google.android.material.snackbar.Snackbar
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlin.experimental.and

class MainActivity : AppCompatActivity(), PermissionListener {

    private val content by lazy { findViewById<ViewGroup>(R.id.content) }
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

    private var permissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        TedPermission.create()
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
        permissionGranted = true
        showSnackbar(resources.getString(R.string.permission_granted))
    }

    override fun onPermissionDenied(deniedPermissions: MutableList<String>) {
        finish()
    }

}
