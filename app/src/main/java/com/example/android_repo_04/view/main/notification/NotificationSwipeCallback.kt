package com.example.android_repo_04.view.main.notification

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


/**
 * Notification Item 각각 Swipe 를 위한
 * ItemTouchHelper.SimpleCallback 상속 클래스 정의.
 * ui 작업은 listener 에게 위임
 */
class NotificationSwipeCallback(
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
        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE)
            return


    }
}