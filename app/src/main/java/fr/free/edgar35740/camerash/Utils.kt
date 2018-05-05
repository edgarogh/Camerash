package fr.free.edgar35740.camerash

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private val SHA1: MessageDigest = try {
    MessageDigest.getInstance("SHA-1")
} catch (e: NoSuchAlgorithmException) {
    throw RuntimeException(e)
}

fun ByteArray.hashSHA1(): ByteArray {
    SHA1.update(this)
    return SHA1.digest()
}
