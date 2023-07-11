package com.example.chess58;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.view.MotionEvent;
import android.view.View;

import com.example.chess58.Pieces.Bishop;
import com.example.chess58.Pieces.King;
import com.example.chess58.Pieces.Knight;
import com.example.chess58.Pieces.Pawn;
import com.example.chess58.Pieces.Piece;
import com.example.chess58.Pieces.Queen;
import com.example.chess58.Pieces.Rook;

public class Chess {
    /**
     * the error message for illegal move
     */
    public static final String ILLEGALMOVEERROR = "Illegal move, try again";
    /**
     * maximum of number of players
     */
    public static final int MAX_NUMBER_PLAYERS = 2;

    /**
     * recording object for this game
     */
    private Recording recording;
    private Context context;
    /**
     * an array holds the kings from both sides of players
     */
    private King[] kings;

    /**
     * String array stores the references to the name of the two players
     */
    private String[] players;
    /**
     * array containing colors for each player
     */
    private int[] colors;
    /**
     * determine if this game is allowed to be recorded
     */
    private boolean record;
    /**
     * a boolean gameover indicates if game is over
     */
    public boolean gameOver = false;
    /**
     * an integer indicates who won the game
     */
    private int whoWon = 0;
    /**
     * an integer indicates which player is under check
     */
    private int whoInCheck = 0;
    /**
     * an integer shows whose turn is currently
     */
    private int playerTurn = 0;
    private ChessBoard chessBoard;
    /**
     * Record a move if this game is allow to record move
     * @param move move to be recorded
     */
    public void RecordMove(Move move) {
        //if we arent recording then prevent
        //move from being recorded
        if(!record) return;
        if(move == null) return;
        recording.RecordMove(move);
    }

    /**
     * Remove the last move that was recorded
     * @return the last recorded move
     */
    public Move RemoveLastMove(){
        //if we arent recording then no move to remove
        if(!record) return null;
        if(recording.Size() <= 0) return null;
        return recording.RemoveMove(recording.Size() - 1);
    }

    /**
     * constructor
     * @param context
     */
    public Chess(Context context) {
        record = true;
        this.context = context;
        recording = new Recording();
        colors = new int[MAX_NUMBER_PLAYERS];
        players = new String[MAX_NUMBER_PLAYERS];
        kings = new King[MAX_NUMBER_PLAYERS];
        players[0] = "w";
        players[1] = "b";
        colors[0] = Color.WHITE;
        colors[1] = Color.BLACK;
        kings[0] = new King(context, players[0], Color.WHITE);
        kings[1] = new King(context, players[1], Color.BLACK);
        chessBoard = new ChessBoard(context, players[0], players[1], kings[0], kings[1]);
    }

    /**
     * constructor to receive recording, and play pre-made game
     * @param context
     * @param recording
     */
    public Chess(Context context, Recording recording){
        //if this constructor is chosen, only recording input is allowed
        this(context);
        record = false;
        this.recording = recording;
    }

    /**
     * Return the chess board
     * @return the chess board
     */
    public ChessBoard GetBoard(){return chessBoard;}
    /**
     * draw the board on the given canvas
     * @param canvas canvas to draw on
     */
    public void DrawBoard(Canvas canvas){
        chessBoard.draw(canvas);
    }
    /**
     * check thoroughly if the inputs provided by the users are valid inputs, by
     * running through numbers of check methods
     *
     * @param oldRow  the current row of the piece that is about to be moved
     * @param oldCol  the current column of the piece that is about to be moved
     * @param newRow  the row, to which the piece will move
     * @param newCol  the column, to which the piece will move
     * @param upgrade the piece that pawn would get promoted to if allowed
     *
     * @return true if input is valid, otherwise return false
     */
    //we will need to remove upgrade param and have another handle input function that contain upgrade
    public boolean handleInput(int oldRow, int oldCol, int newRow, int newCol, char upgrade){
        Piece currPiece = null;
        int currPiece_Player = 0;

        if(!handleInput(oldRow, oldCol, newRow, newCol)){
            return false;
        }

        currPiece = chessBoard.getPiece(newRow, newCol);
        currPiece_Player = findPlayersIndex(currPiece.getPlayer());

        // perform pawn upgrade if possible
        if (currPiece instanceof Pawn) {
            // we need to check if currPiece is allow to make upgrade
            // black side
            if (currPiece_Player == 1) {
                // pawn has reach the opposite side
                // row == 0
                if (newRow == 0) {
                    // we can upgrade
                    UpgradeTo(newRow, newCol, upgrade, currPiece.getPlayer());
                }
            }
            // white side
            else {
                // pawn has reach the opposite side
                if (newRow == 7) {
                    UpgradeTo(newRow, newCol, upgrade, currPiece.getPlayer());
                }
            }
        }

        // recheck if pawn upgrading achieved check
        if (targetPlayerAchievedCheck(playerTurn)) {

            // test for checkmate
            if (targetPlayerAcheivedCheckmate(playerTurn)) {
                // if checkmate occurs then game over
                //possibly we make a toast?
                System.out.println("Checkmate");
                gameOver = true;
                whoWon = playerTurn;
                return true;
            }
            //possibly we make a toast?
            System.out.println("Check");
            return true;
        }

        return true;
    }
    /**
     * check thoroughly if the inputs provided by the users are valid inputs, by
     * running through numbers of check methods.
     * @param oldRow
     * @param oldCol
     * @param newRow
     * @param newCol
     * @return
     */
    public boolean handleInput(int oldRow, int oldCol, int newRow, int newCol){
        Piece currPiece = null;
        Piece nextPiece = null;
        int currPiece_Player = 0;
        int otherPlayerIndex = 1;
        // do a preliminary check to ensure coordinates are
        // 1. valid
        // 2. player are using their own pieces
        // 3. player cannot cannibalizes their own pieces
        if (!preliminaryCheck(oldRow, oldCol, newRow, newCol, playerTurn)) {
            System.out.println(ILLEGALMOVEERROR);
            return false;
        }
        currPiece = chessBoard.getPiece(oldRow, oldCol);
        // test if move is legal
        if (!currPiece.isMoveLegal(oldRow, oldCol, newRow, newCol, chessBoard)) {
            System.out.println(ILLEGALMOVEERROR);
            return false;
        }
        currPiece_Player = findPlayersIndex(currPiece.getPlayer());

        if (currPiece_Player == 1)
            otherPlayerIndex = 0;

        nextPiece = chessBoard.getPiece(newRow, newCol);
        // move currPiece to new location
        if (nextPiece != null)
            chessBoard.removePiece(newRow, newCol);
        chessBoard.removePiece(oldRow, oldCol);
        chessBoard.addPiece(newRow, newCol, currPiece);
        // update the newly moved piece's coordinate
        chessBoard.updateLastMovedPiece(newRow, newCol);

        // determine if the given move forced us in check
        if (targetPlayerAchievedCheck(otherPlayerIndex)) {
            // if this move forced us in check
            // we need to reset the pieces
            System.out.println(ILLEGALMOVEERROR);
            chessBoard.removePiece(newRow, newCol);
            chessBoard.addPiece(oldRow, oldCol, currPiece);
            if (nextPiece != null)
                chessBoard.addPiece(newRow, newCol, nextPiece);
            return false;
        }

        // if we are freed from check, then determine if we put other
        // player in checkmate

        // determine if given move put other player in check
        if (targetPlayerAchievedCheck(playerTurn)) {

            // test for checkmate
            if (targetPlayerAcheivedCheckmate(playerTurn)) {
                // if checkmate occurs then game over
                System.out.println("Checkmate");
                gameOver = true;
                whoWon = playerTurn;
                return true;
            }
            System.out.println("Check");
            return true;
        }

        return true;
    }
    /**
     * Determine if the given target player has achieved checkmate
     *
     * @param targetPlayer the player who acheives the checkmate
     * @return true if this player has achieved checkmate, otherwise false
     */
    private boolean targetPlayerAcheivedCheckmate(int targetPlayer) {
        if (gameOver == true)
            return true;
        String otherPlayer = players[0];
        int otherPlayerIndex = 0;
        if (targetPlayer == 0) {
            otherPlayerIndex = 1;
            otherPlayer = players[1];
        }
        // we will brute force to check the opposite player's
        // pieces if they can stop the checkmate
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = chessBoard.getPiece(row, col);
                if (p == null)
                    continue;
                if (!p.getPlayer().equals(otherPlayer))
                    continue;
                // determine if this piece can cancel the checkmate
                for (int prow = 0; prow < 8; prow++) {
                    for (int pcol = 0; pcol < 8; pcol++) {
                        if (!preliminaryCheck(row, col, prow, pcol, otherPlayerIndex))
                            continue;
                        // if this piece can make a move, determine if that move
                        // can block the check
                        if (p.isMoveLegal(row, col, prow, pcol, chessBoard)) {
                            Piece nextPiece = chessBoard.getPiece(prow, pcol);
                            // put this piece at the prow, pcol and
                            // test if check still occur
                            chessBoard.removePiece(nextPiece);
                            chessBoard.removePiece(row, col);
                            chessBoard.addPiece(prow, pcol, p);
                            // test if the target player can still put
                            // the opposite player in check
                            if (!targetPlayerAchievedCheck(targetPlayer)) {
                                // add the pieces back
                                chessBoard.removePiece(prow, pcol);
                                chessBoard.addPiece(row, col, p);
                                chessBoard.addPiece(prow, pcol, nextPiece);
                                return false;
                            }
                            chessBoard.removePiece(prow, pcol);
                            chessBoard.addPiece(row, col, p);
                            chessBoard.addPiece(prow, pcol, nextPiece);
                        }
                    }
                }
            }
        }
        return true;
    }
    /**
     * Determine if the target player can put the other player in check
     *
     * @param targetPlayer the player who puts the opponent under check
     * @return true if the player successfully puts the other player under check,
     *         false otherwise
     */
    public boolean targetPlayerAchievedCheck(int targetPlayer) {
        if (gameOver == true)
            return true;
        Pair otherPlayerKingPosition = null;
        if (targetPlayer == 0)
            otherPlayerKingPosition = chessBoard.getPieceCoordinate(kings[1]);
        else
            otherPlayerKingPosition = chessBoard.getPieceCoordinate(kings[0]);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = chessBoard.getPiece(row, col);
                int playerIndex = 0;
                if (p == null)
                    continue;
                playerIndex = findPlayersIndex(p.getPlayer());
                if (playerIndex != targetPlayer)
                    continue;
                if (!preliminaryCheck(row, col, otherPlayerKingPosition.row, otherPlayerKingPosition.col, targetPlayer))
                    continue;
                // if this piece can put other player's
                // king in check then return true
                if (p.isMoveLegal(row, col, otherPlayerKingPosition.row, otherPlayerKingPosition.col, chessBoard)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Determine if the given coordinates are valid, using only their pawn, and
     * cannot move on an ally tile
     *
     * @param curr_row the current row of this piece
     * @param curr_col the current column of this piece
     * @param new_row  the new row that this piece moves to
     * @param new_col  the new column that this piece moves to
     * @param player   the player that makes the move
     * @return true if this movement has passed the preliminary check, false
     *         otherwise
     */
    private boolean preliminaryCheck(int curr_row, int curr_col, int new_row, int new_col, int player) {
        Piece currPiece = null;
        Piece nextPiece = null;
        int currPiece_Player = 0;
        // check if coordinates are valid and are not the the same coordinate
        if (!chessBoard.isCoordinateValid(curr_row, curr_col) || !chessBoard.isCoordinateValid(new_row, new_col)
                || (curr_row == new_row && curr_col == new_col))
            return false;

        currPiece = chessBoard.getPiece(curr_row, curr_col);
        // test if move is legal
        if (currPiece == null)
            return false;

        currPiece_Player = findPlayersIndex(currPiece.getPlayer());
        // ensure player only use their pawn
        if (currPiece_Player != player)
            return false;

        nextPiece = chessBoard.getPiece(new_row, new_col);
        // check if the new location has been occupied by ally
        if (nextPiece != null && nextPiece.getPlayer().equals(currPiece.getPlayer()))
            return false;

        return true;
    }
    /**
     * Advance to next player's turn
     */
    public void nextPlayerTurn() {
        if (gameOver == true)
            return;
        playerTurn++;
        if (playerTurn >= MAX_NUMBER_PLAYERS)
            playerTurn = 0;
    }

    /**
     * Find the given player's name index
     *
     * @param player name of player
     * @return return the index of the given player
     */
    public int findPlayersIndex(String player) {
        if (player == null)
            return 0;
        if (players[0].equals(player))
            return 0;
        else
            return 1;
    }

    /**
     * print the winner, print nothing if game is not over
     */
    public String printWinner() {
        if (gameOver == false)
            return "";
        if (whoWon == 0) {
            return ("White wins");
        } else {
            return ("Black wins");
        }

    }
    /**
     * Add the upgrade version given by p to the new location <newCol,newRow> added
     * to the player
     *
     * @param newRow the row of current piece for upgrade
     * @param newCol the column of current piece for upgrade
     * @param p      the character that represents the piece the pawn gets promoted
     *               to
     * @param player the player whose pawn gets promoted
     */
    private void UpgradeTo(int newRow, int newCol, char p, String player) {
        if (!chessBoard.isCoordinateValid(newRow, newCol))
            return;
        // remove the current piece
        chessBoard.removePiece(newRow, newCol);
        // replace with new piece
        switch (p) {
            case 'R':
                chessBoard.addPiece(newRow, newCol, new Rook(context, player, colors[findPlayersIndex(player)]));
                break;
            case 'N':
                chessBoard.addPiece(newRow, newCol, new Knight(context, player, colors[findPlayersIndex(player)]));
                break;
            case 'B':
                chessBoard.addPiece(newRow, newCol, new Bishop(context, player, colors[findPlayersIndex(player)]));
                break;
            case 'Q':
                chessBoard.addPiece(newRow, newCol, new Queen(context, player, colors[findPlayersIndex(player)]));
                break;
            default:
                break;
        }
    }
    private boolean FindValidMoveFor(Piece p, int oldRow, int oldCol, Context context){
        for(int r = 0; r < chessBoard.ROW; r++){
            for(int c = 0; c < chessBoard.COLUMN; c++){
                if(oldRow != r && oldCol != c && chessBoard.PerformMove(oldRow, oldCol, r, c, this, context, false, 'Q')){
                    System.out.println("true");
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * print the current player's turn; Print nothing if game is over
     */
    public String whosTurn() {
        if (gameOver == true)
            return null;
        if (playerTurn == 0) {
            return "White's move";
        } else {
            return "Black's move";
        }
    }

    /**
     * End game by draw
     */
    public void Draw(){
        gameOver = true;
    }
    /**
     * Resign made by the current player
     */
    public void Resign(){
        if(gameOver == true) return;
        gameOver = true;
        if(playerTurn == 0) whoWon = 1;
        else whoWon = 0;
    }

    /**
     * Invoke the AI to move for the current player
     * @param view
     * @return
     */
    public boolean InvokeAI(View view, Context context){
        if(gameOver == true) return false;
        for(int r = 0; r < chessBoard.ROW; r++){
            for(int c = 0; c < chessBoard.COLUMN; c++){
                Piece p = chessBoard.getPiece(r, c);
                int p_playerIndex = 0;
                if(p == null) continue;
                p_playerIndex = findPlayersIndex(p.getPlayer());
                if(p_playerIndex != playerTurn) continue;
                //test if we can find a valid move for this pawn
                if(FindValidMoveFor(p, r, c, context)){
                    view.invalidate();
                    return true;
                }
            }
        }
        return false;
    }
    public Recording getRecording(){
        return recording;
    }
    /**
     * Handle on touch event
     * @param view
     * @param context
     * @param event
     * @return
     */
    public boolean onTouchEvent(View view, Context context ,MotionEvent event){
        return chessBoard.onTouchEvent(this, context, view, event);
    }


}
