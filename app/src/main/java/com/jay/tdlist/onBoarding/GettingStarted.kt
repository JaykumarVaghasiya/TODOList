package com.jay.tdlist.onBoarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.jay.tdlist.MainActivity
import com.jay.tdlist.R
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

class GettingStarted : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences= getSharedPreferences("onboarding",Context.MODE_PRIVATE)
        val onboardingCompleted=sharedPreferences.getBoolean("onboarding_completed",false)

        if(onboardingCompleted){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            setContentView(R.layout.activity_getting_started)

            val viewPager:ViewPager2=findViewById(R.id.viewPager1)
            val dotIndicator:SpringDotsIndicator=findViewById(R.id.spring_dots_indicator)

            val fragments= arrayListOf(
                OnBoarding1Fragment(),
                OnBoarding2Fragment(),
                OnBoarding3Fragment()
            )

            val adapter=FragmentPagerAdapter(this,fragments)
            viewPager.adapter=adapter
            dotIndicator.attachTo(viewPager)
        }
    }
}