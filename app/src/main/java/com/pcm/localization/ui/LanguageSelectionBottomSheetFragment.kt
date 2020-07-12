package com.pcm.localization.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pcm.localization.R
import com.pcm.localization.listener.OnDialogItemClickListener
import com.pcm.localization.utils.Constant
import com.pcm.localization.utils.LocaleManager
import kotlinx.android.synthetic.main.fragment_language_selection_bottom_sheet.*

class LanguageSelectionBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var onDialogItemClickListener: OnDialogItemClickListener? = null

    companion object {
        fun newInstance(): LanguageSelectionBottomSheetFragment {
            return LanguageSelectionBottomSheetFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language_selection_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        tvEnglish.setOnClickListener(this)
        tvHindi.setOnClickListener(this)

        setSelectedLanguage()
    }


    fun setOnDialogItemClickListener(listener: OnDialogItemClickListener): LanguageSelectionBottomSheetFragment {
        onDialogItemClickListener = listener
        return this
    }

    override fun onClick(v: View?) {
        val preLanguage = LocaleManager.getInstance().language
        when (v?.id) {
            R.id.tvEnglish -> {
                LocaleManager.getInstance().language = "en"
            }
            R.id.tvHindi -> {
                LocaleManager.getInstance().language = "hi"
            }
        }
        setSelectedLanguage()
        val data = Bundle()
        data.putBoolean(
            Constant.IS_LANGUAGE_CHANGED,
            preLanguage != LocaleManager.getInstance().language
        )
        onDialogItemClickListener?.onDialogItemClick(this, v!!, data)
        dismissAllowingStateLoss()
    }

    private fun setSelectedLanguage() {
        when (LocaleManager.getInstance().language) {
            "en" -> {
                tvEnglish.isSelected = true
                tvHindi.isSelected = false
            }
            "hi" -> {
                tvHindi.isSelected = true
                tvEnglish.isSelected = false
            }
        }
    }

}
