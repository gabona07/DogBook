package com.example.dogbook.transitionlistener

import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.dogbook.R

object LoginTransitionListener : MotionLayout.TransitionListener {

    override fun onTransitionStarted(motionLayout: MotionLayout?, p1: Int, p2: Int) {
        motionLayout?.let {
            val signInBtn = it.findViewById<Button>(R.id.signInBtn)
            signInBtn.isClickable = false
        }
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentState: Int) {
        motionLayout?.let {
            val signInBtn = it.findViewById<Button>(R.id.signInBtn)
            if (currentState == R.id.LoginFormStateEnd) {
                signInBtn.text = ""
                it.setTransition(currentState, R.id.LoginBtnStateEnd)
                it.transitionToEnd()
            }
            if (currentState == R.id.start) {
                signInBtn.setText(R.string.sign_in_btn_text)
                signInBtn.isClickable = true
            }
        }
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }
}