package com.example.chess58.Pieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import androidx.core.content.res.ResourcesCompat;

import com.example.chess58.ChessBoard;
import com.example.chess58.R;

import java.io.InputStream;

/**
 * @author Yunhao Xu
 * @author Minhesota Geusic
 * @version %I% %G%
 * @since 1.2
 */
public abstract class Piece {
    /**
     * player name that own this piece
     */
    protected String player;
    /**
     * name of this piece
     */
    protected String name;
    /**
     * resource location of the piece
     */
    protected int id;
    protected float offsetX = 18f /2f;
    protected float offsetY = 5f / 2f;
    protected Bitmap bitmap;
    protected Drawable drawable;
    /**
     * Constructor for Piece
     *
     * @param player name of player
     */
    public Piece (Context context, int pieceID, String player) {
        this.player = player;
        id = pieceID;
        drawable = ResourcesCompat.getDrawable(context.getResources(), pieceID, null);
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    }

    /**
     *
     * Determine if this piece can move from the given coordinate
     * to another given coordinate. It is assumed that the two
     * coordinates are valid.
     *
     *
     * @param curr_col current column
     * @param curr_row current row
     * @param new_col new column
     * @param new_row new row
     * @param cb chessboard
     *
     * @return true if the move is legal, false otherwise
     *
     */
    public abstract boolean isMoveLegal(int curr_row, int curr_col, int new_row, int new_col, ChessBoard cb);
    public void draw(Canvas canvas, int yScreenPosition, int xScreenPosition, float scale){
        drawable.setBounds(0,0, drawable.getIntrinsicWidth() * 20, drawable.getIntrinsicWidth() * 20);
        canvas.save();
        canvas.translate(xScreenPosition, yScreenPosition);
        canvas.scale(scale, scale);
        canvas.translate(-drawable.getIntrinsicWidth() * offsetX, drawable.getIntrinsicHeight() * offsetY);
        canvas.drawBitmap(bitmap, 0, 0, null);

        drawable.draw(canvas);
        canvas.restore();
    }
    /**
     * get the player name that owns this piece
     *
     * @return name of the player that own this piece
     */
    public String getPlayer() {
        return player;
    }

    /**
     * get the name of this piece
     *
     * @return name of this piece
     */
    public String toString() {
        return name;
    }
}