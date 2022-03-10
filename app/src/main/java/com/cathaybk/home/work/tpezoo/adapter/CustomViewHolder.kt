package com.cathaybk.home.work.tpezoo.adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.cathaybk.home.work.tpezoo.DataContract
import com.cathaybk.home.work.tpezoo.R
import com.cathaybk.home.work.tpezoo.api.PlantResponse
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), DataContract.ICustomItemView{
    override fun <T> onBindData(itemObject: T) {

        if (itemObject is ZooBuildingEntity){
            with(itemObject as ZooBuildingEntity) {
                val imageView = itemView.findViewById<ImageView>(R.id.item_icon)
//                Glide.with(itemView.context)
//                    .load(itemObject.bPicURL.replace("http://", "https://").trim())
//                    .error(R.drawable.tpezoo_toolbar_image)
//                    .override(300, 300)
//                    .fitCenter()
//                    .into(imageView)
                getImageView(itemObject.bPicURL.replace("http://", "https://"), imageView)

                itemView.findViewById<TextView>(R.id.item_name)?.let {
                    it.text = bName
                }
                itemView.findViewById<TextView>(R.id.item_info)?.let {
                    it.text = bInfo
                }
            }
        }
        if (itemObject is PlantResponse.ResultsItem){
            with(itemObject as PlantResponse.ResultsItem){
                val imageView = itemView.findViewById<ImageView>(R.id.plant_icon)
                Glide.with(itemView.context)
                    .load(itemObject.fPic01URL.replace("http://", "https://").trim())
                    .error(R.drawable.tpezoo_icon)
                    .override(300, 300)
                    .fitCenter()
                    .into(imageView)

                itemView.findViewById<TextView>(R.id.plant_name)?.let {
                    it.text = fNameCh
                }

                itemView.findViewById<TextView>(R.id.plant_sub_name)?.let {
                    it.text = fAlsoKnown
                }
            }
        }
    }

    fun getImageView(imageUrl:String, imageview: ImageView){
        val imageRequest = ImageRequest(
            imageUrl,
            {bitmap -> // response listener
                imageview.setImageBitmap(bitmap)
            },
            300, // max width
            300, // max height
            ImageView.ScaleType.CENTER_CROP, // image scale type
            Bitmap.Config.RGB_565, // decode config
            {error-> // error listener
                Log.e("ImageError", error.toString())
                imageview.setImageResource(R.drawable.tpezoo_icon)
            }
        )

        Volley.newRequestQueue(itemView.context).add(imageRequest)
    }

}