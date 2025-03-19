package com.bignerdranch.playlistmaker.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.playlistmaker.R
import com.bignerdranch.playlistmaker.domain.api.NavigateBackUseCase
import com.bignerdranch.playlistmaker.domain.api.OpenUserAgreementUseCase
import com.bignerdranch.playlistmaker.domain.api.SendSupportEmailUseCase
import com.bignerdranch.playlistmaker.domain.api.ShareAppUseCase
import com.bignerdranch.playlistmaker.domain.impl.NavigateBackUseCaseImpl
import com.bignerdranch.playlistmaker.domain.impl.OpenUserAgreementUseCaseImp
import com.bignerdranch.playlistmaker.domain.impl.SendSupportEmailUseCaseImp
import com.bignerdranch.playlistmaker.domain.impl.ShareAppUseCaseImpl
import com.bignerdranch.playlistmaker.domain.theme.ThemeUseCase
import com.bignerdranch.playlistmaker.presentation.App
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView

class SettingsActivity : AppCompatActivity() {

    private lateinit var buttonArrowBack: ImageView
    private lateinit var switch: SwitchMaterial
    private lateinit var share: MaterialTextView
    private lateinit var sendToSupport: MaterialTextView
    private lateinit var userAgreement: MaterialTextView

    private lateinit var themeUseCase: ThemeUseCase
    private lateinit var navigateBackUseCase: NavigateBackUseCase
    private lateinit var shareAppUseCase: ShareAppUseCase
    private lateinit var sendSupportEmailUseCase: SendSupportEmailUseCase
    private lateinit var openUserAgreementUseCase: OpenUserAgreementUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        buttonArrowBack = findViewById(R.id.arrow_back)
        switch = findViewById(R.id.switch_compat)
        share = findViewById(R.id.share)
        sendToSupport = findViewById(R.id.support)
        userAgreement = findViewById(R.id.user_agreement)



        // Получаем instance ThemeUseCase
        themeUseCase = (applicationContext as App).themeUseCase
        // Синхронизация переключателя с текущей темой
        switch.isChecked = themeUseCase.getTheme()


        navigateBackUseCase = NavigateBackUseCaseImpl(this)
        shareAppUseCase = ShareAppUseCaseImpl(this)
        sendSupportEmailUseCase = SendSupportEmailUseCaseImp(this)
        openUserAgreementUseCase = OpenUserAgreementUseCaseImp(this)


        buttonArrowBack.setOnClickListener {
            navigateBackUseCase.navigateBack()
        }


        share.setOnClickListener {
            val url = getString(R.string.share_url)
            shareAppUseCase.share(url)
        }


        sendToSupport.setOnClickListener {
            val email = getString(R.string.sendToSupport_email)
            val theme = getString(R.string.sendToSupport_theme)
            val message = getString(R.string.sendToSupport_text)

            sendSupportEmailUseCase.sendSupportEmail(
                email,
                theme,
                message
            )
        }


        userAgreement.setOnClickListener {
            val url = getString(R.string.userAgreement_url)
            openUserAgreementUseCase.open(url)
        }

        switch.setOnCheckedChangeListener { _, checked ->
            themeUseCase.switchTheme(checked)
        }

    }
}