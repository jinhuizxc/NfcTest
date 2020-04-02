package com.example.nfctest.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * 设置适配器，布局，数据源，上下文
 */
abstract class BaseAdapter<T>(
    protected var mLayoutId: Int,  //布局id
    protected var mData: List<T>,  // 数据源
    protected var context: Context  // 上下文
) : RecyclerView.Adapter<BaseViewHolder>() {
}