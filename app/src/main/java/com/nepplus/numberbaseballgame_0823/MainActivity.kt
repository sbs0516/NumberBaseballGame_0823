package com.nepplus.numberbaseballgame_0823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nepplus.numberbaseballgame_0823.adapters.MessageAdapter
import com.nepplus.numberbaseballgame_0823.datas.MessageData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mMessageList = ArrayList<MessageData>()

    lateinit var mAdapter:MessageAdapter

    // 세자리 문제 숫자를 저장하기 위한 ArrayList
    val mQuestionNumbers = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeQuestionNumbers() // 세 자리 랜덤 문자 만들기


        mAdapter = MessageAdapter(this, R.layout.message_list_item, mMessageList)

        messageListView.adapter = mAdapter

        okBtn.setOnClickListener {
            val inputNumStr = numberEdt.text.toString()

            val msg = MessageData(inputNumStr, "USER")

            mMessageList.add(msg)
            mAdapter.notifyDataSetChanged()

            // numberEdt 의 문구를 비워주기
            numberEdt.setText("")
            // 문구를 비우고 나면, 리스트뷰를 맨 마지막 데이터로 내리기
            messageListView.smoothScrollToPosition(mMessageList.lastIndex)
        }
    }

    fun makeQuestionNumbers() {
        mQuestionNumbers.add(4)
        mQuestionNumbers.add(7)
        mQuestionNumbers.add(1)
        // 환영 메시지를 채팅창에 띄우자
        // 메시지 리스트에 띄워줄 만들 데이터를 추가하자

        mMessageList.add(MessageData("어서오세요.","CPU"))
        mMessageList.add(MessageData("숫자 야구 게임입니다.","CPU"))
        mMessageList.add(MessageData("세자리 숫자를 맞춰주세요.","CPU"))
    }

}