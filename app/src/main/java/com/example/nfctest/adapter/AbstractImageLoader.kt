package com.example.nfctest.adapter

import android.widget.ImageView

abstract class AbstractImageLoader(val path: String) {

    //需要复写该方法加载图片
    abstract fun loadImage(imageView: ImageView, path: String)
}