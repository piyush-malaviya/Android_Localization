package com.pcm.localization.listener

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

interface OnDialogItemClickListener {
    fun onDialogItemClick(dialog: DialogFragment, view: View, data: Bundle?)
}