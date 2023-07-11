package com.example.chess58;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Move implements Parcelable, Serializable {
    private String pieceName, playerName, targetPieceTaken;
    private int pieceID, startingSpotRank, startingSpotFile, targetSpotRank, targetSpotFile, takenPieceRank = -1, takenPieceFile = -1;
    public int targetPieceNumMove, pieceNumMove;
    public boolean upgrade;
    public boolean pieceFirstMove, targetFirstMove;
    public boolean lastMoveWasFirst, targetLastMoveWasFirst;
    public char upgradeTo;

    /**
     * constructor
     * @param playerName
     * @param pieceName
     * @param targetPieceTaken
     * @param startingSpotRank
     * @param startingSpotFile
     * @param targetSpotRank
     * @param targetSpotFile
     * @param pieceNumMove
     * @param targetPieceNumMove
     * @param upgrade
     * @param upgradeTo
     * @param pieceFirstMove
     * @param targetPieceFirstMove
     * @param lastMoveWasFirst
     * @param targetLastMoveWasFirst
     */
    public Move(String playerName,
                String pieceName,
                String targetPieceTaken,
                int startingSpotRank,
                int startingSpotFile,
                int targetSpotRank,
                int targetSpotFile,
                int pieceNumMove,
                int targetPieceNumMove,
                boolean upgrade,
                char upgradeTo,
                boolean pieceFirstMove,
                boolean targetPieceFirstMove,
                boolean lastMoveWasFirst,
                boolean targetLastMoveWasFirst,
                int takenPieceRank,
                int takenPieceFile){
        this.playerName = playerName;
        this.pieceName = pieceName;
        this.targetPieceTaken = targetPieceTaken;
        this.startingSpotRank = startingSpotRank;
        this.startingSpotFile = startingSpotFile;
        this.targetSpotRank = targetSpotRank;
        this.targetSpotFile = targetSpotFile;
        this.pieceFirstMove =pieceFirstMove;
        this.targetFirstMove = targetPieceFirstMove;
        this.upgrade = upgrade;
        this.upgradeTo = upgradeTo;
        this.pieceNumMove = pieceNumMove;
        this.targetPieceNumMove = targetPieceNumMove;
        this.lastMoveWasFirst = lastMoveWasFirst;
        this.targetLastMoveWasFirst = targetLastMoveWasFirst;
        this.takenPieceRank = takenPieceRank;
        this.takenPieceFile = takenPieceFile;
    }

    protected Move(Parcel in) {
        pieceName = in.readString();
        playerName = in.readString();
        targetPieceTaken = in.readString();
        pieceID = in.readInt();
        startingSpotRank = in.readInt();
        startingSpotFile = in.readInt();
        targetSpotRank = in.readInt();
        targetSpotFile = in.readInt();
        targetPieceNumMove = in.readInt();
        pieceNumMove = in.readInt();
        upgrade = in.readByte() != 0;
        pieceFirstMove = in.readByte() != 0;
        targetFirstMove = in.readByte() != 0;
        lastMoveWasFirst = in.readByte() != 0;
        targetLastMoveWasFirst = in.readByte() != 0;
        upgradeTo = (char) in.readInt();
    }

    public static final Creator<Move> CREATOR = new Creator<Move>() {
        @Override
        public Move createFromParcel(Parcel in) {
            return new Move(in);
        }

        @Override
        public Move[] newArray(int size) {
            return new Move[size];
        }
    };

    /** Return player's name
     * @return the player's name
     */
    public String GetPlayerName(){
        return playerName;
    }
    /** Return piece's name
     * @return the piece's name
     */
    public String GetPieceName(){
        return pieceName;
    }
    /**
     * Return the target piece taken caused by the move, if any
     * @return a target piece's name, null if none
     */
    public String GetTargetPieceName(){ return targetPieceTaken; }
    /** Return piece's ID
     * @return the piece's ID
     */
    public int GetPieceID(){
        return pieceID;
    }
    /** Return piece's starting spot rank
     * @return the piece's starting rank
     */
    public int GetStartingSpotRank(){
        return startingSpotRank;
    }
    /** Return piece's starting spot File
     * @return the piece's starting file
     */
    public int GetStartingSpotFile(){
        return startingSpotFile;
    }
    /** Return piece's target spot rank
     * @return the piece's target rank
     */
    public int GetTargetSpotRank(){
        return targetSpotRank;
    }
    /** Return piece's target spot file
     * @return the piece's target file
     */
    public int GetTargetSpotFile(){
        return targetSpotFile;
    }

    /**
     * return the piece taken's rank
     * @return
     */
    public int GetTakenPieceRank(){return takenPieceRank;}

    /**
     * return the piece taken's file
     * @return
     */
    public int GetTakenPieceFile(){return takenPieceFile;}
    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pieceName);
        dest.writeString(playerName);
        dest.writeString(targetPieceTaken);
        dest.writeInt(pieceID);
        dest.writeInt(startingSpotRank);
        dest.writeInt(startingSpotFile);
        dest.writeInt(targetSpotRank);
        dest.writeInt(targetSpotFile);
        dest.writeInt(targetPieceNumMove);
        dest.writeInt(pieceNumMove);
        dest.writeByte((byte) (upgrade ? 1 : 0));
        dest.writeByte((byte) (pieceFirstMove ? 1 : 0));
        dest.writeByte((byte) (targetFirstMove ? 1 : 0));
        dest.writeByte((byte) (lastMoveWasFirst ? 1 : 0));
        dest.writeByte((byte) (targetLastMoveWasFirst ? 1 : 0));
        dest.writeInt((int) upgradeTo);
    }
}
