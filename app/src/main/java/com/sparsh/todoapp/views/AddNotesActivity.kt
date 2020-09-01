package com.sparsh.todoapp.views

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.sparsh.todoapp.BuildConfig
import com.sparsh.todoapp.R
import com.sparsh.todoapp.utils.AppConstant
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class AddNotesActivity : AppCompatActivity() {
    lateinit var editTextTitle : EditText
    lateinit var editTextDescription : EditText
    lateinit var submitButton : Button
    lateinit var imageViewAdd : ImageView
    val REQUEST_GALLERY_CODE = 2
    val REQUEST_CAMERA_CODE = 1
    val MY_PERMISSION_CODE = 124
    var picture_path = ""
    lateinit var imageLocation:File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindViews()
        clickListener()
    }

    private fun clickListener() {
        submitButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra("title",editTextTitle.text.toString())
                intent.putExtra("description",editTextDescription.text.toString())
                intent.putExtra("image_path",picture_path)
                setResult(RESULT_OK,intent)
                finish()
            }

        })
        imageViewAdd.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(checkAndRequestPermission()) {
                    setUpDialog()
                }
            }

        })
    }

    private fun checkAndRequestPermission(): Boolean {
        val permissionCamera = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        val permissionStorage = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionsNeeded = ArrayList<String>()
        if(permissionStorage != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(permissionCamera != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA)
        }
        if(listPermissionsNeeded.isNotEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toTypedArray<String>(),MY_PERMISSION_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    setUpDialog()
                }
            }
        }
    }

    private fun setUpDialog() {
        val view = LayoutInflater.from(this@AddNotesActivity).inflate(R.layout.dialog_selector,null)
        val textViewGallery = view.findViewById<TextView>(R.id.textViewGallery)
        val textViewCamera = view.findViewById<TextView>(R.id.textViewCamera)
        val dialog = AlertDialog.Builder(this).setView(view).setCancelable(true).create()
        textViewCamera.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                //to ensure that AddNotesActivity takes data from camera we resolve activity from which data is coming
                if(takePictureIntent.resolveActivity(packageManager)!=null){
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                    }
                    catch (e:Exception){

                    }

                    if(photoFile != null){
                        val photoUrl  = FileProvider.getUriForFile(this@AddNotesActivity,BuildConfig.APPLICATION_ID + ".provider",photoFile)
                        imageLocation = photoFile
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUrl)
                        dialog.hide()
                        startActivityForResult(takePictureIntent,REQUEST_CAMERA_CODE)

                    }

                }
            }

        })
        textViewGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,REQUEST_GALLERY_CODE)
                //Implement onActivityResult to handle data coming from Dialog Box of image insertion
                dialog.hide()
            }

        })
        dialog.show()
    }

    private fun createImageFile(): File? {
        val timestamp:String? = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val filename:String? = "JPEG" + timestamp + "_"
        val storageDir:File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(filename,"jpg",storageDir)

    }

    private fun bindViews() {
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        submitButton = findViewById(R.id.buttonSubmit)
        imageViewAdd = findViewById(R.id.imageViewAdd)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CAMERA_CODE ->{
                    picture_path = imageLocation.path.toString()
                    Glide.with(this).load(imageLocation.absoluteFile).into(imageViewAdd)

                }
                REQUEST_GALLERY_CODE -> {
                    val selectedImage = data?.data
                    picture_path = selectedImage?.path.toString()
                    Glide.with(this).load(selectedImage?.path).into(imageViewAdd)
                }
            }
        }
    }

}