package com.app.whirl.activities.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.widget.ImageView
import android.widget.TextView
import com.app.whirl.R
import com.jsibbold.zoomage.ZoomageView
import com.squareup.picasso.Picasso

class ProfilePhotoActivity : AppCompatActivity() {

    var images: ImageView? = null
    var arrowback: TextView? = null
    var primage: ZoomageView? = null
    var image_url = ""
    private var scaleGestureDetector: ScaleGestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_photo)
        image_url = intent.getStringExtra("image").toString()
        arrowback = findViewById(R.id.arrowback)
        primage = findViewById(R.id.primage)
        arrowback!!.setOnClickListener {
            onBackPressed()
        }
        if (image_url.equals("")) {
            Picasso.get().load(R.drawable.profile).into(primage)
        } else {
            Picasso.get().load(image_url).into(primage)

        }
    }
    override fun onResume() {
        super.onResume()
        try {

        } catch (e: Exception) {


        }
    }
}