package com.clakestudio.pc.dafttapchallange.ui.game

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.clakestudio.pc.dafttapchallange.R
import com.clakestudio.pc.dafttapchallange.util.CountDownTimer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.game_fragment.*
import java.util.concurrent.TimeUnit


class GameFragment : Fragment() {


    var taps = 0
    val arraylsit = ArrayList<String>().apply {
        val s = "3, 2,1, Play, 5, 4, 3, 2, 1".split(",")
        addAll(s)
    }

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
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java).apply {
            time.observe(viewLifecycleOwner, Observer {
                text_view_time.text = it
            })
            taps.observe(viewLifecycleOwner, Observer {
                text_view_county.text = it.toString()
            })
            play.observe(viewLifecycleOwner, Observer {
                text_view_play.text = it
            })
            dialog.observe(viewLifecycleOwner, Observer {
                showAlertDialog(it)
            })
        }
        constrain_layout_game.setOnClickListener {
            viewModel.incrementTapsNumber()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.init()
    }

    fun showAlertDialog(message: String) {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setMessage(message)
        builder?.setTitle("Game ened")
        builder?.setCancelable(false)
        builder?.apply {
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                findNavController().navigate(R.id.action_gameEndFragment_to_recordsFragment)
            })
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()


    }


    fun a() =
        Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(arraylsit.size.toLong())
            .map { arraylsit[it.toInt()] }
            .subscribe {
                text_view_time.text = it
            }


}