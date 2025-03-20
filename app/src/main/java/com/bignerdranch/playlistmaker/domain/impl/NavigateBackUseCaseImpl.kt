package com.bignerdranch.playlistmaker.domain.impl

import android.app.Activity
import com.bignerdranch.playlistmaker.domain.api.NavigateBackUseCase

class NavigateBackUseCaseImpl(private val activity: Activity): NavigateBackUseCase {
    override fun navigateBack() {
        activity.finish()
    }
}