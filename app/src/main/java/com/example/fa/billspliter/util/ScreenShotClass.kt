package com.example.fa.billspliter.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ScreenShotClass {
    var ImagePath: File? = null
    var context: Context? = null

    constructor(context: Context?) {
        this.context = context
    }


    public fun takeScreenshot(view: View): Bitmap {
        val rootView: View = view.rootView
        rootView.isDrawingCacheEnabled = true
        return rootView.drawingCache
    }

    public fun saveBitmap(bitmap: Bitmap) {
        val path: String = Environment.getExternalStorageDirectory().toString()
        ImagePath = File(path, "/DCIM/Screenshots/screenshot.png")

        Log.d("Path", ImagePath!!.path.toString())
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(ImagePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("Image share error:", e.message, e)
        } catch (e: IOException) {
            Log.e("Image share error:", e.message, e)
        }
    }


    public fun sharebuttonIntent() {
        val uri: Uri = Uri.fromFile(ImagePath)
        val myIntent: Intent = Intent(Intent.ACTION_SEND)
        myIntent.setType("image/*")
        val shareBody: String = "Below Show the Bill Amount of the Bill and How many Each Person Should Pay"
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
        myIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(context!!, Intent.createChooser(myIntent, "Share Using : "), null)
    }
}