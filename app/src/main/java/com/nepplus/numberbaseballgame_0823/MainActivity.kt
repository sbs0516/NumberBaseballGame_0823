package com.nepplus.numberbaseballgame_0823

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nepplus.numberbaseballgame_0823.adapters.MessageAdapter
import com.nepplus.numberbaseballgame_0823.datas.MessageData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mMessageList = ArrayList<MessageData>()
    var isGameEnd = false

    lateinit var mAdapter:MessageAdapter

    val mQuestionNumbers = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeQuestionNumbers()

        mAdapter = MessageAdapter(this, R.layout.message_list_item, mMessageList)

        messageListView.adapter = mAdapter

        okBtn.setOnClickListener {
            val inputNumStr = numberEdt.text.toString()

            val msg = MessageData(inputNumStr, "USER")

            mMessageList.add(msg)
            mAdapter.notifyDataSetChanged()

            numberEdt.setText("")
            messageListView.smoothScrollToPosition(mMessageList.lastIndex)

            // 새 게임 시작할 수 있게 하는 코드

            if(isGameEnd) {
                if (inputNumStr == "0") {

                    makeQuestionNumbers()
                    messageListView.smoothScrollToPosition(mMessageList.lastIndex)

                } else if (inputNumStr.toInt() in 1..9) {

                    Toast.makeText(this, "게임을 종료합니다.", Toast.LENGTH_SHORT).show()
                    numberEdt.isEnabled = false

                } else {

                    checkAnswer(inputNumStr.toInt())
                }
            } else {
                checkAnswer(inputNumStr.toInt())
            }
        }
    }

    fun makeQuestionNumbers() {

        // 반복문 대신 리스트 활용하여 shuffled 로 리스트 섞기

        val numberList = mutableListOf(1,2,3,4,5,6,7,8,9)
        val numberShuffledList =  numberList.shuffled()

        isGameEnd = false
        mQuestionNumbers.clear()

        mQuestionNumbers.add(numberShuffledList[0])
        mQuestionNumbers.add(numberShuffledList[1])
        mQuestionNumbers.add(numberShuffledList[2])

        mMessageList.add(MessageData("어서오세요.","CPU"))
        mMessageList.add(MessageData("숫자 야구 게임입니다.","CPU"))
        mMessageList.add(MessageData("세자리 숫자를 맞춰주세요.","CPU"))
    }

    fun checkAnswer(inputNum: Int) {
        val userInputNumArr = ArrayList<Int>()
        userInputNumArr.add(inputNum / 100)
        userInputNumArr.add((inputNum / 10) % 10)
        userInputNumArr.add(inputNum % 10)

        var strikeCount = 0
        var ballCount = 0

        for(i in 0..2) {
            for(j in 0..2) {
                if(userInputNumArr[i] == mQuestionNumbers[j]) {
                    if(i == j) {
                        strikeCount++
                    } else {
                        ballCount++
                    }
                }
            }
        }

        if(strikeCount == 3) {

            isGameEnd = true

            mMessageList.add(MessageData("축하합니다. 정답입니다.", "CPU"))
            mMessageList.add(MessageData("새 게임을 시작하시겠습니까?", "CPU"))
            mMessageList.add(MessageData("0. 새 게임\n1~9. 게임 종료", "CPU"))

            mAdapter.notifyDataSetChanged()
            messageListView.smoothScrollToPosition(mMessageList.lastIndex)
        } else {

            mMessageList.add(MessageData("${strikeCount}S ${ballCount}B 입니다", "CPU"))
            mAdapter.notifyDataSetChanged()
            messageListView.smoothScrollToPosition(mMessageList.lastIndex)

        }
    }

}