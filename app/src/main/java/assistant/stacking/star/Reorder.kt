/**
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package assistant.stacking.star

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toolbar
import assistant.stacking.star.notifications.fragment_notifications
import assistant.stacking.star.notifications.notifications_string
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.connection.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.reorder.*
import java.util.*

private var builder:AlertDialog.Builder?=null
private var tuples :String=""
private var message:String=""
private var reverse:Boolean=true
private var _menu:Menu?=null
class Reorder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reorder)
        if (showHelp) {
            showHelp()
            showHelp=false
        }


        setSupportActionBar(my_toolbar)

        if (savedInstanceState == null) {
            showFragment(BoardFragment.newInstance())
        }

        //supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.app_color)))
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, "fragment").commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main_nik, menu)
        _menu=menu
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        //  boolean listFragment = getSupportFragmentManager().findFragmentByTag("fragment") instanceof ListFragment;
        /*   menu.findItem(R.id.action_lists).setVisible(!listFragment);*/
        // menu.findItem(R.id.action_board).setVisible(listFragment);

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.reverse_false ->{
                reverse=false
                reverse=true
                var reverse_true =_menu!!.findItem(R.id.reverse_true)
                reverse_true.isVisible=true
                var reverse_false = _menu!!.findItem(R.id.reverse_false)
                reverse_false.isVisible= false


            }

            R.id.reverse_true ->{
                reverse=true
                var reverse_true =_menu!!.findItem(R.id.reverse_true)
                reverse_true.isVisible=false
                var reverse_false = _menu!!.findItem(R.id.reverse_false)
                reverse_false.isVisible= true

            }

            R.id.action_board2 -> {
                showConfirm()
            }

        //temporarily move to notifications for demo


            R.id.action_lists->{

                showHelp()
            }
        }

        return super.onOptionsItemSelected(item)
    }
    fun showHelp(){
        builder =AlertDialog.Builder(this)
        builder?.setTitle("Reorder parcels ")
        builder?.setMessage(" \n Change the order that parcels are to be placed in the van " +
                "\n Parcels are the top are to be placed first" +
                "\n to reorder a parcel long tap on it and then move it on desired row" +
                "\n When done , click 'send to robot'  ")
        builder?.setNeutralButton("OK") { _: DialogInterface, _: Int -> }
        builder?.show()
    }

    fun showConfirm(){
        message=""
        var i=1
        parcels?.forEach {

            val parcelNumber=it.get(1).toString().toInt()

            println("parcel number is $parcelNumber")
            if (reverse){
                tuples="$parcelNumber,${list.get(parcelNumber)};"+tuples
            }
            else{
                tuples=tuples+"$parcelNumber,${list.get(parcelNumber)};"
            }



            message+="$i)Parcel $parcelNumber expected in shelve ${list.get(parcelNumber)}\n"
            i++
        }
        tuples=tuples.substring(0,tuples.length-1) // removes final semicolon
        println(tuples)

        var url = "http://hawkmon:5000/setinstructions?inst="

        url+=tuples


        builder =AlertDialog.Builder(this)
        builder?.setTitle("Please confirm order and expected shelves ")
        builder?.setMessage("$message")
        builder?.setPositiveButton("Confirm") { _: DialogInterface, _: Int ->


            val beforeTime = Calendar.getInstance().time

            val queue = Volley.newRequestQueue(baseContext)

            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener { response ->
                    //append notifications
                    notifications_string += response
                    // Display the first 500 characters of the response string.
                    val afterTime = Calendar.getInstance().time
                    val difference = afterTime.time - beforeTime.time
                    Toast.makeText(this, "order sent to robot", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this,
                        "No response from $url\n$error",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            // Add the request to the RequestQueue.
            queue.add(stringRequest)
            val intent = Intent(this, fragment_notifications::class.java)
            startActivity(intent)
        }
        builder?.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
        }
        builder?.setNeutralButton("Manage shelves ") { _: DialogInterface, _: Int ->
            val intent = Intent(this, Storage::class.java)

            startActivity(intent)
        }

        builder?.show()
    }


}

