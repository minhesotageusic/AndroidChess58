package com.example.chess58;

import android.graphics.Color;
import android.os.Bundle;

import com.example.chess58.Pieces.Bishop;
import com.example.chess58.Pieces.King;
import com.example.chess58.Pieces.Knight;
import com.example.chess58.Pieces.Pawn;
import com.example.chess58.Pieces.Piece;
import com.example.chess58.Pieces.Queen;
import com.example.chess58.Pieces.Rook;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.ReferenceQueue;

public class Playback_play extends AppCompatActivity {

    /**
     * recording of the specific game
     */
    private Recording recording;
    /**
     * the chessboard view
     */
    private ChessView chessboard;
    /**
     * button to invoke the previous move
     */
    private Button preMove;
    /**
     * button to invoke the next move
     */
    private Button nextMove;
    /**
     * player's turn
     */
    private TextView playerTurnText;
    /**
     * move counter
     */
    private int moveID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback_play);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // activate the up nav button
        // I don't know why this isn't working......!
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // get the information from the bundle
        Bundle bundle = getIntent().getExtras();
        recording = bundle.getParcelable("recording");

        // link all the attributes
        chessboard = (ChessView)findViewById(R.id.chessboards);
        preMove = findViewById(R.id.prevMove);
        nextMove = findViewById(R.id.nextMove);
        playerTurnText = findViewById(R.id.playerTurnTexts);

        if (recording.Size() == 0) finish();
//        System.out.println(chessboard.chess);
        playerTurnText.setText(chessboard.chess.whosTurn());
        chessboard.allowMove = false;
        preMove.setVisibility(View.INVISIBLE);

        // set listener on buttons of preMove and nextMove
//        preMove.setOnClickListener((v)-> prev_move(v));
        nextMove.setOnClickListener((v)-> next_move(v));
    }

    /**
     * display(undo) the  move made by a player
     * @param v button listener
     */
    private void prev_move (View v) {
        // decrement move counter
        this.moveID--;
        if (this.moveID == 0) {
            v.setEnabled(false);
        }
        nextMove.setEnabled(true);


        // get the move at index moveID
        Move move = recording.GetMoveAt(this.moveID);

        // get the information needed for a move
        int finalFile = move.GetStartingSpotFile(), finalRank = move.GetStartingSpotRank();   // basically undo the move which has just been made
        int currentFile = move.GetTargetSpotFile(), currentRank = move.GetTargetSpotRank();
        int takenPieceFile = move.GetTakenPieceFile(), takenPieceRank = move.GetTakenPieceRank(); // the coordinates of the piece that's been taken

        // get enemy's name
        String enemyName = move.GetPlayerName().equals("w")? "b": "w";

        // get the chessboard
        ChessBoard board = chessboard.chess.GetBoard();
        // make the move on the board
        board.addPiece(finalRank, finalFile, board.removePiece(currentRank, currentFile));
        // check if any enemy piece got taken during the move
        String targetPieceName = move.GetTargetPieceName();
        if ( targetPieceName != null ||(takenPieceFile != -1 && takenPieceRank != -1))  {  // require piece's type and its coordinates
            // get the row and column of the taken piece for code re-use
            int row, col;
            if (takenPieceFile == -1) {
                row = currentRank;
                col = currentFile;
            } else {
                row = takenPieceRank;
                col = takenPieceFile;
            }
            // recover the taken piece back on the chessboard
            addPieceOnBoard(targetPieceName, row, col, enemyName);
        }

        chessboard.chess.nextPlayerTurn();
        playerTurnText.setText(chessboard.chess.whosTurn());
        chessboard.invalidate();

    }
    private void addPieceOnBoard (String targetPieceName, int row, int col, String playerName) {
        ChessBoard board = chessboard.chess.GetBoard();
        int color = playerName.equals("w")? Color.WHITE:Color.BLACK;
        boolean moveUp = playerName.equals("w")? true: false;  // for pawn to move up or down
        switch (targetPieceName) {
            case "Q":
                board.addPiece(row, col, new Queen(chessboard.getContext(), playerName, color));
                break;
            case "N":
                board.addPiece(row, col, new Knight(chessboard.getContext(), playerName, color));
                break;
            case "p":
                board.addPiece(row, col, new Pawn(chessboard.getContext(), playerName, color, moveUp));
                break;
            case "R":
                board.addPiece(row, col, new Rook(chessboard.getContext(), playerName, color));
                break;
            case "B":
                board.addPiece(row, col, new Bishop(chessboard.getContext(), playerName, color));
                break;
            default:
                board.addPiece(row, col, new King(chessboard.getContext(), playerName, color));
                break;
        }
    }

    /**
     * display the next move made by a player
     * @param v button listener
     */
    private void next_move (View v) {
        // show and update the player
        playerTurnText.setText(chessboard.chess.whosTurn());
        // get the move at index moveID
        Move move = recording.GetMoveAt(this.moveID);
        if (this.moveID+1 >= recording.Size()) {
            v.setEnabled(false);
            String winner = move.GetPlayerName().equals("w")? "White wins!":"Black wins!";
            playerTurnText.setText(winner);  // display the winner since it is the last move
        }

        // get the information needed for a move
        int currentFile = move.GetStartingSpotFile(), currentRank = move.GetStartingSpotRank();
        int finalFile = move.GetTargetSpotFile(), finalRank = move.GetTargetSpotRank();
        int takenPieceFile = move.GetTakenPieceFile(), takenPieceRank = move.GetTakenPieceRank();

        // get the chessboard
        ChessBoard board = chessboard.chess.GetBoard();
        // it is an invalid move since no piece has been selected  - normally, it would NOT happen
        if (board.getPiece(currentRank, currentFile) == null) return;

        // determine whether there is a piece that has been taken during the move
        if (board.getPiece(finalRank, finalFile) != null) {
            board.removePiece(finalRank, finalFile);
        } else if (takenPieceFile != -1 && takenPieceRank != -1) {
            board.removePiece(takenPieceRank, takenPieceFile);
        }
        // check if pawn upgrading exsits, or else move the piece over to destination
        Piece currentPiece = board.removePiece(currentRank, currentFile);
        if ((currentPiece instanceof Pawn) && move.upgrade) addPieceOnBoard(""+move.upgradeTo, finalRank, finalFile, move.GetPlayerName());
        else board.addPiece(finalRank, finalFile, currentPiece);

        chessboard.chess.nextPlayerTurn();
        // surface the movement on canvas
        chessboard.invalidate();
        // increment the move counter
        this.moveID++;
    }
}