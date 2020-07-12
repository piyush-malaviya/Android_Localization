package com.pcm.localization.extension

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.show(fragmentManager: FragmentManager) {
    fragmentManager.beginTransaction().add(this, tag).commitAllowingStateLoss()
}