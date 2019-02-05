package assistant.stacking.star

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox

var parcels:ArrayList<String>? = arrayListOf("parcel 1","parcel 2","parcel 3","parcel 4","parcel 5","parcel 6","parcel 7","parcel 8")
class CheckBoxes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_boxes)
        var confirmButton = findViewById<Button>(R.id.button_confirm)
        confirmButton.setOnClickListener {
            parcels?.sort()
            var intent=Intent(this,Reorder::class.java)
            intent.putExtra("parcelsList", parcels)
            startActivity(intent)
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox1 -> {
                    if (checked) {
                        parcels?.add("parcel 1")
                    } else {
                        parcels?.remove("parcel 1")
                    }
                }
                R.id.checkbox1 -> {
                    if (checked) {
                        parcels?.add("parcel 1")
                    } else {
                        parcels?.remove("parcel 1")
                    }
                }
                R.id.checkbox2 -> {
                    if (checked) {
                        parcels?.add("parcel 2")
                    } else {
                        parcels?.remove("parcel 2")
                    }
                }
                R.id.checkbox3 -> {
                    if (checked) {
                        parcels?.add("parcel 3")
                    } else {
                        parcels?.remove("parcel 3")
                    }
                }
                R.id.checkbox4 -> {
                    if (checked) {
                        parcels?.add("parcel 4")
                    } else {
                        parcels?.remove("parcel 4")
                    }
                }
                R.id.checkbox4 -> {
                    if (checked) {
                        parcels?.add("parcel 4")
                    } else {
                        parcels?.remove("parcel 4")
                    }
                }
                R.id.checkbox5 -> {
                    if (checked) {
                        parcels?.add("parcel 5")
                    } else {
                        parcels?.remove("parcel 5")
                    }
                }
                R.id.checkbox6 -> {
                    if (checked) {
                        parcels?.add("parcel 6")
                    } else {
                        parcels?.remove("parcel 6")
                    }
                }
                R.id.checkbox7 -> {
                    if (checked) {
                        parcels?.add("parcel 7")
                    } else {
                        parcels?.remove("parcel 7")
                    }
                }
                R.id.checkbox8 -> {
                    if (checked) {
                        parcels?.add("parcel 8")
                    } else {
                        parcels?.remove("parcel 8")
                    }
                }
            }
        }

    }
}