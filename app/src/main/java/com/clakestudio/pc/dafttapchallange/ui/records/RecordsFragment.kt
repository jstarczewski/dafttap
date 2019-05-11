package com.clakestudio.pc.dafttapchallange.ui.records

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.clakestudio.pc.dafttapchallange.R
import com.clakestudio.pc.dafttapchallange.ViewModelFactory
import com.clakestudio.pc.dafttapchallange.adapters.ScoresAdapter
import com.clakestudio.pc.dafttapchallange.data.Score
import kotlinx.android.synthetic.main.game_fragment.*
import kotlinx.android.synthetic.main.records_fragment.*

class RecordsFragment : Fragment() {

    private lateinit var viewModel: RecordsViewModel
    private val adapter: ScoresAdapter = ScoresAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.records_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(RecordsViewModel::class.java).apply {
                scores.observe(viewLifecycleOwner, Observer {
                    adapter.replaceData(it)
                    if (!it.isNullOrEmpty())
                        showRecords()
                })
            }.apply {
                init()
            }

        setUpRecyclerView()
        button_play.setOnClickListener {
            animateOut()
        }
        if (adapter.itemCount == 0)
            hideRecords()
    }

    private fun hideRecords() {
        text_view_records_info.visibility = View.INVISIBLE
    }

    private fun showRecords() {
        text_view_records_info.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        recycler_view_top.apply {
            layoutManager = LinearLayoutManager(this@RecordsFragment.context)
            setHasFixedSize(true)
            adapter = this@RecordsFragment.adapter
        }
    }

    private fun animateOutRecyclerView() {
        recycler_view_top.animate()
            .alpha(0.0F)
            .apply {
                duration = 100L
            }
            .withEndAction {
                navigate()
            }.start()
    }

    private fun animateOutGameTitle() {
        text_view_game_name.animate()
            .alpha(0.0F)
            .apply {
                duration = 100L
            }
            .start()
    }

    private fun animateOutPlayButton() {
        button_play.animate()
            .alpha(0.0F)
            .apply {
                duration = 100L
            }
            .start()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun animateOut() {
        animateOutRecyclerView()
        animateOutGameTitle()
    }

    /*
    private fun animateIn() {
        animateInRecylerView()
        animateInGameTitle()
    }

    private fun animateInRecylerView() {
        recycler_view_top.animate()
            .alpha(1.0F)
            .apply {
                duration = 1000L
            }
            .start()
    }

    private fun animateInGameTitle() {
        text_view_game_name.animate()
            .alpha(1.0F)
            .apply {
                duration = 1000L
            }
            .start()
    }*/

    private fun navigate() {
        val action = RecordsFragmentDirections.actionRecordsFragmentToGameFragment()
        action.min = viewModel.min.value ?: 0
        findNavController().navigate(action)

    }


}
