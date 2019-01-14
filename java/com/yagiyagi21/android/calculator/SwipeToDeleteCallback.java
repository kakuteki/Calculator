package com.yagiyagi21.android.calculator;

import android.graphics.Canvas;
import android.graphics.*;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public abstract class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private Drawable _deleteIcon = ContextCompat.getDrawable(CalcActivity.getInstance(), R.drawable.ic_delete_white);
    private int _deleteIconIntrinsicWidth = _deleteIcon.getIntrinsicWidth();
    private int _deleteIconIntrinsicHeight = _deleteIcon.getIntrinsicHeight();
    private ColorDrawable _background = new ColorDrawable();

    public SwipeToDeleteCallback(int dragDirs, int swipeDirs)  {
        super(dragDirs, swipeDirs);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        boolean isCanceled = dX == 0 && !isCurrentlyActive;
        if(isCanceled) {
            clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        // 背景色を描画
        boolean isLeftDirection = dX < 0;
        if(isLeftDirection){
            _background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        }else {
            _background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (int) dX, itemView.getBottom());
        }
        _background.setColor(CalcActivity.getInstance().getResources().getColor(R.color.colorAccent));
        _background.draw(c);

        // deleteIconを描画
        int itemHeight = itemView.getBottom() -itemView.getTop();
        if(_deleteIcon != null & _deleteIconIntrinsicWidth != 0 & _deleteIconIntrinsicHeight != 0) {
            int deleteIconMargin = (itemHeight - _deleteIconIntrinsicHeight) / 2;
            int deleteIconTop = itemView.getTop() + deleteIconMargin;
            int deleteIconBottom = deleteIconTop + _deleteIconIntrinsicHeight;
            int deleteIconLeft;
            int deleteIconRight;

            if(isLeftDirection) {
                deleteIconLeft = itemView.getRight() - deleteIconMargin - _deleteIconIntrinsicWidth;
                deleteIconRight = itemView.getRight() - deleteIconMargin;
            }else {
                deleteIconLeft = itemView.getLeft() + deleteIconMargin;
                deleteIconRight = itemView.getLeft() + deleteIconMargin + _deleteIconIntrinsicWidth;
            }
            _deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            _deleteIcon.draw(c);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom) {
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        c.drawRect(left, top, right, bottom, clearPaint);
    }
}