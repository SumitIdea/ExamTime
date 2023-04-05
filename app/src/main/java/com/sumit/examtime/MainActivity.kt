package com.sumit.examtime

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sumit.examtime.fragment.FirstFragment
import com.sumit.examtime.fragment.SecondFragment
import com.sumit.examtime.fragment.ThirdFragment

class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment= FirstFragment()
        val secondFragment= SecondFragment()
        val thirdFragment= ThirdFragment()
        setCurrentFragment(firstFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home->setCurrentFragment(firstFragment)
                R.id.navigation_resources->setCurrentFragment(secondFragment)
                R.id.navigation_profile->setCurrentFragment(thirdFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}
