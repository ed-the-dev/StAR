package assistant.stacking.star.notifications

import android.content.Context
import android.content.DialogInterface
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast


import assistant.stacking.star.R
import assistant.stacking.star.notifications.MessagesAdapter
import assistant.stacking.star.parcels
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

var notifications_string:String=""
val number_of_parcels:Int= parcels!!.size //non empty since one parcel is a minimun requirement


class fragment_notifications : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    MessagesAdapter.MessageAdapterListener {
    private val messages = ArrayList<Message>()
    private var recyclerView: RecyclerView? = null
    private var mAdapter: MessagesAdapter? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var actionModeCallback: ActionModeCallback? = null
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notifications)
        val toolbar = findViewById<View>(R.id.toolbar_notifications) as Toolbar
        setSupportActionBar(toolbar)
        var progressBar=findViewById<ProgressBar>(R.id.progressBar_notifications)
        progressBar.max= number_of_parcels
        progressBar.progress=0



        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        swipeRefreshLayout = findViewById<View>(R.id.swipe_refresh_layout) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener(this)

        mAdapter = MessagesAdapter(this, messages, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView!!.adapter = mAdapter

        actionModeCallback = ActionModeCallback()
       /* var  m1 =  Message()
        m1.from= "QR code does not match"
        m1.subject= "Parcel P02 not found , assistance required "
        m1.message="click here to take action"
        m1.timestamp="12:14"
        m1.id=0
        messages.add(m1)
        var  m2 =  Message()
        m2.from= "obstacle detected"
        m2.subject= "robot stopped moving , assistance required "
        m2.message="click here to take action"
        m2.timestamp="12:15"
        m2.id=1
        messages.add(0,m2)

        var  m3 =  Message()
        m3.from= "delivery succesful"
        m3.subject= "Parcels P02 and P08 succesfully placed in van "

        m3.timestamp="12:18"
        m3.id=2
        messages.add(0,m3)
        var  m4 =  Message()
        m4.from= "low battery"
        m4.subject= "charge required "
        m4.message="click here to shut down robot"
        m4.timestamp="12:19"
        m4.id=1

        messages.add(0,m4)
        mAdapter!!.notifyDataSetChanged()*/
        // show loader and fetch messages
       // swipeRefreshLayout!!.post { getInbox() }
        val handler = Handler()
        val delay = 5000 //milliseconds

        handler.postDelayed(object : Runnable {
            override fun run() {
                val url="http://hawkmon:5000/getnotifications"

                getNotifications(url,baseContext)
                var current_id=10
                if (!notifications_string.isNullOrEmpty()){
                    for(n in notifications_string.split(";") ){
                        var notification= Message()
                        notification.from=n
                        if (n=="Delivered"){

                            notification.subject="Parcel ${parcels!![progressBar.progress].substring(0,3)} delivered"
                            progressBar.progress++
                        }
                        notification.color=getRandomMaterialColor("200")
                        var time =Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()+":"
                        if ((Calendar.getInstance().get(Calendar.MINUTE))>9){
                            time+=Calendar.getInstance().get(Calendar.MINUTE).toString()
                        }
                        else {
                            time+="0"+Calendar.getInstance().get(Calendar.MINUTE).toString()
                        }
                        notification.timestamp=time
                        notification.id=current_id
                        messages.add(0,notification)
                        current_id++

                    }
                    notifications_string=""
                }



                mAdapter!!.notifyDataSetChanged()
                handler.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
    }



    /**
     * Fetches mail messages by making HTTP request
     * url: http://api.androidhive.info/json/inbox.json
     */
    private fun getInbox() {
        swipeRefreshLayout!!.isRefreshing = true

        val apiService = ApiClient.client.create(ApiInterface::class.java)

        val call = apiService.inbox
        call.enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                // clear the inbox
               // messages.clear()

                // add all the messages
                // messages.addAll(response.body());

                // TODO - avoid looping
                // the loop was performed to add colors to each message
                  for ( message in response.body()) {
                    // generate a random color
                    message.color=(getRandomMaterialColor("400"));
                    messages.add(message);
                }


                swipeRefreshLayout!!.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                Toast.makeText(applicationContext, "Unable to fetch json: " + t.message, Toast.LENGTH_LONG).show()
                swipeRefreshLayout!!.isRefreshing = false
            }
        })
    }

    /**
     * chooses a random color from array.xml
     */
    private fun getRandomMaterialColor(typeColor: String): Int {
        var returnColor = Color.GRAY
        val arrayId = resources.getIdentifier("mdcolor_$typeColor", "array", packageName)

        if (arrayId != 0) {
            val colors = resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_notifications, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_search) {
            Toast.makeText(applicationContext, "pulling rate is fixed for this demo", Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        // swipe refresh is performed, fetch the messages again
        getInbox()
    }

    override fun onIconClicked(position: Int) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback!!)
        }

        toggleSelection(position)
    }

    override fun onIconImportantClicked(position: Int) {
        // Star icon is clicked,
        // mark the message as important
        val message = messages[position]
        message.isImportant = !message.isImportant
        messages[position] = message
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onMessageRowClicked(position: Int) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (mAdapter!!.selectedItemCount > 0) {
            enableActionMode(position)
        } else {
            // read the message which removes bold from the row
            val message = messages[position]
            message.isRead = true
            messages[position] = message
            mAdapter!!.notifyDataSetChanged()

            Toast.makeText(applicationContext, "Read: " + message.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRowLongClicked(position: Int) {
        // long press is performed, enable action mode
        enableActionMode(position)
    }

    private fun enableActionMode(position: Int) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback!!)
        }
        toggleSelection(position)
    }

    private fun toggleSelection(position: Int) {
        mAdapter!!.toggleSelection(position)
        val count = mAdapter!!.selectedItemCount

        if (count == 0) {
            actionMode!!.finish()
        } else {
            actionMode!!.title = count.toString()
            actionMode!!.invalidate()
        }
    }


    private inner class ActionModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_action_mode, menu)

            // disable swipe refresh if action mode is enabled
            swipeRefreshLayout!!.isEnabled = false
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_delete -> {
                    // delete all the selected messages
                    deleteMessages()
                    mode.finish()
                    return true
                }

                else -> return false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            mAdapter!!.clearSelections()
            swipeRefreshLayout!!.isEnabled = true
            actionMode = null
            recyclerView!!.post {
                mAdapter!!.resetAnimationIndex()
                // mAdapter.notifyDataSetChanged();
            }
        }
    }

    // deleting the messages from recycler view
    private fun deleteMessages() {
        mAdapter!!.resetAnimationIndex()
        val selectedItemPositions = mAdapter!!.selectedItems
        for (i in selectedItemPositions.indices.reversed()) {
            mAdapter!!.removeData(selectedItemPositions[i])
        }
        mAdapter!!.notifyDataSetChanged()
    }

    fun getNotifications(url: String, context: Context) {

        val beforeTime = Calendar.getInstance().time
     //   val mTextView = findViewById<View>(R.id.textView) as TextView
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        //String url = "http://leconte:5000/sendinstruction?inst=True";
        //final String url = ((EditText)findViewById(R.id.editText)).getText().toString();

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            com.android.volley.Response.Listener { response ->
                //append notifications
                notifications_string+=response
                // Display the first 500 characters of the response string.
                val afterTime = Calendar.getInstance().time
                val difference = afterTime.time - beforeTime.time
               // mTextView.text = "Response: $response\nResponse time:$difference"
            }, com.android.volley.Response.ErrorListener { error -> Toast.makeText(this, "No response from $url\n$error",Toast.LENGTH_SHORT).show() })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    override fun onBackPressed() {
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("confirm exit?")
        builder.setPositiveButton("Confirm") { _: DialogInterface, _: Int ->

        super.onBackPressed()
        }

        builder.setNegativeButton("cancel") { _: DialogInterface, _: Int ->
        }
        builder.show()

    }
}

