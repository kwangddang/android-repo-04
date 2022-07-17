package com.example.android_repo_04.view.main.notification

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.R
import com.example.android_repo_04.utils.dpToPx


/**
 * Notification Item 각각 Swipe 를 위한
 * ItemTouchHelper.SimpleCallback 상속 클래스 정의.
 * ui 작업은 listener 에게 위임
 */
class NotificationSwipeCallback(
    private val context: Context,
    private val listener: NotificationSwipeListener
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

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
            listener.swipe(viewHolder, direction, position)
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
        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE || dX > 0) // Swipe Right
            return

        /**
         * viewWidth + dX(음수) 만큼 background color를 primary로 채움
         * -dX가 -(Icon Position Left) 를 넘을 때 해당 자리에 Icon 출력
         */
        viewHolder.itemView.let { view ->
            ColorDrawable(ContextCompat.getColor(context, R.color.primary)).apply {
                setBounds((view.right + dX).toInt(), view.top, view.right, view.bottom)
                draw(c)
            }
            ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_check)?.apply {
                val margin = dpToPx(context, 48f)
                val left = view.right - margin - intrinsicWidth
                if (-dX > view.right - left) {
                    val top = view.top + ((view.bottom - view.top - intrinsicHeight)/2)
                    val right = left + intrinsicWidth
                    val bottom = top + intrinsicHeight
                    setBounds(left, top, right, bottom)
                    draw(c)
                }
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}