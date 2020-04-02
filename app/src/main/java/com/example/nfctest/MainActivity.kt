package com.example.nfctest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nfctest.adapter.BaseAdapter
import com.example.nfctest.card.DefaultCardRecord

/**
 * NFC库，兼容4.3之前API以及4.4之后的API，读卡器模式，Sample读取羊城通卡号、余额、交易记录
 * https://github.com/scauzhangpeng/NfcSample
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mLlReadCard: LinearLayout
    private lateinit var mLlShowCard: LinearLayout

    private lateinit var mTvCardNumber: TextView
    private lateinit var mTvCardBalance: TextView

    private lateinit var mRvCardRecord: RecyclerView
    private lateinit var mAdapter: BaseAdapter<DefaultCardRecord>
    private lateinit var mCardRecords: MutableList<DefaultCardRecord>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initCardClient()

    }

    private fun initCardClient() {

    }

    private fun initViews() {
        mLlReadCard = findViewById(R.id.ll_read_card)
        mLlShowCard = findViewById(R.id.ll_show_card)

        mTvCardNumber = findViewById(R.id.tv_card_number)
        mTvCardBalance = findViewById(R.id.tv_card_balance)

        mRvCardRecord = findViewById(R.id.rv_card_record)
    }
}
