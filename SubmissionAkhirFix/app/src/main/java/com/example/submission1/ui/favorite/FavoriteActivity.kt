package com.example.submission1.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission1.R
import kotlinx.android.synthetic.main.activity_home.*

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val sectionsFavPagerAdapter = SectionsFavPagerAdapter(this, supportFragmentManager)
        view_pager.adapter=sectionsFavPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }
}