package com.example.chess58;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;

public class ChessView extends View {

    public Chess chess;
    public static boolean allowMove;

    public ChessView(Context context) {
        super(context);
        intialize(null, 0);
    }
    public ChessView(Context context, AttributeSet attrs){
        super(context, attrs);
        intialize(attrs, 0);
    }
    public ChessView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        intialize(attrs, defStyle);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(allowMove) return chess.onTouchEvent(this, getContext(), event);
        else return false;
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        chess.DrawBoard(canvas);
    }
    private void intialize(AttributeSet attrs, int defStyle){
        chess = new Chess(getContext());
        allowMove = true;
    }

}
