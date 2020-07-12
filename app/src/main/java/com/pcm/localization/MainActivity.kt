package com.pcm.localization

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.DialogFragment
import com.pcm.localization.base.BaseActivity
import com.pcm.localization.extension.show
import com.pcm.localization.listener.OnDialogItemClickListener
import com.pcm.localization.ui.LanguageSelectionBottomSheetFragment
import com.pcm.localization.utils.Constant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var isLanguageChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        if (savedInstanceState != null) {
            isLanguageChange = savedInstanceState.getBoolean(Constant.IS_LANGUAGE_CHANGED, false)
        }
    }

    private fun initView() {
        tvDummyText.text = getString(R.string.dummy_text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_language, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionLanguage) {
            openLanguageSelectionDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(Constant.IS_LANGUAGE_CHANGED, isLanguageChange)
        super.onSaveInstanceState(outState)
    }

    private fun openLanguageSelectionDialog() {
        LanguageSelectionBottomSheetFragment.newInstance()
            .setOnDialogItemClickListener(object : OnDialogItemClickListener {
                override fun onDialogItemClick(dialog: DialogFragment, view: View, data: Bundle?) {

                    // if language selection change then update UI
                    if (data != null && data.getBoolean(Constant.IS_LANGUAGE_CHANGED, false)) {
                        //tvLanguage.text = getSelectedLanguage()
                        // set isLanguageChange flag to true and save to activity savedInstanceState
                        // because when recreate method called all the current state is cleared
                        // and when user back press set activity result with isLanguage change,
                        // so parent activity can also recreate and update ui
                        isLanguageChange = true
                        recreate()
                    }
                }
            })
            .show(supportFragmentManager)
    }

}