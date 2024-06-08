package fr.free.edgar35740.camerash

import android.media.Image
import android.os.Handler

import com.otaliastudios.cameraview.frame.Frame
import com.otaliastudios.cameraview.frame.FrameProcessor
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private val SHA1: MessageDigest = try {
    MessageDigest.getInstance("SHA-1")
} catch (e: NoSuchAlgorithmException) {
    throw RuntimeException(e)
}

class CameraHasher(private val handler: Handler, private val callback: (digest: ByteArray)->Unit) :
        FrameProcessor {

    override fun process(frame: Frame) {
        if (frame.dataClass == ByteArray::class.java) {
            SHA1.reset()
            SHA1.update(frame.getData<ByteArray>())
            returnDigest(SHA1.digest())
        } else if (frame.dataClass == Image::class.java) {
            val image = frame.getData<Image>()
            for (plane in image.planes) {
                SHA1.update(plane.buffer)
            }
            returnDigest(SHA1.digest())
        }
    }

    private fun returnDigest(digest: ByteArray) {
        handler.post { callback(digest) }
    }

}
