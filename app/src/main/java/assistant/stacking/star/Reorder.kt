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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import kotlinx.android.synthetic.main.connection.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.reorder.*


class Reorder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reorder)
        parcels=intent.getStringArrayListExtra("parcelsList")
        Toast.makeText(this,"${parcels?.toString()}",Toast.LENGTH_SHORT)

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
            /* case R.id.action_lists:
                showFragment(ListFragment.newInstance());
                return true;*/
            R.id.action_board -> {
                showFragment(BoardFragment.newInstance())
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}

