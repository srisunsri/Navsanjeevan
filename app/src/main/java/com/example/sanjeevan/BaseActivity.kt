package com.example.sanjeevan

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseActivity : AppCompatActivity() {

    lateinit var headerTitle: TextView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                switchFragment(HomeFragment())
//                setHeaderTitle("Home")
                true
            }
            R.id.action_donate -> {
                switchFragment(DonateFragment())
//                setHeaderTitle("Donate")
                true
            }
            R.id.action_about -> {
                switchFragment(AboutFragment())
//                setHeaderTitle("About")
                true
            }
            R.id.action_gallery -> {
                switchFragment(GalleryFragment())
//                setHeaderTitle("Gallery")
                true
            }
            R.id.action_contact -> {
                switchFragment(ContactFragment())
//                setHeaderTitle("Contact")
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
//        enableEdgeToEdge()

        // Initialize headerTitle before setting it
//        headerTitle = findViewById(R.id.title) // Ensure you have a TextView with this ID in your layout

        // Set the default fragment
        if (savedInstanceState == null) {
            switchFragment(HomeFragment())
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        Log.d("BaseActivity", "headerTitle initialized: ${::headerTitle.isInitialized}")

    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }

//    private fun setHeaderTitle(title: String) {
//        headerTitle.text = title
//    }
}
