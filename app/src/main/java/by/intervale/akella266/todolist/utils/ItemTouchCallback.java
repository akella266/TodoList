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

    enum ButtonState{
        GONE,
        VISIBLE
    }

    private boolean swipeBack = false;
    private ButtonState buttonShowedState = GONE;
    private RectF leftButton = null;
    private RectF rightButton = null;
    private Context context;
    private static final float buttonWidth = 300;
    private ItemTouchActions buttonsActions;
    private int countButtons;

    public ItemTouchCallback(Context context, int countButtons, ItemTouchActions actoins) {
        this.context = context;
        this.buttonsActions = actoins;
        this.countButtons = countButtons;
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
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = false;
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
            if (buttonShowedState != GONE) {
                if (buttonShowedState == ButtonState.VISIBLE) dX = Math.min(dX, -buttonWidth*countButtons);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        if (buttonShowedState == GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
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
                swipeBack = motionEvent.getAction() == MotionEvent.ACTION_CANCEL ||
                        motionEvent.getAction() == MotionEvent.ACTION_UP;

                if(swipeBack){
                    if (dX < -buttonWidth) buttonShowedState = ButtonState.VISIBLE;
                }

                if(buttonShowedState != GONE){
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
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
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
                    ItemTouchCallback.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    if (buttonsActions != null){
                        if (leftButton != null && leftButton.contains(event.getX(), event.getY())
                                && buttonShowedState == ButtonState.VISIBLE)
                            buttonsActions.onLeftClick(recyclerView.getAdapter(), viewHolder.getAdapterPosition());
                        else if(rightButton != null && rightButton.contains(event.getX(), event.getY())
                                && buttonShowedState == ButtonState.VISIBLE)
                            buttonsActions.onRightClick(recyclerView.getAdapter(), viewHolder.getAdapterPosition());
                    }

                    buttonShowedState = GONE;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView,
                                   boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder){
          View itemView = viewHolder.itemView;
        Paint p = new Paint();

        RectF sButton = new RectF(itemView.getRight() - buttonWidth,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRect(sButton,p);
        drawText("DELETE", c, sButton, p);

        RectF fButton = null;
        if (countButtons == 2) {
            fButton = new RectF(itemView.getRight() - buttonWidth * 2,
                    itemView.getTop(),
                    itemView.getRight() - buttonWidth,
                    itemView.getBottom());
            p.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
            c.drawRect(fButton, p);
            drawText("DONE", c, fButton, p);
        }


        leftButton = null;
        rightButton = null;
        if (buttonShowedState == ButtonState.VISIBLE) {
            if (countButtons == 2)
                leftButton = fButton;
            rightButton = sButton;
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
}
