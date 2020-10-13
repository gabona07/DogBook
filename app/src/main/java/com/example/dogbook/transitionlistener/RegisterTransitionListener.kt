package com.example.dogbook.transitionlistener

import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.dogbook.R

object RegisterTransitionListener : MotionLayout.TransitionListener {

    override fun onTransitionStarted(motionLayout: MotionLayout?, p1: Int, p2: Int) {
        motionLayout?.let {
            val signUpBtn = it.findViewById<Button>(R.id.signUpBtn)
            signUpBtn.isClickable = false
        }
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentState: Int) {
        motionLayout?.let {
            val signUpBtn = it.findViewById<Button>(R.id.signUpBtn)
            when (currentState) {
                R.id.registerFormStateEnd -> {
                    signUpBtn.text = ""
                    it.transitionToState(R.id.registerBtnStateEnd)
                }
                R.id.start -> {
                    signUpBtn.setText(R.string.sign_up_btn_text)
                    signUpBtn.isClickable = true
                }
            }
        }
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
}