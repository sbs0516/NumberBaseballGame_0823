package com.nepplus.numberbaseballgame_0823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nepplus.numberbaseballgame_0823.adapters.MessageAdapter
import com.nepplus.numberbaseballgame_0823.datas.MessageData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mMessageList = ArrayList<MessageData>()

    lateinit var mAdapter:MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMessageList.add(MessageData("안녕하세요","CPU"))
        mMessageList.add(MessageData("반갑습니다","USER"))

        mAdapter = MessageAdapter(this, R.layout.message_list_item, mMessageList)

        messageListView.adapter = mAdapter
    }
}