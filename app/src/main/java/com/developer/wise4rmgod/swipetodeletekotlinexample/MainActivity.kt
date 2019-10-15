package com.developer.wise4rmgod.swipetodeletekotlinexample

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.R.attr.logo
import androidx.core.content.res.ResourcesCompat


class MainActivity : AppCompatActivity() {

    private var imageModelArrayList: ArrayList<Model>? = null
    lateinit var adapter: Adapter
    private val p = Paint()

    private val myImageNameList =
        arrayOf("Benz", "Bike", "Car", "Carrera", "Ferrari", "Harly", "Lamborghini", "Silver")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageModelArrayList = populateList()

        adapter = Adapter(imageModelArrayList!!, applicationContext)
        recycler.adapter = adapter
        recycler.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        enableSwipe()

    }

    private fun enableSwipe() {
        val simpleItemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    val deletedModel = imageModelArrayList!!.get(position)
                    adapter.removeItem(position)
                    // showing snack bar with Undo option
                    val snackbar = Snackbar.make(
                        window.decorView.rootView,
                        " removed from Recyclerview!",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("UNDO") {
                        // undo is selected, restore the deleted item
                        adapter.restoreItem(deletedModel, position)
                    }
                    snackbar.setActionTextColor(Color.YELLOW)
                    snackbar.show()
                } else {
                    val deletedModel = imageModelArrayList!!.get(position)
                    adapter.removeItem(position)
                    // showing snack bar with Undo option
                    val snackbar = Snackbar.make(
                        window.decorView.rootView,
                        " removed from Recyclerview!",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("UNDO") {
                        // undo is selected, restore the deleted item
                        adapter.restoreItem(deletedModel, position)
                    }
                    snackbar.setActionTextColor(Color.YELLOW)
                    snackbar.show()
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3

                    if (dX > 0) {
                        p.color = Color.parseColor("#388E3C")
                        val background = RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)

                        icon = BitmapFactory.decodeResource(resources, R.drawable.deleteicon)

                        val icon_dest = RectF(
                            itemView.left.toFloat() + width,
                            itemView.top.toFloat() + width,
                            itemView.left.toFloat() + 2 * width,
                            itemView.bottom.toFloat() - width
                        )

                        c.drawBitmap(icon, null, icon_dest, p)
                    } else {
                        p.color = Color.parseColor("#D32F2F")
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)

                        icon = BitmapFactory.decodeResource(resources, R.drawable.deleteicon)
                        val icon_dest = RectF(
                            itemView.right.toFloat() - 2 * width,
                            itemView.top.toFloat() + width,
                            itemView.right.toFloat() - width,
                            itemView.bottom.toFloat() - width
                        )

                        c.drawBitmap(icon, null, icon_dest, p)
                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun populateList(): ArrayList<Model> {

        val list = ArrayList<Model>()

        for (i in 0..7) {
            val imageModel = Model()
            imageModel.name = myImageNameList[i]
            list.add(imageModel)
        }

        return list
    }

}
