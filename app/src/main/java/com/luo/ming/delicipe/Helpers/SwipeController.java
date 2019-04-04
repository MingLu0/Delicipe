package com.luo.ming.delicipe.Helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.luo.ming.delicipe.R;

import java.io.File;
import java.io.InputStream;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

enum ButtonsState {
    GONE,
    EDIT_CLICKED,
    DELETE_CLICKED
}

public class SwipeController extends Callback {

    private boolean swipeBack = false;

    private ButtonsState buttonShowedState = ButtonsState.GONE;

    private RectF buttonInstance = null;

    private RecyclerView.ViewHolder currentItemViewHolder = null;

    private SwipeControllerActions buttonsActions = null;

    private static final float buttonWidth = 200;
    private Context context;

    public SwipeController(SwipeControllerActions buttonsActions,Context context) {
        this.buttonsActions = buttonsActions;
        this.context=context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT );
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
            swipeBack = buttonShowedState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonsState.GONE) {
                dX= Math.min(dX, -buttonWidth*2);
                View itemView = viewHolder.itemView;
                Log.d("dX",String.valueOf(dX));
                Log.d("dXdY",String.valueOf(dY));
                Log.d("dXitem",String.valueOf(itemView.getX()));
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemViewHolder = viewHolder;
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;

                if (swipeBack) {
                    View itemView = viewHolder.itemView;
                    Log.d("onTouch event X",String.valueOf(event.getX()));
                    Log.d("onTouch itemView",String.valueOf(itemView.getWidth()));
                    Log.d("onTouch dX",String.valueOf(dX));

                    if (event.getX()< (itemView.getWidth()-buttonWidth)) buttonShowedState = ButtonsState.EDIT_CLICKED;
                    else buttonShowedState  = ButtonsState.DELETE_CLICKED;

                    if (buttonShowedState != ButtonsState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    View itemView = viewHolder.itemView;
                    Log.d("onTouchdown event X",String.valueOf(event.getX()));
                    Log.d("onTouchdown itemView",String.valueOf(itemView.getWidth()));
                    Log.d("onTouchdown dX",String.valueOf(dX));

                    if(event.getX()<itemView.getWidth()-buttonWidth) buttonShowedState = ButtonsState.EDIT_CLICKED;
                    else buttonShowedState = ButtonsState.DELETE_CLICKED;

                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeController.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    View itemView = viewHolder.itemView;
                    Log.d("onTouchup event X",String.valueOf(event.getX()));
                    Log.d("onTouchup itemView",String.valueOf(itemView.getWidth()));
                    Log.d("onTouchup dX",String.valueOf(dX));

                    if (buttonsActions != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {


                        if (buttonShowedState == ButtonsState.EDIT_CLICKED) {
                            buttonsActions.onEditClicked(viewHolder.getAdapterPosition());
                            Log.d("click","edit clicked");
                        }
                        else if (buttonShowedState == ButtonsState.DELETE_CLICKED) {
                            buttonsActions.onDeleteClicked(viewHolder.getAdapterPosition());
                            Log.d("click","delete clicked");
                        }
                    }
                    buttonShowedState = ButtonsState.GONE;
                    currentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
        float buttonWidthWithoutPadding = buttonWidth - 0;
        float corners = 16;

        View itemView = viewHolder.itemView;


        Bitmap bitmapEdit = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_action_edit);

        Paint p = new Paint();

        RectF editButton = new RectF(itemView.getRight()-(buttonWidthWithoutPadding*2)+30, itemView.getTop()+40, itemView.getRight() -buttonWidthWithoutPadding-30, itemView.getBottom()-40);

        p.setColor(Color.WHITE);

        c.drawBitmap(bitmapEdit,null,editButton,p);


        Bitmap bitmapDelete = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_action_delete);

        RectF deleteButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding+30, itemView.getTop()+40, itemView.getRight()-30, itemView.getBottom()-40);
        p.setColor(Color.WHITE);

        c.drawBitmap(bitmapDelete,null,deleteButton,p);


        buttonInstance = null;
        if (buttonShowedState == ButtonsState.EDIT_CLICKED) {
            buttonInstance = editButton;
        }
        else if (buttonShowedState == ButtonsState.DELETE_CLICKED) {
            buttonInstance = deleteButton;
        }
    }



    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }
}
