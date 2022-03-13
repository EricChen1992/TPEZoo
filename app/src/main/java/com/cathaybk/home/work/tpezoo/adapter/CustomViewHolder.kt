package com.cathaybk.home.work.tpezoo.adapter


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cathaybk.home.work.tpezoo.DataContract
import com.cathaybk.home.work.tpezoo.HttpsTrustManager
import com.cathaybk.home.work.tpezoo.R
import com.cathaybk.home.work.tpezoo.WebImageSingleton
import com.cathaybk.home.work.tpezoo.api.PlantResponse
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), DataContract.ICustomItemView{
    override fun <T> onBindData(itemObject: T) {

        if (itemObject is ZooBuildingEntity){
            with(itemObject as ZooBuildingEntity) {
                val imageView = itemView.findViewById<ImageView>(R.id.item_icon)
                HttpsTrustManager().allowALLSSL()
                WebImageSingleton.getInstance(itemView.context).setImageParameter(0, itemObject.bPicURL.replace("http://", "https://").trim().replace(" ", ""), imageView)

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
                HttpsTrustManager().allowALLSSL()
                val imageView = itemView.findViewById<ImageView>(R.id.plant_icon)
                imageView.setImageBitmap(null)
                WebImageSingleton.getInstance(itemView.context).setImageParameter(0, itemObject.fPic01URL.replace("http://", "https://").trim().replace(" ", ""), imageView)

                itemView.findViewById<TextView>(R.id.plant_name)?.let {
                    it.text = fNameCh
                }

                itemView.findViewById<TextView>(R.id.plant_sub_name)?.let {
                    it.text = fAlsoKnown
                }
            }
        }
    }

}