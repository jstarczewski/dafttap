package com.clakestudio.pc.dafttapchallange.ui.game

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.clakestudio.pc.dafttapchallange.R

class GameFragment : Fragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        // TODO: Use the ViewModel
        showAlertDialog()
    }

    fun showAlertDialog() {

        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setMessage("Game ened")
        builder?.setTitle("Twoja stara")
        builder?.apply {
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                findNavController().navigate(R.id.action_gameEndFragment_to_recordsFragment)
            })
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()


    }

}
