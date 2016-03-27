package stefan.com.mk.mycriminalintent;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Stefan on 07-Mar-16.
 */
public class Crime { //Model кај што имам варијабли поврзани со самата апликација
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private Date mTime;


    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();//sets mDate to the current date.
        mTime = new Date();
    }

    public UUID getId() {
        return mId;
    }//само getter пошто е read-only

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }
}
