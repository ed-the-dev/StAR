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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(assistant.stacking.star.R.layout.fragment_van_layout, container, false)

        view.btnconn.setOnClickListener { view ->
            val intent = Intent(context, Connection::class.java);
            startActivity(intent);
        }
        return view

    }


}