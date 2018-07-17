package com.example.fa.billspliter.ui.billhistory

import android.content.DialogInterface
import android.graphics.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fa.billspliter.ui.adapter.HistoryAdapter
import com.example.fa.billspliter.R
import com.example.fa.billspliter.data.model.BillEntity
import com.example.fa.billspliter.presenter.RoomHelper
import com.example.fa.billspliter.ui.billspliter.HomeActivity.Companion.loginType
import com.example.fa.billspliter.util.DialogFactory
import kotlinx.android.synthetic.main.fragment_history.view.*
import android.support.v7.widget.helper.ItemTouchHelper
import android.opengl.ETC1.getWidth
import android.graphics.BitmapFactory
import android.graphics.Bitmap


class History : Fragment(), MvpViewHistory {

    var roomHelper = RoomHelper(this)
    private lateinit var recycleView: RecyclerView
    private var dialogFactory = DialogFactory()
    private var billList: ArrayList<BillEntity>? = null
    private lateinit var recycleAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recycleView = view.recycleView

        return view
    }

    override fun onResume() {
        super.onResume()
        if (loginType == "skip") {
            roomHelper.getHistory()
        } else {
            roomHelper.getHistorySaveServer()
        }
    }

    override fun setRecycleView(billList: List<BillEntity>) {
        try {
            this.billList = billList as ArrayList<BillEntity>
            recycleAdapter = HistoryAdapter(context!!, billList, this)
            val recycleLayout = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            recycleView.layoutManager = recycleLayout
            recycleView.adapter = recycleAdapter
            // initSwipeToDelete()
        } catch (e: NullPointerException) {
            Log.d("History Adapter error:", e.toString())
        }

        initSwipeToDelete()
    }

    /* Handling on click event*/
    override fun onClick(billEntity: BillEntity) {
        var dataBundle: Bundle = Bundle()
        dataBundle.putSerializable("data", billEntity)
        findNavController().navigate(R.id.action_history_to_historyDetail, dataBundle)
    }

    /* Handling on long click event. */
    override fun onLongClick(billEntity: BillEntity) {
        dialogFactory.createTwoButtonDialog(context!!, "Alert!", "Are you sure want to remove?",
                DialogInterface.OnClickListener { dialog, which ->
                    if (loginType == "skip") {
                        roomHelper.removeFromDb(billEntity)
                    } else {
                        roomHelper.removeFromFirebase(billEntity.serverKey!!)
                    }
                    billList?.remove(billEntity)
                    recycleAdapter.setData(billList!!)
                }).show()
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.setGroupVisible(0, false)
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val position = viewHolder!!.adapterPosition
                val selectedBill = billList?.get(position)
                if (loginType == "skip") {
                    roomHelper.removeFromDb(selectedBill!!)
                } else {
                    roomHelper.removeFromFirebase(selectedBill!!.serverKey!!)
                }
                billList?.remove(selectedBill)
                recycleAdapter.setData(billList!!)
            }

            override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                var dX = dX
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (dX > 0) {
                        /***  creating an icon ***/
                        val itemView = viewHolder!!.itemView
                        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.delete_icon)
                        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 60, 60, false)
                        c!!.drawBitmap(resizedBitmap, itemView.left.toFloat(), itemView.top.toFloat(), null)
                    } else if (dX < 0) {
                        /*** create text or button ***/
                        val buttonWidthWithoutPadding = 300f - 20
                        val corners = 16f
                        val itemView = viewHolder!!.itemView
                        val p = Paint()
                        val rightButton = RectF(itemView.right - buttonWidthWithoutPadding, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        p.color = Color.BLUE
                        c?.drawRoundRect(rightButton, corners, corners, p)
                        drawText("Remove", c!!, rightButton, p)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }).attachToRecyclerView(recycleView)

    }

    private fun drawText(text: String, c: Canvas, button: RectF, p: Paint) {
        val textSize = 60f
        p.color = Color.WHITE
        p.isAntiAlias = true
        p.textSize = textSize

        val textWidth = p.measureText(text)
        c.drawText(text, button.centerX() - textWidth / 2, button.centerY() + textSize / 2, p)
    }
}
