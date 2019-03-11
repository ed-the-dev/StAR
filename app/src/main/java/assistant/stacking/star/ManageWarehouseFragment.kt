package assistant.stacking.star

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assistant.stacking.star.notifications.fragment_notifications
import kotlinx.android.synthetic.main.fragment_manage_warehouse.view.*


class ManageWarehouseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_manage_warehouse, container, false)
        view.button_notifications.setOnClickListener{
            val intent = Intent(context, fragment_notifications::class.java)

            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }


}