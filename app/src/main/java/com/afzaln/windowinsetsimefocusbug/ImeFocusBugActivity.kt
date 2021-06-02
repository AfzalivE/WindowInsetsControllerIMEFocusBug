package com.afzaln.windowinsetsimefocusbug

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.contains
import com.afzaln.windowinsetsimefocusbug.databinding.ActivityImeFocusBugBinding

class ImeFocusBugActivity : AppCompatActivity() {

    private var withShowSoftInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityImeFocusBugBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editText = binding.editText

        binding.removeView.setOnClickListener { binding.root.removeView(editText) }
        binding.withShowSoftInput.setOnCheckedChangeListener { _, isChecked ->
            withShowSoftInput = isChecked
        }
        binding.showImeButton.setOnClickListener {
            if (!binding.root.contains(editText)) {
                binding.root.addView(editText)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                showKeyboard(editText)
            }
        }
    }

    @RequiresApi(30)
    private fun showKeyboard(editText: EditText) {
        if (withShowSoftInput) {
            editText.requestFocus()
            getSystemService<InputMethodManager>()?.showSoftInput(editText, 0)
        }
        val insetController = editText.windowInsetsController
        insetController?.show(WindowInsets.Type.ime())
    }
}
