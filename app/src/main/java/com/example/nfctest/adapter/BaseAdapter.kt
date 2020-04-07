package com.example.nfctest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 设置适配器，布局，数据源，上下文
 */
abstract class BaseAdapter<T>(
    protected var mLayoutId: Int,  //布局id
    protected var mData: List<T>,  // 数据源
    protected var context: Context  // 上下文
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val mInflater: LayoutInflater
    private var mMultipleTypeSupport: MultipleTypeSupport<T>? = null
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    constructor(data: List<T>, context: Context, multipleTypeSupport: MultipleTypeSupport<T>)
            : this(-1, data, context) {
        mMultipleTypeSupport = multipleTypeSupport;
    }

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun getItemViewType(position: Int): Int {
        return mMultipleTypeSupport?.getLayoutId(mData[position], position)
            ?: super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (mMultipleTypeSupport != null) {
            mLayoutId = viewType
        }

        val itemView = mInflater.inflate(mLayoutId, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // 绑定数据
        bindData(holder, mData[position], position)
        mOnItemClickListener?.let {
            holder.itemView.setOnClickListener {
                mOnItemClickListener?.onItemClick(holder.itemView, position)
            }

            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener {
                    mOnItemClickListener?.onItemClick(holder.itemView, position)
                }
            }

            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener{
                    mOnItemLongClickListener?.onItemLongClickListener(holder.itemView, position)
                    false
                }
            }
        }
    }

    protected abstract fun bindData(holder: BaseViewHolder, t: T, position: Int)

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClickListener(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mOnItemLongClickListener = listener
    }


}