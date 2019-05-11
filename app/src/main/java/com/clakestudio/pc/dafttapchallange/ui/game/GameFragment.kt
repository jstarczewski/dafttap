package com.clakestudio.pc.dafttapchallange.ui.game

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.clakestudio.pc.dafttapchallange.R
import com.clakestudio.pc.dafttapchallange.ViewModelFactory
import com.clakestudio.pc.dafttapchallange.util.CountDownTimer
import kotlinx.android.synthetic.main.game_fragment.*


class GameFragment : Fragment() {


    var taps = 0
    val arraylsit = ArrayList<String>().apply {
        val s = "3, 2,1, Play, 5, 4, 3, 2, 1".split(",")
        addAll(s)
    }

    lateinit var countDownTimer: android.os.CountDownTimer

    private val countedCompleter = CountDownTimer(8, 1, arraylsit, { text_view_time.text = it })

    companion object {
        fun newInstance() = GameFragment()
    }

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(GameViewModel::class.java).apply {
                init()
                time.observe(viewLifecycleOwner, Observer {
                    text_view_time.text = it
                })
                taps.observe(viewLifecycleOwner, Observer {
                    text_view_count.text = it.toString()
                })
                play.observe(viewLifecycleOwner, Observer {
                    text_view_play.text = it
                })
                dialog.observe(viewLifecycleOwner, Observer {
                    showAlertDialog(it)
                })
                isRunning.observe(viewLifecycleOwner, Observer {
                    if (it) {
                        if (viewModel.remainingTime.value != null && viewModel.remainingTime.value != 0L)
                            prepareTimer()
                        countDownTimer.start()
                        hidePrepareCountdown()
                    } else {
                        countDownTimer.cancel()
                        showPrepareCountdown()
                    }
                })
            }
        initMinValueFromBundle()
        prepareTimer()
        constrain_layout_game.setOnClickListener {
            viewModel.incrementTapsNumber()
        }
    }

    fun initMinValueFromBundle() = arguments?.let {
        viewModel.min.value = GameFragmentArgs.fromBundle(it).min
    }


    override fun onResume() {
        super.onResume()
        viewModel.resumeGame()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseGame()
    }

    private fun showAlertDialog(message: String) {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setMessage(message)
        builder?.setTitle("Game ended")
        builder?.setCancelable(false)
        builder?.apply {
            setPositiveButton("OK") { _, _ ->
                findNavController().popBackStack()
            }
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }


    private fun prepareTimer() {
        countDownTimer = object : android.os.CountDownTimer(viewModel.remainingTime.value!!, 100) {
            override fun onFinish() {
                viewModel.endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                viewModel.setRemainingTime(millisUntilFinished)
            }

        }
    }

    private fun showPrepareCountdown() {
        text_view_play.animate()
            .alpha(1.0F).apply {
                duration = 1000L
            }.start()
    }

    private fun hidePrepareCountdown() {
        text_view_play.animate()
            .alpha(0.0F).apply {
                duration = 1000L
            }.start()
    }

}
