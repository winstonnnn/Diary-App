package com.daily.mydailylife.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.daily.mydailylife.ui.extension.transparentStatusBar
import kotlin.reflect.KClass

abstract class BaseActivity<VB: ViewBinding, VM: ViewModel>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
    model: KClass<out VM>
): AppCompatActivity() {

    val viewModel by ViewModelLazy(model, {viewModelStore}, {defaultViewModelProviderFactory})
    val binding by lazy { bindingInflater(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContentView(binding.root)
    }
}