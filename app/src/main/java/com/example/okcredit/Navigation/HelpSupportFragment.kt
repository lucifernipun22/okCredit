package com.example.okcredit.Navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.helpSupport.HelpSupportViewModel
import com.example.okcredit.R

class HelpSupportFragment : Fragment() {

    private lateinit var helpSupportViewModel: HelpSupportViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*helpSupportViewModel =
            ViewModelProvider(this).get(HelpSupportViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fagment_helpsupport, container, false)
        val textView: TextView = root.findViewById(R.id.tvdemo)
        helpSupportViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}