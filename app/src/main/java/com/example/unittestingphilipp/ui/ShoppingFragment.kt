package com.example.unittestingphilipp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unittestingphilipp.R

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * TODO
         *  This way for instantiation where i passed " requireActivity() "
         *  to bind the viewmodel to the activity not the fragment
         *  and this way if wanted to share the viewmodel through several fragments
         *  to make the viewmodel only die if the activity itself was died and
         *  survive even if the fragment is died
         */
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }
}