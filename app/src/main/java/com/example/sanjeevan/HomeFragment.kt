package com.example.sanjeevan

import ImageSliderAdapter
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var textIntroduction: TextView
    private lateinit var textMission: TextView
    private lateinit var textVision: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private val slideInterval = 7000L // 7 seconds

    // Dummy images for slider (replace with actual URLs or resources)
    private val images = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        textIntroduction = view.findViewById(R.id.textIntroduction)
        textMission = view.findViewById(R.id.textMission)
        textVision = view.findViewById(R.id.textVision)

        // Set up ViewPager adapter
        viewPager.adapter = ImageSliderAdapter(requireContext(), images)

        // Set introduction text with color for numbers
        val introText = """
            Navsanjeevan Social Trust is a right-based and behavior-change organization solving complex societal problems in India. 
            It operates in 51 villages, 10 schools, and engages with 4136 community members and 665 students. 
            The trust focuses on community empowerment, skill development, and enhancing education systems.
        """.trimIndent()

        val spannableString = SpannableString(introText)
        val violetColor = ContextCompat.getColor(requireContext(), R.color.violet)

        // Apply color to numbers
        val numberPattern = Regex("\\b\\d+\\b")
        val matcher = numberPattern.findAll(introText)

        for (match in matcher) {
            spannableString.setSpan(
                ForegroundColorSpan(violetColor),
                match.range.first,
                match.range.last + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                match.range.first,
                match.range.last + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        textIntroduction.text = spannableString
        val boldSpan = StyleSpan(Typeface.BOLD)

        val regex = "\\d+".toRegex()
        val missionText = """
            MISSION
            Navsanjeevan Social Trust strives to build an equitable and egalitarian society. We aim to alleviate poverty by empowering the next generation with Skill Education. We work on diminishing the skill gap that exists in the present world, by making youth market-ready. Navsanjeevan aims to build a healthy, vibrant, and dynamic society by empowering youth through skill education and providing sustainable employment/self-employment relevant to the market needs.
        """.trimIndent()

        val spannableStringMission = SpannableString(missionText)
        regex.findAll(missionText).forEach { matchResult ->
            spannableStringMission.setSpan(boldSpan, matchResult.range.first, matchResult.range.last + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        textMission.text = spannableStringMission

        val visionText = """
            VISION
            Navsanjeevan Social Trust has endeavored on a journey to erase the class differences that exist in the present world. For this, we have created a platform for the marginalized youth, women, and farmers, aiming to educate, inform, and inspire them to contribute actively to the formation of an egalitarian and enlightened society.
        """.trimIndent()

        val spannableStringVision = SpannableString(visionText)
        regex.findAll(visionText).forEach { matchResult ->
            spannableStringVision.setSpan(boldSpan, matchResult.range.first, matchResult.range.last + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        textVision.text = spannableStringVision
//        val title: TextView = view.findViewById(R.id.title)
//        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
//        title.startAnimation(fadeInAnimation)

        // Set custom page transformer
//        viewPager.setPageTransformer(SlowPageTransformer())

        // Start auto-slide
//        startAutoSlide()

        return view
    }

    private val slideRunnable = object : Runnable {
        override fun run() {
            if (viewPager.adapter?.itemCount == currentPage) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
            handler.postDelayed(this, slideInterval)
        }
    }

    private fun startAutoSlide() {
        handler.postDelayed(slideRunnable, slideInterval)
    }

    private fun stopAutoSlide() {
        handler.removeCallbacks(slideRunnable)
    }

    override fun onPause() {
        super.onPause()
        stopAutoSlide()
    }

    override fun onResume() {
        super.onResume()
        startAutoSlide()
    }

    inner class SlowPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            page.apply {
                val pageWidth = width
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Fade the page out.
                        alpha = 1 - Math.abs(position)

                        // Counteract the default slide transition
                        translationX = pageWidth * -position

                        // Scale the page down (between MIN_SCALE and 1)
                        val scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }
}
