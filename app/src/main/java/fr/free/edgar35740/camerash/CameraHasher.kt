package fr.free.edgar35740.camerash

import android.os.Handler

import com.google.android.cameraview.CameraView


class CameraHasher(private val handler: Handler, private val callback: (digest: ByteArray)->Unit) :
        CameraView.Callback() {

    override fun onFramePreview(cameraView: CameraView, data: ByteArray, width: Int, height: Int,
                                orientation: Int) {
        onData(data)
    }

    override fun onPictureTaken(cameraView: CameraView, data: ByteArray) {
        onData(data)
    }

    private fun onData(data: ByteArray) {
        val digest = data.hashSHA1()
        handler.post { callback(digest) }
    }

}
