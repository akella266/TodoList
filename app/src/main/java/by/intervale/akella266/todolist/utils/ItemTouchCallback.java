package by.intervale.akella266.todolist.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import by.intervale.akella266.todolist.R;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static by.intervale.akella266.todolist.utils.ItemTouchCallback.ButtonState.GONE;


public class ItemTouchCallback extends ItemTouchHelper.Callback {

    private boolean mSwipeBack = false;
    private ButtonState mButtonShowedState = GONE;
    private RectF mLeftButton = null;
    private RectF mRightButton = null;
    private Context mContext;
    private static final float BUTTON_WIDTH = 300;
    private ItemTouchActions mButtonsActions;
    private int mCountButtons;

    public ItemTouchCallback(Context context, int countButtons, ItemTouchActions actoins) {
        this.mContext = context;
        this.mButtonsActions = actoins;
        this.mCountButtons = countButtons;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {}

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (mSwipeBack) {
            mSwipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE){
            if (mButtonShowedState != GONE) {
                if (mButtonShowedState == ButtonState.VISIBLE) dX = Math.min(dX, -BUTTON_WIDTH * mCountButtons);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        if (mButtonShowedState == GONE)
            super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);

        drawButtons(c, viewHolder);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener(final Canvas c,
                                  final RecyclerView recyclerView,
                                  final RecyclerView.ViewHolder viewHolder,
                                  final float dX, final float dY,
                                  final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mSwipeBack = motionEvent.getAction() == MotionEvent.ACTION_CANCEL ||
                        motionEvent.getAction() == MotionEvent.ACTION_UP;
                if(mSwipeBack) if (dX < -BUTTON_WIDTH) mButtonShowedState = ButtonState.VISIBLE;

                if(mButtonShowedState != GONE){
                    setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    setItemsClickable(recyclerView, false);
                }
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchDownListener(final Canvas c,
                                      final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY,
                                      final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) setTouchUpListener(c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive);
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchUpListener(final Canvas c,
                                    final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY,
                                    final int actionState, final boolean isCurrentlyActive) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ItemTouchCallback.super.onChildDraw(c,
                            recyclerView,
                            viewHolder,
                            0F,
                            dY,
                            actionState,
                            isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) { return false; }
                    });
                    setItemsClickable(recyclerView, true);
                    mSwipeBack = false;

                    if (mButtonsActions != null){
                        if (mLeftButton != null && mLeftButton.contains(event.getX(), event.getY())
                                && mButtonShowedState == ButtonState.VISIBLE)
                            mButtonsActions.onLeftClick(recyclerView.getAdapter(), viewHolder.getAdapterPosition());
                        else if(mRightButton != null && mRightButton.contains(event.getX(), event.getY())
                                && mButtonShowedState == ButtonState.VISIBLE)
                            mButtonsActions.onRightClick(recyclerView.getAdapter(), viewHolder.getAdapterPosition());
                    }
                    mButtonShowedState = GONE;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView,
                                   boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i)
            recyclerView.getChildAt(i).setClickable(isClickable);
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder){
        View itemView = viewHolder.itemView;
        Paint p = new Paint();

        RectF sButton = new RectF(itemView.getRight() - BUTTON_WIDTH,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRect(sButton,p);
        drawText("DELETE", c, sButton, p);

        RectF fButton = null;
        if (mCountButtons == 2) {
            fButton = new RectF(itemView.getRight() - BUTTON_WIDTH * 2,
                    itemView.getTop(),
                    itemView.getRight() - BUTTON_WIDTH,
                    itemView.getBottom());
            p.setColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            c.drawRect(fButton, p);
            drawText("DONE", c, fButton, p);
        }

        mLeftButton = null;
        mRightButton = null;
        if (mButtonShowedState == ButtonState.VISIBLE) {
            if (mCountButtons == 2) mLeftButton = fButton;
            mRightButton = sButton;
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint p) {
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
    }

    enum ButtonState{
        GONE,
        VISIBLE
    }
}
