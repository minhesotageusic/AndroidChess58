package com.example.chess58;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chess58.Pieces.Bishop;
import com.example.chess58.Pieces.Knight;
import com.example.chess58.Pieces.Pawn;
import com.example.chess58.Pieces.Piece;
import com.example.chess58.Pieces.Queen;
import com.example.chess58.Pieces.Rook;

public class ChessActivity extends AppCompatActivity {

    public static Button confirmMoveBtn, undoMoveBtn, recordBtn,
            resignBtn, drawBtn, acceptDrawBtn, aiBtn, mainMenuBtn,
            queenUpgradeBtn, bishopUpgradeBtn, knightUpgradeBtn, rookUpgradeBtn;
    public static TextView playerTurnText, upgradeText;
    public ChessView chessBoardView;

    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_chess);

        chessBoardView = (ChessView) findViewById(R.id.chessboard);
        playerTurnText = findViewById(R.id.playerTurnText);
        confirmMoveBtn = findViewById(R.id.confirmMoveBtn);
        undoMoveBtn = findViewById(R.id.undoMoveBtn);
        recordBtn = findViewById(R.id.recordBtn);
        resignBtn = findViewById(R.id.resignBtn);
        drawBtn = findViewById(R.id.drawBtn);
        acceptDrawBtn = findViewById(R.id.acceptDrawBtn);
        aiBtn = findViewById(R.id.aiBtn);
        mainMenuBtn = findViewById(R.id.mainMenuBtn);
        queenUpgradeBtn = findViewById(R.id.queenUpgradeBtn);
        bishopUpgradeBtn = findViewById(R.id.bishopUpgradeBtn);
        knightUpgradeBtn = findViewById(R.id.knighUpgradeBtn);
        rookUpgradeBtn = findViewById(R.id.rookUpgradeBtn);
        upgradeText = findViewById(R.id.upgradeText);

        playerTurnText.setText(chessBoardView.chess.whosTurn());

        confirmMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmMove(v);
            }
        });
        undoMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                UndoMove(v);
            }
        });
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Record(v);
            }
        });
        resignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resign(v);
            }
        });
        aiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AI(v);
            }
        });
        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestDraw(v);
            }
        });
        acceptDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptDraw(v);
            }
        });
        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu(v);
            }
        });
        knightUpgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KnightUpgrade(v);
            }
        });
        rookUpgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RookUpgrade(v);
            }
        });
        bishopUpgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BishopUpgrade(v);
            }
        });
        queenUpgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueenUpgrade(v);
            }
        });
        //disable both buttons
        confirmMoveBtn.setEnabled(false);
        undoMoveBtn.setEnabled(false);
        mainMenuBtn.setVisibility(View.INVISIBLE);
        recordBtn.setVisibility(View.INVISIBLE);
        acceptDrawBtn.setVisibility(View.INVISIBLE);
        queenUpgradeBtn.setVisibility(View.INVISIBLE);
        bishopUpgradeBtn.setVisibility(View.INVISIBLE);
        knightUpgradeBtn.setVisibility(View.INVISIBLE);
        rookUpgradeBtn.setVisibility(View.INVISIBLE);
        upgradeText.setVisibility(View.INVISIBLE);
    }
    public void KnightUpgrade(View v){
        chessBoardView.chess.GetBoard().PerformMoveUpgrade('N', chessBoardView.chess, getApplicationContext());

        confirmMoveBtn.setEnabled(true);
        undoMoveBtn.setEnabled(true);
        aiBtn.setEnabled(false);

        chessBoardView.setVisibility(View.VISIBLE);
        ChessActivity.aiBtn.setVisibility(View.VISIBLE);
        ChessActivity.drawBtn.setVisibility(View.VISIBLE);
        ChessActivity.resignBtn.setVisibility(View.VISIBLE);
        ChessActivity.confirmMoveBtn.setVisibility(View.VISIBLE);
        ChessActivity.undoMoveBtn.setVisibility(View.VISIBLE);

        queenUpgradeBtn.setVisibility(View.INVISIBLE);
        bishopUpgradeBtn.setVisibility(View.INVISIBLE);
        knightUpgradeBtn.setVisibility(View.INVISIBLE);
        rookUpgradeBtn.setVisibility(View.INVISIBLE);
        upgradeText.setVisibility(View.INVISIBLE);
    }
    public void QueenUpgrade(View v){
        chessBoardView.chess.GetBoard().PerformMoveUpgrade('Q', chessBoardView.chess, getApplicationContext());

        confirmMoveBtn.setEnabled(true);
        undoMoveBtn.setEnabled(true);
        aiBtn.setEnabled(false);

        chessBoardView.setVisibility(View.VISIBLE);
        ChessActivity.aiBtn.setVisibility(View.VISIBLE);
        ChessActivity.drawBtn.setVisibility(View.VISIBLE);
        ChessActivity.resignBtn.setVisibility(View.VISIBLE);
        ChessActivity.confirmMoveBtn.setVisibility(View.VISIBLE);
        ChessActivity.undoMoveBtn.setVisibility(View.VISIBLE);

        queenUpgradeBtn.setVisibility(View.INVISIBLE);
        bishopUpgradeBtn.setVisibility(View.INVISIBLE);
        knightUpgradeBtn.setVisibility(View.INVISIBLE);
        rookUpgradeBtn.setVisibility(View.INVISIBLE);
        upgradeText.setVisibility(View.INVISIBLE);
    }
    public void BishopUpgrade(View v){
        chessBoardView.chess.GetBoard().PerformMoveUpgrade('B', chessBoardView.chess, getApplicationContext());

        confirmMoveBtn.setEnabled(true);
        undoMoveBtn.setEnabled(true);
        aiBtn.setEnabled(false);

        chessBoardView.setVisibility(View.VISIBLE);
        ChessActivity.aiBtn.setVisibility(View.VISIBLE);
        ChessActivity.drawBtn.setVisibility(View.VISIBLE);
        ChessActivity.resignBtn.setVisibility(View.VISIBLE);
        ChessActivity.confirmMoveBtn.setVisibility(View.VISIBLE);
        ChessActivity.undoMoveBtn.setVisibility(View.VISIBLE);

        queenUpgradeBtn.setVisibility(View.INVISIBLE);
        bishopUpgradeBtn.setVisibility(View.INVISIBLE);
        knightUpgradeBtn.setVisibility(View.INVISIBLE);
        rookUpgradeBtn.setVisibility(View.INVISIBLE);
        upgradeText.setVisibility(View.INVISIBLE);
    }
    public void RookUpgrade(View v){
        chessBoardView.chess.GetBoard().PerformMoveUpgrade('R', chessBoardView.chess, getApplicationContext());

        confirmMoveBtn.setEnabled(true);
        undoMoveBtn.setEnabled(true);
        aiBtn.setEnabled(false);

        chessBoardView.setVisibility(View.VISIBLE);
        ChessActivity.aiBtn.setVisibility(View.VISIBLE);
        ChessActivity.drawBtn.setVisibility(View.VISIBLE);
        ChessActivity.resignBtn.setVisibility(View.VISIBLE);
        ChessActivity.confirmMoveBtn.setVisibility(View.VISIBLE);
        ChessActivity.undoMoveBtn.setVisibility(View.VISIBLE);

        queenUpgradeBtn.setVisibility(View.INVISIBLE);
        bishopUpgradeBtn.setVisibility(View.INVISIBLE);
        knightUpgradeBtn.setVisibility(View.INVISIBLE);
        rookUpgradeBtn.setVisibility(View.INVISIBLE);
        upgradeText.setVisibility(View.INVISIBLE);
    }
    public void MainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * Resign from the game
     * @param view
     */
    public void Resign(View view){
        //game over
        chessBoardView.chess.Resign();
        chessBoardView.allowMove = false;

        String whoWon = chessBoardView.chess.printWinner();
        playerTurnText.setText(whoWon);

        confirmMoveBtn.setVisibility(View.INVISIBLE);
        undoMoveBtn.setVisibility(View.INVISIBLE);
        resignBtn.setVisibility(View.INVISIBLE);
        drawBtn.setVisibility(View.INVISIBLE);
        acceptDrawBtn.setVisibility(View.INVISIBLE);
        aiBtn.setVisibility(View.INVISIBLE);

        recordBtn.setVisibility(View.VISIBLE);
        mainMenuBtn.setVisibility(View.VISIBLE);
    }

    /**
     * invoke ai to move
     * @param view
     */
    public void AI(View view){
        if(!chessBoardView.chess.InvokeAI(chessBoardView, getApplicationContext())){
            ChessActivity.aiBtn.setEnabled(true);
            ChessView.allowMove = true;
            ChessActivity.undoMoveBtn.setEnabled(false);
            ChessActivity.confirmMoveBtn.setEnabled(false);
        }
    }

    /**
     * Accept draw
     * @param view
     */
    public void AcceptDraw(View view){

    }

    /**
     * Request next player to draw
     * @param view
     */
    public void RequestDraw(View view){
        chessBoardView.allowMove = false;
        confirmMoveBtn.setVisibility(View.INVISIBLE);
        undoMoveBtn.setVisibility(View.INVISIBLE);
        resignBtn.setVisibility(View.INVISIBLE);
        drawBtn.setVisibility(View.INVISIBLE);
        recordBtn.setVisibility(View.INVISIBLE);
        mainMenuBtn.setVisibility(View.INVISIBLE);
        aiBtn.setVisibility(View.INVISIBLE);

        acceptDrawBtn.setVisibility(View.VISIBLE);
        //end game
        //do we offer the player to record?
        //go back to main menu
        acceptDrawBtn.setVisibility(View.INVISIBLE);

        recordBtn.setVisibility(View.VISIBLE);
        mainMenuBtn.setVisibility(View.VISIBLE);
    }
    /**
     * Open record activity
     * @param view
     */
    public void Record(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable("recording", chessBoardView.chess.getRecording());
        Intent intent = new Intent(this, SavePrompt.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * Undo the previous move
     * @param view view for this function
     */
    public void UndoMove(View view){
        //1st remove last move from recording
        //2nd undo previous move on board
        //3rd disable undo btn and confirm btn
        Move lastMove = chessBoardView.chess.RemoveLastMove();
        if(lastMove != null) {
            Piece enemyPiece = null;
            Piece currPiece = null;
            Pair startingLoc = null;
            Pair targetLoc = null;
            String targetPieceTaken = lastMove.GetTargetPieceName();
            String currentPiece = lastMove.GetPieceName();
            String currPlayer = lastMove.GetPlayerName();
            String enemyPlayer = "w";
            int enemyPlayerColor = Color.WHITE;
            int currPlayerColor = Color.BLACK;
            int enemyNumMoves = lastMove.targetPieceNumMove;
            int currNumMoves = lastMove.pieceNumMove;
            int takenPieceRow = lastMove.GetTakenPieceRank();
            int takenPieceCol = lastMove.GetTakenPieceFile();
            boolean upgrade = lastMove.upgrade;
            boolean currPieceFirstMove = lastMove.pieceFirstMove;
            boolean targetPieceFirstMove = lastMove.targetFirstMove;
            boolean lastMoveWasFirst = lastMove.lastMoveWasFirst;
            boolean targetLastMoveWasFirst = lastMove.targetLastMoveWasFirst;
            char upgradeTo = lastMove.upgradeTo;

            startingLoc = new Pair(lastMove.GetStartingSpotRank(), lastMove.GetStartingSpotFile());
            targetLoc = new Pair(lastMove.GetTargetSpotRank(), lastMove.GetTargetSpotFile());

            if(currPlayer.equals("w")) {
                enemyPlayerColor = Color.BLACK;
                enemyPlayer = "b";
                currPlayerColor = Color.WHITE;
            }

            //last moved removed an opposite piece
            //that we need to put back
            if(targetPieceTaken != null){
                if (targetPieceTaken.equals("p")) {         //pawn
                    if(enemyPlayerColor == Color.WHITE) enemyPiece = new Pawn(getApplicationContext(), enemyPlayer, enemyPlayerColor, true);
                    else enemyPiece = new Pawn(getApplicationContext(), enemyPlayer, enemyPlayerColor, false);
                    ((Pawn)enemyPiece).setFirstMove(targetPieceFirstMove);
                    ((Pawn)enemyPiece).setNumOfMoves(enemyNumMoves);
                    ((Pawn)enemyPiece).setLastMoveWasFirstMove(targetLastMoveWasFirst);
                }
                else if(targetPieceTaken.equals("R")){      //rook
                    enemyPiece = new Rook(getApplicationContext(), enemyPlayer, enemyPlayerColor);
                    ((Rook)enemyPiece).setFirstMove(targetPieceFirstMove);
                }
                else if(targetPieceTaken.equals("N")){      //knight
                    enemyPiece = new Knight(getApplicationContext(), enemyPlayer, enemyPlayerColor);
                }
                else if(targetPieceTaken.equals("B")){      //bishop
                    enemyPiece = new Bishop(getApplicationContext(), enemyPlayer, enemyPlayerColor);
                }
                else {      //queen
                    enemyPiece = new Queen(getApplicationContext(), enemyPlayer, enemyPlayerColor);
                }
            }
            //move was not made on an upgraded piece
            if( !upgrade ) {
                currPiece = chessBoardView.chess.GetBoard().removePiece(targetLoc.row, targetLoc.col);
                if(currPiece instanceof Pawn){
                    System.out.println("here " + currPieceFirstMove + " "  + targetPieceFirstMove);
                    ((Pawn)currPiece).setNumOfMoves(currNumMoves);
                    ((Pawn)currPiece).setFirstMove(currPieceFirstMove);
                    ((Pawn)currPiece).setLastMoveWasFirstMove(lastMoveWasFirst);
                }else if(currPiece instanceof Rook){
                    ((Rook)currPiece).setFirstMove(currPieceFirstMove);
                }
            }
            //move was made on upgraded piece
            //restore piece to a pawn
            else {
                chessBoardView.chess.GetBoard().removePiece(targetLoc.row, targetLoc.col);
                if(currPlayerColor == Color.WHITE) currPiece = new Pawn(getApplicationContext(), currPlayer, currPlayerColor, true);
                else currPiece = new Pawn(getApplicationContext(), currPlayer, currPlayerColor, false);
                ((Pawn) currPiece).setFirstMove(false);
                ((Pawn) currPiece).setLastMoveWasFirstMove(false);
                ((Pawn) currPiece).setNumOfMoves(currNumMoves);
            }
            chessBoardView.chess.GetBoard().addPiece(startingLoc.row, startingLoc.col, currPiece);
            if(currPiece instanceof Pawn){
                System.out.println("pawn: " + ((Pawn)currPiece).wasLastMoveFirstMove());
            }
            if( enemyPiece != null ){
                if(takenPieceRow == -1 && takenPieceCol == -1){
                    chessBoardView.chess.GetBoard().addPiece(targetLoc.row, targetLoc.col, enemyPiece);
                }else{
                    chessBoardView.chess.GetBoard().addPiece(takenPieceRow, takenPieceCol, enemyPiece);
                }
            }
        }
        chessBoardView.allowMove = true;
        confirmMoveBtn.setEnabled(false);
        undoMoveBtn.setEnabled(false);
        chessBoardView.invalidate();
    }

    /**
     * Confirm current move and go to next player's turn
     * @param view view for this function
     */
    public void ConfirmMove(View view){
        if(chessBoardView == null) return;
        if(chessBoardView.chess == null) return;
        //1st go to next player's turn
        //2nd change playerTurnText to reflect that
        //3rd disable undo and confirm button
        chessBoardView.allowMove = true;
        chessBoardView.chess.nextPlayerTurn();
        playerTurnText.setText(chessBoardView.chess.whosTurn());
        confirmMoveBtn.setEnabled(false);
        undoMoveBtn.setEnabled(false);
        aiBtn.setEnabled(true);
    }

}
