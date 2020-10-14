package com.example.dogbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.dogbook.R
import com.example.dogbook.adapter.ViewPagerAdapter
import com.example.dogbook.model.AuthUser
import com.example.dogbook.model.Dog
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlin.math.abs

class MainPageActivity : AppCompatActivity() {

    private val adapter = ViewPagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        viewPagerInit()
    }

    fun navigateToForm(view: View) {
        val user = intent.getParcelableExtra<AuthUser>(FormActivity.AUTH_USER_KEY)
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra(FormActivity.AUTH_USER_KEY, user)
        startActivity(intent)
    }

    private fun viewPagerInit() {
        dogsViewPager.apply {
            adapter = this@MainPageActivity.adapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            this.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val pageTransformer = CompositePageTransformer()
            pageTransformer.addTransformer(MarginPageTransformer(10))
            pageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.95f + r * 0.05f
            }
            setPageTransformer(pageTransformer)
        }

        // Add placeholder dummy data
        adapter.setDogsData(arrayListOf(
            Dog("", "", "Kora", "", "", "", ""),
            Dog("", "", "Mex", "", "", "", "")
        ))
    }
}