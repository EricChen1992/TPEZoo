package com.cathaybk.home.work.tpezoo

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class WebImageSingleton constructor(context: Context) {
    companion object{
        @Volatile
        private var INSTANCE : WebImageSingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: WebImageSingleton(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    private val imageLoader : ImageLoader by lazy {
        ImageLoader(
            requestQueue,
            object : ImageLoader.ImageCache {
                private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024 ).toInt()
                private val cacheSize = maxMemory / 8
                private val imageCache = LruCache<String, Bitmap>(cacheSize)
                override fun getBitmap(p0: String?): Bitmap? {
                    return imageCache.get(p0)
                }

                override fun putBitmap(p0: String?, p1: Bitmap?) {
                    imageCache.put(p0, p1)
                }
            })
    }

    fun setImageParameter(type: Int, imageUrl: String, imageview: ImageView){
        val imageListener: ImageLoader.ImageListener? = ImageLoader.getImageListener(
            imageview,
            if (type==0) R.drawable.tpezoo_icon else R.drawable.tpezoo_toolbar_image,
            if (type==0) R.drawable.tpezoo_icon else R.drawable.tpezoo_toolbar_image,
        )

        imageLoader?.run {
            get(imageUrl,
                imageListener,
                300,
                300,
                ImageView.ScaleType.CENTER_CROP
            )
        }
    }

    fun <T> addRequestQueue(req : Request<T>){
        requestQueue.add(req)
    }
}