package com.example.chess58;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recording implements Parcelable , Serializable{
    private String title;
    private String date;
    private ArrayList<Move> moves;

    /**
     * constructor
     */
    public Recording(){
        moves = new ArrayList<Move>();
    }

    protected Recording(Parcel in) {
        title = in.readString();
        moves = in.readArrayList(Move.class.getClassLoader());
    }

    public static final Creator<Recording> CREATOR = new Creator<Recording>() {
        @Override
        public Recording createFromParcel(Parcel in) {
            return new Recording(in);
        }

        @Override
        public Recording[] newArray(int size) {
            return new Recording[size];
        }
    };

    /**
     * Set title
     * @param title target title
     */
    public void SetTitle(String title){
        this.title = title;
    }

    /**
     * Set date
     * @param date target date
     */
    public void SetDate(String date){
        this.date = date;
    }

    /**
     * get title
     *
     * @return return recording's title
     */
    public String getTitle () {
        return this.title;
    }

    /**
     * get date
     * @return return recording's date
     */
    public String getDate(){
        return date;
    }
    /** Record a given move
     * @param move move to record
     */
    public void RecordMove(Move move){
        moves.add(move);
    }
    /** Remove a given move from the recording
     * @param move move to remove
     */
    public void RemoveMove(Move move){
        moves.remove(move);
    }

    /** Remove a given move from the recording
     * @param index move's index to be remove
     * @return the move that was removed
     */
    public Move RemoveMove(int index){
        if(index < 0 || index >= moves.size()) return null;
        return moves.remove(index);
    }

    /** Get a move at the given index
     * @param index move at given index
     * @return Move at given idnex
     */
    public Move GetMoveAt(int index){
        if(index < 0 || index >= moves.size()) return null;
        return moves.get(index);
    }

    /** Get size of recording
     * @return the size of recording
     */
    public int Size(){
        return moves.size();
    }

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

        dest.writeString(title);
        dest.writeList(moves);
    }
}
