package com.clakestudio.pc.dafttapchallange.ui.records

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

import com.clakestudio.pc.dafttapchallange.R
import com.clakestudio.pc.dafttapchallange.ViewModelFactory
import kotlinx.android.synthetic.main.records_fragment.*

class RecordsFragment : Fragment() {

    companion object {
        fun newInstance() = RecordsFragment()
    }

    private lateinit var viewModel: RecordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.records_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application)).get(RecordsViewModel::class.java)

        button_play.setOnClickListener {
            findNavController().navigate(R.id.action_recordsFragment_to_gameFragment)
        }

        // TODO: Use the ViewModel
    }

}
