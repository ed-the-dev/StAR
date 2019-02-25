package assistant.stacking.star

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox


class CheckBoxes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_boxes)
        var confirmButton = findViewById<Button>(R.id.button_confirm)
        confirmButton.setOnClickListener {
            parcels?.sort()
            var intent=Intent(this,Reorder::class.java)

            startActivity(intent)
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox01 -> {
                    if (checked) {
                        parcels?.add("P01")
                    } else {
                        parcels?.remove("P01")
                    }
                }

                R.id.checkbox02 -> {
                    if (checked) {
                        parcels?.add("P02")
                    } else {
                        parcels?.remove("P02")
                    }
                }
                R.id.checkbox03 -> {
                    if (checked) {
                        parcels?.add("P03")
                    } else {
                        parcels?.remove("P03")
                    }
                }
                R.id.checkbox04 -> {
                    if (checked) {
                        parcels?.add("P04")
                    } else {
                        parcels?.remove("P04")
                    }
                }

                R.id.checkbox05 -> {
                    if (checked) {
                        parcels?.add("P05")
                    } else {
                        parcels?.remove("P05")
                    }
                }
                R.id.checkbox06 -> {
                    if (checked) {
                        parcels?.add("P06")
                    } else {
                        parcels?.remove("P06")
                    }
                }
                R.id.checkbox07 -> {
                    if (checked) {
                        parcels?.add("P07")
                    } else {
                        parcels?.remove("P07")
                    }
                }
                R.id.checkbox08 -> {
                    if (checked) {
                        parcels?.add("P08")
                    } else {
                        parcels?.remove("P08")
                    }
                }
            }
        }

    }
}