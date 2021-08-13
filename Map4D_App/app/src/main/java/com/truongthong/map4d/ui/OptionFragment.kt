package com.truongthong.map4d.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.transition.MaterialFadeThrough
import com.truongthong.map4d.R

class OptionFragment : Fragment() {

    companion object{
        fun newInstance() = OptionFragment()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_option, container, false)

        enterTransition = MaterialFadeThrough()


        return view
    }


}