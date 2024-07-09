package ru.itis.kazanda.fragments.map

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.ProgressDialogViewBinding

@RequiresApi(Build.VERSION_CODES.R)
class CustomProgressDialog(context: Context) {

    private var binding: ProgressDialogViewBinding? = null
    private lateinit var dialog: CustomDialog

    fun start(title: String = "") {
        binding?.run {
            pbTitle.text = title
        }
        dialog.show()
    }

    fun stop() {
        dialog.dismiss()
    }

    init {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.progress_dialog_view, null)
        binding = ProgressDialogViewBinding.bind(view)

        binding?.run {
            cvLoading.setCardBackgroundColor(Color.parseColor("#80919191"))

            setColorFilter(
                pbLoading.indeterminateDrawable,
                ResourcesCompat.getColor(context.resources, com.google.android.material.R.color.design_default_color_primary, null)
            )

            pbTitle.setTextColor(Color.WHITE)

            dialog = CustomDialog(context)
            dialog.setContentView(view)
        }
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    class CustomDialog(context: Context): Dialog(context, R.style.CustomDialogTheme) {
        init {
            window?.decorView?.setOnApplyWindowInsetsListener { v, insets ->
                WindowInsets.CONSUMED
            }
        }
    }

}