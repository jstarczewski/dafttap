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
import androidx.recyclerview.widget.LinearLayoutManager

import com.clakestudio.pc.dafttapchallange.R
import com.clakestudio.pc.dafttapchallange.ViewModelFactory
import com.clakestudio.pc.dafttapchallange.adapters.ScoresAdapter
import com.clakestudio.pc.dafttapchallange.data.Score
import kotlinx.android.synthetic.main.records_fragment.*

class RecordsFragment : Fragment() {

    companion object {
        fun newInstance() = RecordsFragment()
    }

    private lateinit var viewModel: RecordsViewModel
    val scores = arrayListOf<Score>(
        Score(2, "e"),
        Score(2, "e"),
        Score(2, "e"),
        Score(2, "e"),
        Score(2, "e")
    )
    private val adapter: ScoresAdapter = ScoresAdapter(scores)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.records_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(RecordsViewModel::class.java)

        button_play.setOnClickListener {
            findNavController().navigate(R.id.action_recordsFragment_to_gameFragment)
        }
        setUpRecyclerView()

        // TODO: Use the ViewModel
    }

    fun setUpRecyclerView() {
        recycler_view_top.apply {
            layoutManager = LinearLayoutManager(this@RecordsFragment.context)
            setHasFixedSize(true)
            adapter = this@RecordsFragment.adapter
        }
    }

}
