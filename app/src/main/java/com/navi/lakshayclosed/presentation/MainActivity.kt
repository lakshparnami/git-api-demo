package com.navi.lakshayclosed.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.navi.lakshayclosed.R
import com.navi.lakshayclosed.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(PagingLibraryFragment::class.java)
        binding.buttonCustomPaging.setOnClickListener {
            setFragment(CustomPagingFragment::class.java)
        }
        binding.buttonPagingLibrary.setOnClickListener {
            setFragment(PagingLibraryFragment::class.java)
        }

    }

    private fun <T : Fragment> setFragment(fragmentClass: Class<T>) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragmentClass, null)
            .commit()
    }

}



















