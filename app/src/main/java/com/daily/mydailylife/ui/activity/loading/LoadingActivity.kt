package com.daily.mydailylife.ui.activity.loading

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.daily.mydailylife.databinding.ActivityLoadingBinding
import com.daily.mydailylife.ui.activity.BaseActivity
import com.daily.mydailylife.ui.activity.main.MainActivity
import com.daily.mydailylife.ui.util.NoneViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadingActivity: BaseActivity<ActivityLoadingBinding, NoneViewModel>(
    ActivityLoadingBinding::inflate,
    NoneViewModel::class
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(1000)
            startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
            finish()
        }
    }

}