/*
 * Copyright 2014 Magnus Woxblom
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package assistant.stacking.star

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

import assistant.stacking.star.Addon.BoardView
import assistant.stacking.star.Addon.DragItem

import java.util.ArrayList

class BoardFragment : Fragment() {
    private var mBoardView: BoardView? = null
    private var mColumns: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.board_layout, container, false)

        mBoardView = view.findViewById(R.id.board_view)
        mBoardView!!.setSnapToColumnsWhenScrolling(true)
        mBoardView!!.setSnapToColumnWhenDragging(true)
        mBoardView!!.setSnapDragItemToTouch(true)
        mBoardView!!.setCustomDragItem(MyDragItem(context!!, R.layout.column_item))
        mBoardView!!.setCustomColumnDragItem(MyColumnDragItem(context!!, R.layout.column_drag_layout))
        mBoardView!!.setSnapToColumnInLandscape(false)
        mBoardView!!.setColumnSnapPosition(BoardView.ColumnSnapPosition.CENTER)
        mBoardView!!.setBoardListener(object : BoardView.BoardListener {
            override fun onItemDragStarted(column: Int, row: Int) {
                //Toast.makeText(getContext(), "Start - column: " + column + " row: " + row, Toast.LENGTH_SHORT).show();
            }

            override fun onItemDragEnded(fromColumn: Int, fromRow: Int, toColumn: Int, toRow: Int) {
                if (fromColumn != toColumn || fromRow != toRow) {

                    //Toast.makeText(context, "from row $fromRow to row $toRow", Toast.LENGTH_SHORT).show()
                    var temp=parcels!![fromRow]
                    parcels!![fromRow]=parcels!![(toRow)]
                    parcels!![toRow]=temp

                    println("parcels are $parcels")
                    Toast.makeText(context,"item succesfully moved",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onItemChangedPosition(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int) {
                //Toast.makeText(mBoardView.getContext(), "Position changed - column: " + newColumn + " row: " + newRow, Toast.LENGTH_SHORT).show();
            }

            override fun onItemChangedColumn(oldColumn: Int, newColumn: Int) {
                val itemCount1 = mBoardView!!.getHeaderView(oldColumn).findViewById<TextView>(R.id.item_count)
                itemCount1.text = mBoardView!!.getAdapter(oldColumn)!!.itemCount.toString()
                val itemCount2 = mBoardView!!.getHeaderView(newColumn).findViewById<TextView>(R.id.item_count)
                itemCount2.text = mBoardView!!.getAdapter(newColumn)!!.itemCount.toString()
            }

            override fun onFocusedColumnChanged(oldColumn: Int, newColumn: Int) {
                //Toast.makeText(getContext(), "Focused column changed from " + oldColumn + " to " + newColumn, Toast.LENGTH_SHORT).show();
            }

            override fun onColumnDragStarted(position: Int) {
                //Toast.makeText(getContext(), "Column drag started from " + position, Toast.LENGTH_SHORT).show();
            }

            override fun onColumnDragChangedPosition(oldPosition: Int, newPosition: Int) {
                //Toast.makeText(getContext(), "Column changed from " + oldPosition + " to " + newPosition, Toast.LENGTH_SHORT).show();
            }

            override fun onColumnDragEnded(position: Int) {
                //Toast.makeText(getContext(), "Column drag ended at " + position, Toast.LENGTH_SHORT).show();
            }
        })
        mBoardView!!.setBoardCallback(object : BoardView.BoardCallback {
            override fun canDragItemAtPosition(column: Int, dragPosition: Int): Boolean {
                // Add logic here to prevent an item to be dragged
                return true
            }

            override fun canDropItemAtPosition(oldColumn: Int, oldRow: Int, newColumn: Int, newRow: Int): Boolean {
                // Add logic here to prevent an item to be dropped
                return true
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

      //  (activity as AppCompatActivity).supportActionBar!!.title = "Star app"
        addColumn()

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        //inflater.inflate(R.menu.menu_board, menu);
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        /* menu.findItem(R.id.action_disable_drag).setVisible(mBoardView.isDragEnabled());
        menu.findItem(R.id.action_enable_drag).setVisible(!mBoardView.isDragEnabled());*/
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        /* switch (item.getItemId()) {
            case R.id.action_disable_drag:
                mBoardView.setDragEnabled(false);
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.action_enable_drag:
                mBoardView.setDragEnabled(true);
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.action_add_column:
                addColumn();
                return true;
            case R.id.action_remove_column:
                mBoardView.removeColumn(0);
                return true;
            case R.id.action_clear_board:
                mBoardView.clearBoard();
                return true;
        }*/
        return super.onOptionsItemSelected(item)
    }

    private fun addColumn() {
        val mItemArray = ArrayList<Pair<Long, String>>()

        if (parcels!=null){
            for (i in 0 until parcels!!.size) {
                val id = i.toLong()
                mItemArray.add(Pair(id, parcels?.get(i)))
        }

        }

        val column = mColumns
        val listAdapter = ItemAdapter(mItemArray, R.layout.column_item, R.id.item_layout, true)
        val header = View.inflate(activity, R.layout.column_header, null)

        if (parcels!=null) {
            (header.findViewById<View>(R.id.item_count) as TextView).text = "" + parcels!!.size
            header.visibility=GONE
        }
        /* header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = sCreatedItems++;
                Pair item = new Pair<>(id, "Test " + id);
                mBoardView.addItem(mBoardView.getColumnOfHeader(v), 0, item, true);
                //mBoardView.moveItem(4, 0, 0, true);
                //mBoardView.removeItem(column, 0);
                //mBoardView.moveItem(0, 0, 1, 3, false);
                //mBoardView.replaceItem(0, 0, item1, true);
                ((TextView) header.findViewById(R.id.item_count)).setText(String.valueOf(mItemArray.size()));
            }
        });*/
        mBoardView!!.addColumn(listAdapter, header, header, false)
        mColumns++
    }

    private class MyColumnDragItem internal constructor(context: Context, layoutId: Int) : DragItem(context, layoutId) {

        init {
            setSnapToTouch(false)
        }

        override fun onBindDragView(clickedView: View, dragView: View) {
            val clickedLayout = clickedView as LinearLayout
            val clickedHeader = clickedLayout.getChildAt(0)
            val clickedRecyclerView = clickedLayout.getChildAt(1) as RecyclerView

            val dragHeader = dragView.findViewById<View>(R.id.drag_header)
            val dragScrollView = dragView.findViewById<ScrollView>(R.id.drag_scroll_view)
            val dragLayout = dragView.findViewById<LinearLayout>(R.id.drag_list)
            dragLayout.removeAllViews()

            (dragHeader.findViewById<View>(R.id.text) as TextView).text = (clickedHeader.findViewById<View>(R.id.text) as TextView).text
            (dragHeader.findViewById<View>(R.id.item_count) as TextView).text = (clickedHeader.findViewById<View>(R.id.item_count) as TextView).text
            for (i in 0 until clickedRecyclerView.childCount) {
                val view = View.inflate(dragView.context, R.layout.column_item, null)
                (view.findViewById<View>(R.id.text) as TextView).text = (clickedRecyclerView.getChildAt(i).findViewById<View>(R.id.text) as TextView).text
                dragLayout.addView(view)

                if (i == 0) {
                    dragScrollView.scrollY = -clickedRecyclerView.getChildAt(i).top
                }
            }

            dragView.pivotY = 0f
            dragView.pivotX = (clickedView.getMeasuredWidth() / 2).toFloat()
        }

        override fun onStartDragAnimation(dragView: View) {
            super.onStartDragAnimation(dragView)
            dragView.animate().scaleX(0.9f).scaleY(0.9f).start()
        }

        override fun onEndDragAnimation(dragView: View) {
            super.onEndDragAnimation(dragView)
            dragView.animate().scaleX(1f).scaleY(1f).start()
        }
    }

    private class MyDragItem internal constructor(context: Context, layoutId: Int) : DragItem(context, layoutId) {

        override fun onBindDragView(clickedView: View, dragView: View) {
            val text = (clickedView.findViewById<View>(R.id.text) as TextView).text
            (dragView.findViewById<View>(R.id.text) as TextView).text = text
            val dragCard = dragView.findViewById<CardView>(R.id.card)
            val clickedCard = clickedView.findViewById<CardView>(R.id.card)

            dragCard.maxCardElevation = 40f
            dragCard.cardElevation = clickedCard.cardElevation
            // I know the dragView is a FrameLayout and that is why I can use setForeground below api level 23
            dragCard.foreground = clickedView.resources.getDrawable(R.drawable.card_view_drag_foreground)
        }

        override fun onMeasureDragView(clickedView: View, dragView: View) {
            val dragCard = dragView.findViewById<CardView>(R.id.card)
            val clickedCard = clickedView.findViewById<CardView>(R.id.card)
            val widthDiff = dragCard.paddingLeft - clickedCard.paddingLeft + dragCard.paddingRight - clickedCard.paddingRight
            val heightDiff = dragCard.paddingTop - clickedCard.paddingTop + dragCard.paddingBottom - clickedCard.paddingBottom
            val width = clickedView.measuredWidth + widthDiff
            val height = clickedView.measuredHeight + heightDiff
            dragView.layoutParams = FrameLayout.LayoutParams(width, height)

            val widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
            val heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
            dragView.measure(widthSpec, heightSpec)
        }

        override fun onStartDragAnimation(dragView: View) {
            val dragCard = dragView.findViewById<CardView>(R.id.card)
            val anim = ObjectAnimator.ofFloat(dragCard, "CardElevation", dragCard.cardElevation,  -50f, 50f, -30f, 30f, -20f, 20f, -5f, 5f, 0f)
            anim.setInterpolator(DecelerateInterpolator())
            anim.setDuration(DragItem.ANIMATION_DURATION.toLong())
            anim.start()
        }


        override fun onEndDragAnimation(dragView: View) {
            val dragCard = dragView.findViewById<CardView>(R.id.card)
            val anim = ObjectAnimator.ofFloat(dragCard, "CardElevation", dragCard.cardElevation,  -50f, 50f, -30f, 30f, -20f, 20f, -5f, 5f, 0f)
            anim.setInterpolator(DecelerateInterpolator())
            anim.setDuration(DragItem.ANIMATION_DURATION.toLong())
            anim.start()
        }
    }

    companion object {

        private val sCreatedItems = 0

        fun newInstance(): BoardFragment {
            return BoardFragment()
        }
    }
}
