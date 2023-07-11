package com.example.chess58;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.chess58.Pieces.Bishop;
import com.example.chess58.Pieces.King;
import com.example.chess58.Pieces.Knight;
import com.example.chess58.Pieces.Pawn;
import com.example.chess58.Pieces.Piece;
import com.example.chess58.Pieces.Queen;
import com.example.chess58.Pieces.Rook;

public class ChessBoard {
    /**
     * maximum number of tiles of the board width
     */
    public static final int MAX_TILE_WIDTH = 8;
    /**
     * maximum number of tiles of the board height
     */
    public static final int MAX_TILE_HEIGHT = 8;
    /**
     * Data structure for board
     */
    private Tile[][] board;

    private Piece[] pieces;

    private boolean boardCreated;
    /**
     * size of the board
     */
    public final static int COLUMN = 8, ROW = 8;
    /**keep track of last moved piece's coordinates
     */
    private Pair lastMovedPiece = new Pair(-1,-1); // initializing them to -1 to indicate that nobody has moved yet
    /**
     * Used to keep track of initial press location
     */
    public Pair currLastMoveLoc = new Pair(-1,-1); // initialized to -1
    public Pair currFinalMoveLoc = new Pair(-1, -1); // initialized to -1
    /**
     *
     */
    private Paint fillPaint;
    /**
     * outline color
     */
    private Paint outlinePaint;
    /**
     * size of board in pixels
     */
    private int boardSize;
    /**
     * Left margin in pixels
     */
    private int marginX;
    private int gray_color = 0xffffffff;
    private int black_color = Color.GRAY;
    private int oldRow, oldCol, newRow, newCol;
    /**
     * Top margin in pixels
     */
    private int marginY;
    /**
     * board scale factor
     */
    private float scaleFactor;
    /**
     * location of last touched x
     */
    private float lastTouchX;
    /**
     * Location of last touched y
     */
    private float lastTouchY;
    /**
     *
     */
    final static float SCALE_IN_VIEW = 0.9f;
    /**
     * constructor for chessboard
     */
    public ChessBoard(Context context, String player1, String player2, King king1, King king2){
        board = new Tile[ROW][COLUMN];
        pieces = new Piece[4 * COLUMN];

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(gray_color);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(black_color);
        outlinePaint.setStrokeWidth(10);

        pieces[0] = new Rook(context, player1, Color.WHITE);
        pieces[1] = new Knight(context, player1, Color.WHITE);
        pieces[2] = new Bishop(context, player1, Color.WHITE);
        pieces[3] = new Queen(context, player1, Color.WHITE);
        pieces[4] = king1;
        pieces[5] = new Bishop(context, player1, Color.WHITE);
        pieces[6] = new Knight(context, player1, Color.WHITE);
        pieces[7] = new Rook(context, player1, Color.WHITE);
        for(int i = 8; i < 8 + COLUMN; i ++){
            pieces[i] = new Pawn(context, player1, Color.WHITE, true);
        }

        for(int i = 8 + COLUMN; i < 8 + COLUMN + COLUMN; i++){
            pieces[i] = new Pawn(context, player2, Color.BLACK, false);
        }
        pieces[8 + COLUMN + COLUMN] = new Rook(context, player2, Color.BLACK);
        pieces[8 + COLUMN + COLUMN + 1] = new Knight(context, player2, Color.BLACK);
        pieces[8 + COLUMN + COLUMN + 2] = new Bishop(context, player2, Color.BLACK);
        pieces[8 + COLUMN + COLUMN + 3] = new Queen(context, player2, Color.BLACK);
        pieces[8 + COLUMN + COLUMN + 4] = king2;
        pieces[8 + COLUMN + COLUMN + 5] = new Bishop(context, player2, Color.BLACK);
        pieces[8 + COLUMN + COLUMN + 6] = new Knight(context, player2, Color.BLACK);
        pieces[8 + COLUMN + COLUMN + 7] = new Rook(context, player2, Color.BLACK);
    }
    /**
     * Determine if the given coordinate is valid
     *
     * @param i row
     * @param j column
     *
     * @return return true if given coordinate is valid,
     * false otherwise
     */
    public boolean isCoordinateValid(int i, int j) {
        return !(i < 0 || j < 0 || j >= COLUMN || i >= ROW );
    }
    /**
     * Return game piece located on i,j
     *
     * @param i row
     * @param j column
     *
     * @return return the game piece on i,j. Null otherwise
     */
    public Piece getPiece(int i, int j) {
        if(board == null) return null;
        if(!isCoordinateValid(i, j)) return null;
        return board[i][j].GetPiece();
    }
    /**
     * return the coordinate pair i,j for the given Piece p
     *
     * @param p piece
     *
     * @return pair of coordinate i,j if Piece p exist, false otherwise.
     */
    public Pair getPieceCoordinate (Piece p){
        if(board == null || p == null) return null;
        int i = 0, j = 0;
        for(i = 0; i < 8; i ++)
            for(j = 0; j < 8; j++)
                if(board[i][j] != null && board[i][j].GetPiece() == p) return new Pair(i, j);
        return null;
    }
    /**
     * Add a given Piece p into the coordinate
     *
     * @param i row
     * @param j column
     * @param p piece
     *
     * @return true if the given piece p can be added to the
     * given coordinate, false otherwise
     */
    public boolean addPiece(int i, int j, Piece p) {
        if(board == null || p == null) return false;
        if(!isCoordinateValid(i,j)) return false;
        if(board[i][j].GetPiece() != null) return false;
        System.out.println(("x: " + board[i][j].RelativeScreenX() + ", y: " + board[i][j].RelativeScreenY()) + "\t" + (board[i][j] == null));
        board[i][j].SetPiece(p);
        return false;
    }
    /**
     * remove the given Piece p from the board
     *
     * @param p piece
     *
     * @return true if the piece was removed, false otherwise
     */
    public boolean removePiece(Piece p) {
        if(board == null) return false;
        Pair pair = getPieceCoordinate(p);
        if(pair == null) return false;
        board[pair.row][pair.col].SetPiece(null);
        return true;
    }
    /**
     * remove a piece from the given coordinate
     *
     * @param i row
     * @param j column
     * @return the piece on the given coordinate, null if
     * none exist
     */
    public Piece removePiece(int i, int j) {
        if(board == null) return null;
        if(!isCoordinateValid(i,j)) return null;
        if(board[i][j] == null) return null;
        System.out.println("attempting to remove:");
        System.out.println("requested position: < " + i + ", " + j );
        Piece retp = board[i][j].GetPiece();
        board[i][j].SetPiece(null);
        return retp;
    }
    /**
     * update the coordinate of most recently moved piece
     *
     * @param row row
     * @param col column
     *
     **/
    public void updateLastMovedPiece(int row, int col) {
        lastMovedPiece.row = row;
        lastMovedPiece.col = col;
    }
    /**
     * give the coordinate of last moved piece
     *
     * @return the pair row,column as its coordinate. null if no one has moved yet
     **/
    public Pair lastPieceMoved () {
        if (lastMovedPiece.row < 0 || lastMovedPiece.col > 7) return null;
        return lastMovedPiece;
    }
    public void printBoard(){
        boolean blackSpot = false;
        for(int i = 7 ; i >= 0 ; i--) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j].GetPiece() == null) {
                    if(!blackSpot) {
                        System.out.print("   ");
                    }else {
                        System.out.print("## ");
                    }
                }else {
                    System.out.print(board[i][j].GetPiece().getPlayer() + board[i][j].GetPiece().toString() + " ");
                }
                blackSpot = !blackSpot;
            }
            blackSpot = !blackSpot;
            System.out.println((i+1));
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }
    /**
     * Get the index of the given file s
     *
     * @param s file
     * @return an integer representing the given file s. Return -1 s is an incorrect
     *         file.
     */
    public int getFileIndex(char s) {
        if (s == 'a' || s == 'A')
            return 0;
        if (s == 'b' || s == 'B')
            return 1;
        if (s == 'c' || s == 'C')
            return 2;
        if (s == 'd' || s == 'D')
            return 3;
        if (s == 'e' || s == 'E')
            return 4;
        if (s == 'f' || s == 'F')
            return 5;
        if (s == 'g' || s == 'G')
            return 6;
        if (s == 'h' || s == 'H')
            return 7;
        return -1;
    }
    /**
     * Draw the chess board on the canvas
     * @param canvas
     */
    public void draw(Canvas canvas){
        //this function must not store canvas
        //do not perform any initialization that
        //persist beyond in this function
        int canvasH = canvas.getHeight();
        int canvasW = canvas.getWidth();
        int rectWidth;
        int minDimension = canvasH;
        int xDelta, yDelta;
        boolean tileColorToggle = false;

        if(canvasW < canvasH) minDimension = canvasW;

        boardSize = (int)(minDimension * SCALE_IN_VIEW);

        marginX = (int)((canvasW - boardSize) / 2f);
        marginY = (int)((canvasH - boardSize) / 2f);

        scaleFactor = boardSize / ((float)(1 * 5));

        rectWidth = boardSize / 8;
        //draw outline
        DrawBoardOutLine(canvas);
        //background
        canvas.drawRect(marginX, marginY, marginX + boardSize, marginY + boardSize, fillPaint);
        //draw individual squares
        //iterate by column first
        xDelta = 0;
        yDelta = 0;
        for(int x = 0; x < MAX_TILE_WIDTH; x++ ){
            //iterate by row second
            for(int y = 0; y < MAX_TILE_HEIGHT; y++){
                //black tile
                if(tileColorToggle){
                    canvas.drawRect(marginX + xDelta, marginY + yDelta, marginX + rectWidth + xDelta, marginY + rectWidth + yDelta, outlinePaint);
                }
                //white tile
                else{
                    canvas.drawRect(marginX + xDelta, marginY + yDelta, marginX + rectWidth + xDelta, marginY + rectWidth + yDelta, fillPaint);
                }
                xDelta += rectWidth;
                tileColorToggle = !tileColorToggle;
            }
            xDelta = 0;
            yDelta += rectWidth;
            tileColorToggle = !tileColorToggle;
        }
        xDelta = marginX;
        yDelta = marginY;
        int i = 0;
        //fill arrays with tiles for each coordinate row,col
        for (int row = 0; row < ROW; row++){
            for(int col = 0; col < COLUMN; col++){
                if(!boardCreated){
                    Tile tile = new Tile(marginY - yDelta + ((marginY + rectWidth * (ROW - 1))), marginX + xDelta, row, col, rectWidth,null);
                    if( row == 0 || row == 1 || row == 6 || row == 7 ){
                        tile.SetPiece(pieces[i]);
                        i++;
                    }
                    board[row][col] = tile;
                }
                DrawPiece(canvas, board[row][col]);
                xDelta += rectWidth;
            }
            xDelta = marginX;
            yDelta += rectWidth;
        }
        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.restore();
        boardCreated = true;
    }
    public boolean PerformMove(int oldRow, int oldCol, int newRow, int newCol, Chess chess, Context context, boolean displayError, char upgradePiece){
        String oldPiece = null;
        String newPiece = null;
        int takenRow = -1;
        int takenCol = -1;
        int currPieceNumMove = 0;
        int targetPieceNumMove = 0;
        boolean currPieceFirstMove = false;
        boolean targetPieceFirstMove = false;
        boolean lastMoveFirst = false;
        boolean targetLastMoveFirst = false;
        boolean upgrade = false;
        char upgradeTo = upgradePiece;

        System.out.println("hit on tile <" + newRow + ", " + newCol + ">");
        if(getPiece(oldRow, oldCol) != null){
            Piece p = getPiece(oldRow, oldCol);
            Piece p1 = getPiece(newRow, newCol);
            String playerName = p.getPlayer();
            String pieceName = p.toString();
            oldPiece = p.toString();

            if(p instanceof Pawn && p1 == null){
                Piece pawn = getPiece(oldRow, newCol);
                if(pawn != null && pawn instanceof Pawn && !p.getPlayer().equals(pawn.getPlayer())){
                    p1 = pawn;
                    takenRow = oldRow;
                    takenCol = newCol;
                }
            }

            //determine first move and number of moves
            if(p1 == null ){
                if(p instanceof Pawn){
                    currPieceNumMove = ((Pawn)p).numberOfMoves();
                    targetPieceNumMove = 0;
                    currPieceFirstMove = ((Pawn)p).hasMoved();
                    targetPieceFirstMove = false;
                    lastMoveFirst = ((Pawn)p).wasLastMoveFirstMove();
                    targetLastMoveFirst = false;
                }
                else if(p instanceof Rook){
                    System.out.println("p is instance of rook");
                    currPieceNumMove = 0;
                    targetPieceNumMove = 0;
                    currPieceFirstMove = ((Rook)p).hasMoved();
                    targetPieceFirstMove = false;
                    lastMoveFirst = false;
                    targetLastMoveFirst = false;
                }else{
                    System.out.println("p is not instance of pawn or rook");
                    currPieceNumMove = 0;
                    targetPieceNumMove = 0;
                    currPieceFirstMove = false;
                    targetPieceFirstMove = false;
                    lastMoveFirst = false;
                    targetLastMoveFirst = false;
                }
            }
            else{
                if(p instanceof Pawn && p1 instanceof Pawn){
                    currPieceNumMove = ((Pawn)p).numberOfMoves();
                    targetPieceNumMove = ((Pawn)p1).numberOfMoves();
                    currPieceFirstMove = ((Pawn)p).hasMoved();
                    targetPieceFirstMove = ((Pawn)p1).hasMoved();
                    lastMoveFirst = ((Pawn)p).wasLastMoveFirstMove();
                    targetLastMoveFirst = ((Pawn)p1).wasLastMoveFirstMove();
                }
                else if(p instanceof Rook && p1 instanceof Rook){
                    currPieceNumMove = 0;
                    targetPieceNumMove = 0;
                    currPieceFirstMove = ((Rook)p).hasMoved();
                    targetPieceFirstMove = ((Rook)p1).hasMoved();
                    lastMoveFirst = false;
                    targetLastMoveFirst = false;
                }
                else if(p instanceof Pawn && p1 instanceof Rook){
                    currPieceNumMove = ((Pawn)p).numberOfMoves();
                    targetPieceNumMove = 0;
                    currPieceFirstMove = ((Pawn)p).hasMoved();
                    targetPieceFirstMove = ((Rook)p1).hasMoved();
                    lastMoveFirst = ((Pawn)p).wasLastMoveFirstMove();
                    targetLastMoveFirst = false;
                }
                else if(p instanceof Rook && p1 instanceof Pawn){
                    currPieceNumMove = 0;
                    targetPieceNumMove = ((Pawn)p1).numberOfMoves();
                    currPieceFirstMove = ((Rook)p).hasMoved();
                    targetPieceFirstMove = ((Pawn)p1).hasMoved();
                    lastMoveFirst = false;
                    targetLastMoveFirst = ((Pawn)p1).wasLastMoveFirstMove();
                }
                else if(p instanceof Pawn) {
                    currPieceNumMove = ((Pawn)p).numberOfMoves();
                    targetPieceNumMove = 0;
                    currPieceFirstMove = ((Pawn)p).hasMoved();
                    targetPieceFirstMove = false;
                    lastMoveFirst = ((Pawn)p).wasLastMoveFirstMove();
                    targetLastMoveFirst = false;
                }
                else if (p instanceof  Rook){
                    currPieceNumMove = 0;
                    targetPieceNumMove = 0;
                    currPieceFirstMove = ((Rook)p).hasMoved();
                    targetPieceFirstMove = false;
                    lastMoveFirst = false;
                    targetLastMoveFirst = false;
                }
                else if(p1 instanceof Pawn) {
                    currPieceNumMove = 0;
                    targetPieceNumMove = ((Pawn)p1).numberOfMoves();
                    currPieceFirstMove = false;
                    targetPieceFirstMove = ((Pawn)p1).hasMoved();
                    lastMoveFirst = false;
                    targetLastMoveFirst = ((Pawn)p1).wasLastMoveFirstMove();
                }
                else if (p1 instanceof  Rook){
                    currPieceNumMove = 0;
                    targetPieceNumMove = 0;
                    currPieceFirstMove = false;
                    targetPieceFirstMove = ((Rook)p1).hasMoved();
                    lastMoveFirst = false;
                    targetLastMoveFirst = false;
                }
                else{
                    currPieceNumMove = 0;
                    targetPieceNumMove = 0;
                    currPieceFirstMove = false;
                    targetPieceFirstMove = false;
                    lastMoveFirst = false;
                    targetLastMoveFirst = false;
                }
            }
            System.out.println("position: " + oldRow + "\t" + oldCol + "\t" + newRow + "\t" + newCol);
            if(!chess.handleInput(oldRow, oldCol, newRow, newCol, upgradeTo)){
                if(displayError) Toast.makeText(context, Chess.ILLEGALMOVEERROR, Toast.LENGTH_SHORT).show();
                return false;
            }else{
                Piece newP = getPiece(newRow, newCol);
                newPiece = newP.toString();

                if(!newPiece.equals(oldPiece)){
                    upgrade = true;
                }
                //enable undo and confirm button
                //TODO: we also need to first check if the piece moved
                //was a pawn and if it reached the opposite side, and
                //what upgrade the player want
                //record this move
                if(p1 == null) {
                    System.out.println(currPieceNumMove + " " + targetPieceNumMove + " " + currPieceFirstMove + " "  + targetPieceFirstMove);
                    chess.RecordMove(new Move(playerName,
                            pieceName,
                            null,
                            oldRow,
                            oldCol,
                            newRow,
                            newCol,
                            currPieceNumMove,
                            targetPieceNumMove,
                            upgrade,
                            upgradeTo,
                            currPieceFirstMove,
                            targetPieceFirstMove,
                            lastMoveFirst,
                            targetLastMoveFirst,
                            takenRow,
                            takenCol));
                }
                else{
                    System.out.println(currPieceNumMove + " " + targetPieceNumMove + " " + currPieceFirstMove + " "  + targetPieceFirstMove);
                    chess.RecordMove(new Move(playerName,
                            pieceName,
                            p1.toString(),
                            oldRow,
                            oldCol,
                            newRow,
                            newCol,
                            currPieceNumMove,
                            targetPieceNumMove,
                            upgrade,
                            upgradeTo,
                            currPieceFirstMove,
                            targetPieceFirstMove,
                            lastMoveFirst,
                            targetLastMoveFirst,
                            takenRow,
                            takenCol));
                }
                if(chess.gameOver){
                    ChessActivity.playerTurnText.setText(chess.printWinner());
                    ChessActivity.undoMoveBtn.setVisibility(View.INVISIBLE);
                    ChessActivity.confirmMoveBtn.setVisibility(View.INVISIBLE);
                    ChessActivity.aiBtn.setVisibility(View.INVISIBLE);
                    ChessActivity.drawBtn.setVisibility(View.INVISIBLE);
                    ChessActivity.resignBtn.setVisibility(View.INVISIBLE);

                    ChessActivity.recordBtn.setVisibility(View.VISIBLE);
                    ChessActivity.mainMenuBtn.setVisibility(View.VISIBLE);
                }
                ChessView.allowMove = false;
                ChessActivity.undoMoveBtn.setEnabled(true);
                ChessActivity.confirmMoveBtn.setEnabled(true);
                ChessActivity.aiBtn.setEnabled(false);
                return true;
            }
        }
        return false;
    }
    public boolean PerformMoveUpgrade(char upgrade, Chess chess, Context context){
        return PerformMove(oldRow, oldCol, newRow, newCol, chess, context, true, upgrade);
    }
    /**
     * Handle on touch event
     * @param chess chess logic
     * @param context context requesting touch event
     * @param view view requesting touch event
     * @param event event occurred with this touch event
     * @return
     */
    public boolean onTouchEvent(Chess chess, Context context, View view, MotionEvent event){
        float xPos = event.getX();
        float yPos = event.getY();
        boolean res = false;
        System.out.println( "Last row: "+ currLastMoveLoc.row + ", Last col: " + currLastMoveLoc.col);
        System.out.println( "Final row: "+ currFinalMoveLoc.row + ", Final col: " + currFinalMoveLoc.col);

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                res = touchEvent(xPos, yPos);
                int oldRow = currLastMoveLoc.row;
                int oldCol = currLastMoveLoc.col;
                int newRow = currFinalMoveLoc.row;
                int newCol = currFinalMoveLoc.col;

                if(res){
                    System.out.println("hit on tile <" + oldRow + ", " + oldCol + ">");
                    if(newRow >= 0 && newCol >= 0 && oldRow >= 0 && oldCol >= 0){
                        //before we perform move we need to prompt user for a upgrade
                        Piece p = getPiece(oldRow, oldCol);
                        if(p instanceof Pawn){
                            String p_player = p.getPlayer();
                            boolean promptUserUpgrade = false;
                            if(p_player.equals("w")){
                                if(newRow == ROW - 1){
                                    promptUserUpgrade = true;
                                }
                            }else{
                                if(newRow == 0){
                                    promptUserUpgrade = true;
                                }
                            }
                            if(promptUserUpgrade){
                                view.setVisibility(View.INVISIBLE);
                                ChessActivity.aiBtn.setVisibility(View.INVISIBLE);
                                ChessActivity.drawBtn.setVisibility(View.INVISIBLE);
                                ChessActivity.resignBtn.setVisibility(View.INVISIBLE);
                                ChessActivity.confirmMoveBtn.setVisibility(View.INVISIBLE);
                                ChessActivity.undoMoveBtn.setVisibility(View.INVISIBLE);

                                ChessActivity.queenUpgradeBtn.setVisibility(View.VISIBLE);
                                ChessActivity.knightUpgradeBtn.setVisibility(View.VISIBLE);
                                ChessActivity.bishopUpgradeBtn.setVisibility(View.VISIBLE);
                                ChessActivity.rookUpgradeBtn.setVisibility(View.VISIBLE);
                                ChessActivity.upgradeText.setVisibility(View.VISIBLE);

                                this.oldRow = oldRow;
                                this.oldCol = oldCol;
                                this.newRow = newRow;
                                this.newCol = newCol;
                                currLastMoveLoc.col = -1;
                                currLastMoveLoc.row = -1;
                                currFinalMoveLoc.col = -1;
                                currFinalMoveLoc.row = -1;
                                return true;
                            }
                        }

                        PerformMove(oldRow,oldCol, newRow, newCol, chess, context, true, 'Q');
                        currLastMoveLoc.col = -1;
                        currLastMoveLoc.row = -1;
                        currFinalMoveLoc.col = -1;
                        currFinalMoveLoc.row = -1;
                        //force re-draw
                        view.invalidate();
                    }
                }else{
                    if(currLastMoveLoc.col >= 0 || currLastMoveLoc.row >= 0){
                        Toast.makeText(context, Chess.ILLEGALMOVEERROR, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                //we lost touch contact just reset everything
                currLastMoveLoc.col = -1;
                currLastMoveLoc.row = -1;
                currFinalMoveLoc.col = -1;
                currFinalMoveLoc.row = -1;
                view.invalidate();
                break;
            default:
                break;
        }

        return res;
    }

    /**
     * Touch event helper
     * @param posX
     * @param posY
     * @return
     */
    private boolean touchEvent(float posX, float posY){
        for(int r = 0 ; r < ROW; r++){
            for(int c = 0; c < COLUMN; c++){
               Tile tile = board[r][c];
               if(tile == null) continue;
               //check if position hit a tile
               if(tile.inCell(posX, posY)){
                   if(currLastMoveLoc.col < 0 || currLastMoveLoc.row < 0){
                       currLastMoveLoc.col = c;
                       currLastMoveLoc.row = r;
                       return true;
                   }
                   currFinalMoveLoc.col = c;
                   currFinalMoveLoc.row = r;
                   return true;
               }
            }
        }
        //reset if position not inside the board
        currLastMoveLoc.col = -1;
        currLastMoveLoc.row = -1;
        currFinalMoveLoc.col = -1;
        currFinalMoveLoc.row = -1;
        //toaster notify invalid move
        return false;
    }

    /**
     * Draw the border outline of the board
     * @param canvas
     */
    private void DrawBoardOutLine(Canvas canvas){
        //bottom line
        canvas.drawLine(marginX, marginY, marginX + boardSize, marginY, outlinePaint);
        //left line
        canvas.drawLine(marginX, marginY, marginX, marginY + boardSize, outlinePaint);
        //top line
        canvas.drawLine(marginX, marginY + boardSize, marginX + boardSize, marginY + boardSize, outlinePaint);
        //right line
        canvas.drawLine(marginX + boardSize, marginY, marginX + boardSize, marginY + boardSize, outlinePaint);
    }

    /**
     * Draw a piece located on the given tile.
     * @param tile
     */
    private void DrawPiece(Canvas canvas, Tile tile){
        if(tile.GetPiece() == null) return;
        tile.GetPiece().draw(canvas, tile.RelativeScreenY(), tile.RelativeScreenX(), 0.08f);
    }
}
