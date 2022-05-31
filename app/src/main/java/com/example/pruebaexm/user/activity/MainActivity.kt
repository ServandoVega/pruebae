package com.example.pruebaexm.user.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pruebaexm.R
import com.example.pruebaexm.databinding.ActivityMainBinding
import com.example.pruebaexm.user.fragment.RegisterListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var vBind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBind.root)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().add(vBind.fragmentContainer.id,RegisterListFragment()).commit()
    }

}