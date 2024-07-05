package ru.itis.kazanda.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentDetailScreenBinding

class DetailScreenFragment : Fragment(R.layout.fragment_detail_screen) {
    private lateinit var binding: FragmentDetailScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailScreenBinding.bind(view)

        val placeId = arguments?.getInt("placeId") ?: return
        val place = PlaceRepository.places.find { it.id == placeId } ?: return

        binding.placeName.text = place.name
        binding.placePayment.text = when (place.payment) {
            0 -> "free"
            1 -> "$"
            2 -> "$$"
            else -> "$$$"
        }
        binding.placeAddress.text = place.address
        binding.placeHours.text = place.hours
        binding.placeDescription.text = place.description
        setupImageCarousel()
        binding.toggleDescriptionIcon.setOnClickListener {
            if (binding.placeDescription.visibility == View.GONE) {
                binding.placeDescription.visibility = View.VISIBLE
                binding.toggleDescriptionIcon.setImageResource(R.drawable.baseline_expand_less_24)
            } else {
                binding.placeDescription.visibility = View.GONE
                binding.toggleDescriptionIcon.setImageResource(R.drawable.baseline_expand_more_24)
            }
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun setupImageCarousel() {
        val placeId = arguments?.getInt("placeId") ?: return
        val place = PlaceRepository.places.find { it.id == placeId } ?: return
        val images = place.imageUrls.split("\n").filter { it.isNotBlank() }

        binding.photoViewPager.adapter = ImageSliderAdapter(images)
        binding.photoViewPager.offscreenPageLimit = 1
        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(resources.getDimensionPixelSize(R.dimen.viewpager_margin)))
            addTransformer { page, position ->
                val r: Float = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
                page.alpha = 0.25f + r * 0.75f
            }
        }
        binding.photoViewPager.setPageTransformer(compositePageTransformer)
        binding.photoViewPager.setCurrentItem(Integer.MAX_VALUE / 2, false)
    }

    /*    private fun setupImageCarousel() {
            val placeId = arguments?.getInt("placeId") ?: return
            val place = PlaceRepository.places.find { it.id == placeId } ?: return
            val images = place.imageUrls.split("\n").filter { it.isNotBlank() }

            binding.photoViewPager.adapter = ImageSliderAdapter(images)
            binding.photoViewPager.offscreenPageLimit = 1
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.viewpager_margin)
            val screenWidth = resources.displayMetrics.widthPixels

            val imageViewWidth = (screenWidth * 0.75).toInt()
            val imageViewHeight = (imageViewWidth * 3 / 4)

            binding.photoViewPager.setPadding((screenWidth - imageViewWidth) / 2, 0, (screenWidth - imageViewWidth) / 2, 0)
            binding.photoViewPager.clipToPadding = false
            binding.photoViewPager.clipChildren = false

            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(pageMarginPx))
                addTransformer { page, position ->
                    val r: Float = 1 - abs(position)
                    page.scaleY = 0.6f + r * 0.4f
                    page.translationX = -(imageViewWidth / 2) * position
                    page.alpha = 0.25f + r * 0.75f
                    page.layoutParams.width = imageViewWidth
                    page.layoutParams.height = imageViewHeight
                    page.requestLayout()
                }
            }
            binding.photoViewPager.setPageTransformer(compositePageTransformer)
            binding.photoViewPager.setCurrentItem(Integer.MAX_VALUE / 2, false)
        }
        private fun setupImageCarousel() {
            val placeId = arguments?.getInt("placeId") ?: return
            val place = PlaceRepository.places.find { it.id == placeId } ?: return
            val images = place.imageUrls.split("\n").filter { it.isNotBlank() }

            binding.photoViewPager.adapter = ImageSliderAdapter(images)
            binding.photoViewPager.offscreenPageLimit = 1
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.viewpager_margin)
            val pageWidth = binding.photoViewPager.width
            val screenWidth = resources.displayMetrics.widthPixels

            binding.photoViewPager.setPadding(screenWidth / 3, 0, screenWidth / 3, 0)
            binding.photoViewPager.clipToPadding = false
            binding.photoViewPager.clipChildren = false

            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(pageMarginPx))
                addTransformer { page, position ->
                    val r: Float = 1 - Math.abs(position)
                    page.scaleY = 0.6f + r * 0.4f // Уменьшенный масштаб для текущего фото
                    page.translationX = -pageWidth / 2 * position
                    page.alpha = 0.25f + r * 0.75f
                }
            }
            binding.photoViewPager.setPageTransformer(compositePageTransformer)
            binding.photoViewPager.setCurrentItem(Integer.MAX_VALUE / 2, false)
        }*/
    /*    private fun setupImageCarousel() {
            val placeId = arguments?.getInt("placeId") ?: return
            val place = PlaceRepository.places.find { it.id == placeId } ?: return
            val images = place.imageUrls.split("\n").filter { it.isNotBlank() }

            binding.photoViewPager.adapter = ImageSliderAdapter(images)
            binding.photoViewPager.offscreenPageLimit = 1
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.viewpager_margin)
            val screenWidth = resources.displayMetrics.widthPixels

            val imageViewWidth = (screenWidth * 0.5).toInt()
            val imageViewHeight = (imageViewWidth * 3 / 4)

            binding.photoViewPager.setPadding((screenWidth - imageViewWidth) / 2, 0, (screenWidth - imageViewWidth) / 2, 0)
            binding.photoViewPager.clipToPadding = false
            binding.photoViewPager.clipChildren = false

            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(pageMarginPx))
                addTransformer { page, position ->
                    val r: Float = 1 - abs(position)
                    page.scaleY = 0.6f + r * 0.4f
                    page.translationX = -(imageViewWidth / 2) * position
                    page.alpha = 0.25f + r * 0.75f
                    page.layoutParams.width = imageViewWidth
                    page.layoutParams.height = imageViewHeight
                    page.requestLayout()
                    if (position != 0f) {
                        page.translationX += position * pageMarginPx
                    }
                }
            }
            binding.photoViewPager.setPageTransformer(compositePageTransformer)
            binding.photoViewPager.setCurrentItem(Integer.MAX_VALUE / 2, false)
        }*/

}