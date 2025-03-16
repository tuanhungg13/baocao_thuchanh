package com.dev.csesport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CyclingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CyclingFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cycling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rcv_cycling)

        val runs = listOf(
            RunItem("15km", "22:04", "12/3/2025"),
            RunItem("8km", "22:04", "12/2/2025"),
            RunItem("5km", "22:04", "10/2/2025"),
            RunItem("Cycle", "22:04", "09/3/2025")
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RunningAdapter(runs)
    }
}