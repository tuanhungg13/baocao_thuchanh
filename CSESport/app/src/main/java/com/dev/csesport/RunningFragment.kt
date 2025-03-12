package com.dev.csesport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RunningFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class RunningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_running, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rcv_running)

        val runs = listOf(
            RunItem("5km", "22:04", "29/11/2020"),
            RunItem("10km", "22:04", "29/11/2020"),
            RunItem("Half Marathon", "22:04", "29/11/2020"),
            RunItem("Marathon", "22:04", "29/11/2020")
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RunningAdapter(runs)
    }
}
