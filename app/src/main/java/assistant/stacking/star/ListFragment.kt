package assistant.stacking.star

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import kotlinx.android.synthetic.main.fragment_list.*

import kotlinx.android.synthetic.main.fragment_list.view.*
var parcels:MutableList<String>? = ArrayList()

class ListFragment : Fragment() {



    // on Nik's the initialisation of parcels is above class

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        var ch=ArrayList<View>()
        for (i in (0..1)){
            for (j in (0..9)){
                if (i==0 &&j==0){
                    continue
                }
                var n=i.toString()+j.toString()
                var ll=view.checkboxes_layout

                var v = ll.getChildAt(10*(i)+j-1)
                if (v is CheckBox){
                    ch.add(v)
                    var checkBoxText="P"+n
                    if (i==0){
                        checkBoxText+="    27/2/2019"
                    }
                    else{
                        checkBoxText+="    28/2/2019"
                    }
                    when (j.rem(3)){
                        0->{
                            checkBoxText+="    High"
                        }
                        1->{
                            checkBoxText+="    Medium"
                        }
                        2->{
                            checkBoxText+="    Low"
                        }
                    }
                    v.text=checkBoxText
                }


            }
        }
        view.search_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                var ll=view.checkboxes_layout
                when (s.toString()){
                    ""->{

                        for (i in(0..ch.size-1)){
                            ch.get(i).visibility= VISIBLE
                        }
                        view.button_done.visibility=GONE

                    }
                    else->{
                        for (i in(0..ll.childCount)) {
                            var v =ll.getChildAt(i)
                            if (v is CheckBox){
                                if (v.text.substring(0,s.toString().length)==s.toString()){
                                    v.visibility= VISIBLE
                                }else{
                                    v.visibility= GONE
                                }
                            }


                        }
                        view.button_done.visibility= VISIBLE

                    }

                }
            }
        })



        // var confirmButton = (R.id.button_confirm)
        view.button_confirm.setOnClickListener {
            parcels?.sort()
            // var intent=Intent(this,Reorder::class.java) // old stuff

            val intent = Intent(context,Reorder::class.java)
            //intent.putExtra("parcelsList", assistant.stacking.star.parcels)
            startActivity(intent)
        }
        view.button_done.setOnClickListener{
            view.search_text.setText("")
        }


        ch.forEach{

            if (it is CheckBox){

                it.setOnCheckedChangeListener{_,isChecked ->
                    System.out.println("mpenei")
                    if (isChecked) {
                        System.out.println(it.text.toString())
                        parcels?.add(it.text.toString())
                        System.out.println(parcels?.size)
                    } else {
                        parcels?.remove(it.text.toString())
                    }
                }
            }
        }
//        view.checkbox01.setOnCheckedChangeListener{buttonView, isChecked ->
//            if (isChecked) {
//                assistant.stacking.star.parcels?.add("P01")
//            } else {
//                assistant.stacking.star.parcels?.remove("P01")
//            }
//           // Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
//        }
//        view.checkbox02.setOnCheckedChangeListener{buttonView, isChecked ->
//            if (isChecked) {
//                assistant.stacking.star.parcels?.add("P02")
//            } else {
//                assistant.stacking.star.parcels?.remove("P02")
//            }
//          //  Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
//        }
//        view.checkbox03.setOnCheckedChangeListener{buttonView, isChecked ->
//            if (isChecked) {
//                assistant.stacking.star.parcels?.add("P03")
//            } else {
//                assistant.stacking.star.parcels?.remove("P03")
//            }
//           // Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
//        }
//        view.checkbox04.setOnCheckedChangeListener{buttonView, isChecked ->
//            if (isChecked) {
//                assistant.stacking.star.parcels?.add("P04")
//            } else {
//                assistant.stacking.star.parcels?.remove("P04")
//            }
//          //  Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
//        }
//        view.checkbox05.setOnCheckedChangeListener{buttonView, isChecked ->
//            if (isChecked) {
//                assistant.stacking.star.parcels?.add("P05")
//            } else {
//                assistant.stacking.star.parcels?.remove("P05")
//            }
//          //  Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
//        }
//        view.checkbox06.setOnCheckedChangeListener{buttonView, isChecked ->
//            if (isChecked) {
//                assistant.stacking.star.parcels?.add("P06")
//            } else {
//                assistant.stacking.star.parcels?.remove("P06")
//            }
//           // Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
//        }
//        view.checkbox07.setOnCheckedChangeListener{buttonView, isChecked ->
//            if (isChecked) {
//                assistant.stacking.star.parcels?.add("P07")
//            } else {
//                assistant.stacking.star.parcels?.remove("P07")
//            }
//           // Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
//        }
//        view.checkbox08.setOnCheckedChangeListener{buttonView, isChecked ->
//            if (isChecked) {
//                assistant.stacking.star.parcels?.add("P08")
//            } else {
//                assistant.stacking.star.parcels?.remove("P08")
//            }
//            //Toast.makeText(context, isChecked.toString(), Toast.LENGTH_SHORT).show()
//        }


        return view
    }




}