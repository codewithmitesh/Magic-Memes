package com.example.magicmemes


import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_meme_page.*


class memePage : AppCompatActivity() {

    var currentImageurl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme_page)
        loaded()
    }
     fun loaded(){

         progressBar.visibility =View.VISIBLE

        // Instantiate the RequestQueue.

        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest  = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                currentImageurl = response.getString("url")

//                val options: RequestOptions = RequestOptions()
//                    .centerCrop()


                Glide.with(this).load(currentImageurl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                       progressBar.visibility = View.GONE
                           return false
       }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false

                    }





                }).into(memeImageView)

            },
            Response.ErrorListener {
                Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_LONG).show()
            })
           MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)



//      / Add the request to the RequestQueue.
   //   MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
}



//


    fun nextmeme(view: View) {
        loaded()
    }
    fun sharememe(view: View) {
   val  intent  = Intent(Intent.ACTION_SEND)
        intent.type  = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"HI!!!! Checkout This Cool MEME I Get From MY Magic Mems App $currentImageurl")

        val chooser = Intent.createChooser(intent,"Share This Meme Using...")
        startActivity(chooser)

    }
}