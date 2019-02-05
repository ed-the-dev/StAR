package assistant.stacking.star

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_van_layout.view.*
import android.R
import android.support.design.widget.FloatingActionButton
import android.widget.Button


class VanLayout : Fragment() {

    // var fab = findViewById(R.id.button) as FloatingActionButton

    // var btnconnect = R.id.btnconnect as Button

    //private lateinit var btnconnect: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        ///*

        val view: View = inflater.inflate(assistant.stacking.star.R.layout.fragment_van_layout, container, false)

        // btnconnect = findViewById(assistant.stacking.star.R.id.btnconn) as Button

        // btnconnect = findViewById(assistant.stacking.star.R.id.btnconn) as Button



        view.btnconn.setOnClickListener { view ->
            val intent = Intent(context, Connection::class.java);
            startActivity(intent);
        }
        return view



        //return inflater.inflate(assistant.stacking.star.R.layout.fragment_van_layout, container, false)
    }


}