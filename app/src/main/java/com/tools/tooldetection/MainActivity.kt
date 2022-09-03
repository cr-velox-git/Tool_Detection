package com.tools.tooldetection

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.io.File
import java.io.FileInputStream


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: FileViewModel

    var imageView: ImageView? = null
    var select: Button? = null
    var test: Button? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[FileViewModel::class.java]

        imageView = findViewById(R.id.imageView)
        select = findViewById(R.id.select)
        test = findViewById(R.id.test)

        select?.setOnClickListener {
            openImagePicker()
        }

        test?.setOnClickListener {
            uploadFile()
        }

    }

    private fun uploadFile() {
        if (imageUri != null) {
            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(imageUri!!, "r", null) ?: return
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(imageUri!!))
            file.createNewFile()
//            val outputStream = FileOutputStream(file)
//            inputStream.copyTo(outputStream)
            file.outputStream().use {
                inputStream.copyTo(it)
            }
            viewModel.uploadImage(file)
        } else {
            Toast.makeText(this, "select image", Toast.LENGTH_SHORT).show()
        }

    }


    private fun openImagePicker() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeType = arrayOf("image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICKER) {
            imageUri = data?.data
            imageView?.setImageURI(imageUri)
        }
    }

    @SuppressLint("Range")
    fun ContentResolver.getFileName(uri: Uri): String {
        var name = ""
        val cursor = query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }


}