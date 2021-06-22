package `in`.bitcode.intentreuse

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    lateinit var edtPath: EditText
    lateinit var btnShowImage: Button
    lateinit var btnSendBR: Button
    lateinit var btnShowImageInGal: Button
    lateinit var btnVideo: Button
    lateinit var btnAudio: Button
    lateinit var btnWeb: Button
    lateinit var btnCall: Button
    lateinit var btnShareImage: Button
    lateinit var btnPickImage: Button
    lateinit var imgUser: ImageView


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())

        init()

        btnShowImage.setOnClickListener(BtnShowImageClickListener())

        btnShowImageInGal.setOnClickListener() {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                Uri.parse(edtPath.text.toString()),
                "image/jpeg"
            )
            startActivity(intent)
        }

        btnVideo.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW)

            //intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.setDataAndType(
                Uri.parse(edtPath.text.toString()),
                "video/mp4"
            )
            startActivity(intent)
        }

        btnAudio.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                Uri.parse(edtPath.text.toString()),
                "audio/mp3"
            )
            startActivity(intent)
        }

        btnWeb.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(edtPath.text.toString())
            startActivity(intent)
        }

        btnCall.setOnClickListener {
            var intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(edtPath.text.toString())
            startActivity(intent)
        }

        btnShareImage.setOnClickListener {
            var intent = Intent(Intent.ACTION_SEND)
            intent.setDataAndType(
                Uri.parse(edtPath.text.toString()),
                "image/jpeg"
            )
            startActivity(intent)
        }

        btnPickImage.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(intent, 1)
        }

        btnSendBR.setOnClickListener {
            var intent = Intent("in.bitcode.media.DOWNLOAD_COMPLETE")
            intent.putExtra("path", edtPath.text.toString())
            sendBroadcast(intent)

            if(ActivityCompat.checkSelfPermission(MainActivity@this, android.Manifest.permission.BROADCAST_STICKY) == PackageManager.PERMISSION_GRANTED) {
                var stickyIntent = Intent("in.bitcode.event.COMPLETE")
                sendStickyBroadcast(stickyIntent)
            }

        }
    }

    private inner class BtnShowImageClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            var intent = Intent("in.bitcode.image.SHOW")
            intent.addCategory("in.bitcode.category.MEDIA")
            intent.setDataAndType(
                Uri.parse(edtPath.text.toString()),
                "image/jpeg"
            )
            //intent.action = "in.bitcode.image.SHOW"
            //intent.putExtra("path", edtPath.text.toString())
            startActivity(intent)
        }
    }

    private fun init() {
        edtPath = findViewById(R.id.edtPath)
        btnShowImage = findViewById(R.id.btnShowImage)
        btnShowImageInGal = findViewById(R.id.btnShowImageInGal)
        btnVideo = findViewById(R.id.btnVideo)
        btnAudio = findViewById(R.id.btnAudio)
        btnWeb = findViewById(R.id.btnWeb)
        btnCall = findViewById(R.id.btnCall)
        btnShareImage = findViewById(R.id.btnShare)
        btnPickImage = findViewById(R.id.btnPickImage)
        imgUser = findViewById(R.id.imgUser)
        btnSendBR = findViewById(R.id.btnSendBR)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            Log.e("tag", data.data.toString())
            imgUser.setImageURI(data.data)
        }
    }
}


