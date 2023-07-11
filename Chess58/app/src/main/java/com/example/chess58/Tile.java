package com.example.chess58;

import com.example.chess58.Pieces.Piece;

/**
 * Store location on screen, coordinate on game board, and piece on this tile
 */
public class Tile {
    /**
     * Piece stored on this tile
     */
    private Piece piece;
    /**
     * coordinate of this tile
     */
    private Pair boardCoordinate;
    /**
     * coordinate of this tile relative to the screen
     */
    private Pair screenCoordinate;
    /**
     * size of the tile
     */
    private float cellSize;

    /**
     * Constructor
     * @param marginY y location of this tile on the screen
     * @param marginX x location of this tile on the screen
     * @param row row this tile is occupying
     * @param col column this tile is occupying
     * @param piece piece located on this tile
     */
    public Tile(int marginY, int marginX, int row, int col, float cellSize, Piece piece){
        boardCoordinate = new Pair(row, col);
        screenCoordinate = new Pair(marginY, marginX);
        this.piece = piece;
        this.cellSize = cellSize;
    }

    /**
     * set the given piece to occupy this tile
     * @param piece piece to occupy this tile
     */
    public void SetPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * Return the piece occupying this tile
     * @return
     */
    public Piece GetPiece(){
        return piece;
    }

    /**
     * Return the row this tile occupy
     * @return
     */
    public int Row(){
        return boardCoordinate.row;
    }

    /**
     * Return the column this tile occupy
     * @return
     */
    public int Column(){
        return boardCoordinate.col;
    }

    /**
     * Return the y position relative to the screen
     * @return
     */
    public int RelativeScreenY(){
        return screenCoordinate.row;
    }

    /**
     * Return the x position relative to the screen
     * @return
     */
    public int RelativeScreenX(){
        return screenCoordinate.col;
    }

    public boolean inCell(float x, float y){

        float lowX = RelativeScreenX() - cellSize / 2f;
        float highX = RelativeScreenX() + cellSize / 2f;
        float lowY = RelativeScreenY() - cellSize / 2f;
        float highY = RelativeScreenY() + cellSize / 2f;
        //System.out.println("testX: " + x + "\t testY: " + y);
        //System.out.println("lowX: " + lowX + "\t highX: " + highX + "\nlowY: " + lowY + "\t highY: " + highY);
        return (x >= lowX && x <= highX && y >= lowY && y <= highY);
    }
}
