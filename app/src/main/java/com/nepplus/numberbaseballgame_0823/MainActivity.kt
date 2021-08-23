package com.nepplus.numberbaseballgame_0823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

            // 컴퓨터가 ?S ?B 을 판단하여 메시지 추가(답장)
            checkAnswer(inputNumStr.toInt())
        }
    }

    fun makeQuestionNumbers() {
//        mQuestionNumbers.add(4)
//        mQuestionNumbers.add(7)
//        mQuestionNumbers.add(1)

        // 랜덤한 3개 숫자 뽑아내기
        // 1~9만 사용하고, 중복 불가
//        for(i in 0..2) {
//            while(true) {
//                val randomNum = (Math.random() * 9 + 1).toInt()
//                // mQuest ~ 에 이미 들어있는지 검사
//                var isDuplOk = true
//                for(num in mQuestionNumbers) {
//                    if(num == randomNum) {
//                        // 중복된 숫자. 사용 불가
//                        isDuplOk = false
//                    }
//                }
//                if(isDuplOk) {
//                    mQuestionNumbers.add(randomNum)
//                    break
//                }
//            }
//        }

        var numberList = mutableListOf(1,2,3,4,5,6,7,8,9)
        numberList.shuffle()
        mQuestionNumbers.add(numberList[0])
        mQuestionNumbers.add(numberList[1])
        mQuestionNumbers.add(numberList[2])


        // 환영 메시지를 채팅창에 띄우자
        // 메시지 리스트에 띄워줄 만들 데이터를 추가하자

        mMessageList.add(MessageData("어서오세요.","CPU"))
        mMessageList.add(MessageData("숫자 야구 게임입니다.","CPU"))
        mMessageList.add(MessageData("세자리 숫자를 맞춰주세요.","CPU"))
    }

    fun checkAnswer(inputNum: Int) {
        // 유저가 입력한 숫자가 ?S ?B 인지 판단하는 함수

        // 사람이 입력한 숫자를 각 자리별로 나눠서 목록에 대입
        val userInputNumArr = ArrayList<Int>()
        userInputNumArr.add(inputNum / 100)
        userInputNumArr.add((inputNum / 10) % 10)
        userInputNumArr.add(inputNum % 10)

        var strikeCount = 0
        var ballCount = 0

        for(i in 0..2) {
            for(j in 0..2) {
                // 내가 입력한 숫자 i 번째와 컴퓨터가 낸 숫자 j 번째가 같은 숫자인지?
                if(userInputNumArr[i] == mQuestionNumbers[j]) {
                    // 같은 숫자를 찾음
                    // 위치도 같은지 여부 확인
                    if(i == j) {
                        strikeCount++
                    } else {
                        ballCount++
                    }
                }
            }
        }

        // 3S 라면, 축하한다는 메시지 출력 후 입력을 막음
        if(strikeCount == 3) {
            mMessageList.add(MessageData("축하합니다. 정답입니다.", "CPU"))
            mAdapter.notifyDataSetChanged()
            messageListView.smoothScrollToPosition(mMessageList.lastIndex)
            Toast.makeText(this, "게임을 종료합니다.", Toast.LENGTH_SHORT).show()
            // 입력을 막음
            numberEdt.isEnabled = false
        } else {
            // ?S ?B 인지를 컴퓨터가 말하는 것으로 처리
            mMessageList.add(MessageData("${strikeCount}S ${ballCount}B 입니다", "CPU"))
            mAdapter.notifyDataSetChanged()
            messageListView.smoothScrollToPosition(mMessageList.lastIndex)
        }
    }

}