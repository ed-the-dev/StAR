package assistant.stacking.star

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.support.v7.app.AppCompatActivity

import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    var parcels:ArrayList<String>? = arrayListOf("parcel 1","parcel 2","parcel 3","parcel 4","parcel 5","parcel 6","parcel 7","parcel 8")

    // on Nik's the initialisation of parcels is above class

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        // var confirmButton = (R.id.button_confirm)
        view.button_confirm.setOnClickListener {
            assistant.stacking.star.parcels?.sort()
            // var intent=Intent(this,Reorder::class.java) // old stuff

            val intent = Intent(context,Reorder::class.java)
            intent.putExtra("parcelsList", assistant.stacking.star.parcels)
            startActivity(intent)
        }



        view.checkbox1.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                assistant.stacking.star.parcels?.add("parcel 1")
            } else {
                assistant.stacking.star.parcels?.remove("parcel 1")
            }
            Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
        }
        view.checkbox2.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                assistant.stacking.star.parcels?.add("parcel 2")
            } else {
                assistant.stacking.star.parcels?.remove("parcel 2")
            }
            Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
        }
        view.checkbox3.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                assistant.stacking.star.parcels?.add("parcel 3")
            } else {
                assistant.stacking.star.parcels?.remove("parcel 3")
            }
            Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
        }
        view.checkbox4.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                assistant.stacking.star.parcels?.add("parcel 4")
            } else {
                assistant.stacking.star.parcels?.remove("parcel 4")
            }
            Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
        }
        view.checkbox5.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                assistant.stacking.star.parcels?.add("parcel 5")
            } else {
                assistant.stacking.star.parcels?.remove("parcel 5")
            }
            Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
        }
        view.checkbox6.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                assistant.stacking.star.parcels?.add("parcel 6")
            } else {
                assistant.stacking.star.parcels?.remove("parcel 6")
            }
            Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
        }
        view.checkbox7.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                assistant.stacking.star.parcels?.add("parcel 7")
            } else {
                assistant.stacking.star.parcels?.remove("parcel 7")
            }
            Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
        }
        view.checkbox8.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked) {
                assistant.stacking.star.parcels?.add("parcel 8")
            } else {
                assistant.stacking.star.parcels?.remove("parcel 8")
            }
            Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
        }


        return view
    }




}