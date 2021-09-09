package com.sheng.wang.media.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

/**
 * 拍照
 */
object ImageIntentUtils {
    /**
     * 照相
     */
    fun takePicture(context: Activity, requestCode: Int): String? {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(context.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = FileUtils.createFile(context, Environment.DIRECTORY_PICTURES, ".jpg")
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photoFile != null) {
                val uri: Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri =
                        FileProvider.getUriForFile(context, context.packageName + ".FileProvider", photoFile)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } else {
                    uri = Uri.fromFile(photoFile)
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                context.startActivityForResult(intent, requestCode)
            }
            return photoFile?.absolutePath
        }
        return null
    }
}