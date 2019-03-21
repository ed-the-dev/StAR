package assistant.stacking.star

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_storage.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class Storage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
       /* editText_1.setText(list.get(1)).toString()
        editText_2.setText(list.get(2)).toString()
        editText_3.setText(list.get(3)).toString()
        editText_4.setText(list.get(4)).toString()
        editText_5.setText(list.get(5)).toString()
        editText_6.setText(list.get(6)).toString()*/

        button_confirm_storage.setOnClickListener{
            if (list.distinct().size<6){
                Toast.makeText(this,"No more than one parcel can be at each shelve",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            list[1]=editText_1.text.toString().toInt()
            list[2]=editText_2.text.toString().toInt()
            list[3]=editText_3.text.toString().toInt()
            list[4]=editText_4.text.toString().toInt()
            list[5]=editText_5.text.toString().toInt()
            list[6]=editText_6.text.toString().toInt()

            val intent = Intent(this, Storage::class.java)

            startActivity(intent)
        }
    }


}
