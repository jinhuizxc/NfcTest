package com.example.nfctest.adapter

import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseViewHolder
 */
class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mView: SparseArray<View> = SparseArray()

    fun <V : View> getView(id: Int): V {
        var view: View? = mView.get(id)
        if (view == null) {
            view = itemView.findViewById(id)
            mView.put(id, view)
        }
        return view as V
    }

    fun setText(id: Int, text: String): BaseViewHolder {
        val tv = getView<TextView>(id)
        tv.text = text
        return this
    }

    fun setImageResources(viewId: Int, resId: Int): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    fun setImagePath(viewId: Int, abstractImageLoader: AbstractImageLoader): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        abstractImageLoader.loadImage(view, abstractImageLoader.path)
        return this
    }

    fun setVisibility(viewId: Int, visibility: Int):BaseViewHolder{
        val view = getView<View>(viewId)
        view.visibility = visibility
        return this
    }

    fun setOnItemClickListener(listener: View.OnClickListener): BaseViewHolder{
        itemView.setOnClickListener(listener)
        return this
    }


}