package com.loitp.views.layout.circularView;

import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Handler;
import android.widget.CursorAdapter;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public abstract class CircularViewCursorAdapter implements BaseCircularViewAdapter {
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    protected boolean mDataValid;
    protected boolean mAutoRequery;
    private Cursor mCursor;
    protected int mRowIDColumn;
    protected ChangeObserver mChangeObserver;
    protected DataSetObserver mDataSetObserver;

    @Deprecated
    public CircularViewCursorAdapter(Cursor c) {
        init(c, CursorAdapter.FLAG_AUTO_REQUERY);
    }

    @Suppress(names = "unused")
    public CircularViewCursorAdapter(Cursor c, boolean autoRequery) {
        init(c, autoRequery ? CursorAdapter.FLAG_AUTO_REQUERY : CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Suppress(names = "unused")
    public CircularViewCursorAdapter(Cursor c, int flags) {
        init(c, flags);
    }

    void init(final Cursor c, int flags) {
        if ((flags & CursorAdapter.FLAG_AUTO_REQUERY) == CursorAdapter.FLAG_AUTO_REQUERY) {
            flags |= CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
            mAutoRequery = true;
        } else {
            mAutoRequery = false;
        }
        boolean cursorPresent = c != null;
        mCursor = c;
        mDataValid = cursorPresent;
        mRowIDColumn = cursorPresent ? c.getColumnIndexOrThrow("_id") : -1;
        if ((flags & CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) == CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
            mChangeObserver = new ChangeObserver();
            mDataSetObserver = new MyDataSetObserver();
        } else {
            mChangeObserver = null;
            mDataSetObserver = null;
        }

        if (cursorPresent) {
            if (mChangeObserver != null) c.registerContentObserver(mChangeObserver);
            if (mDataSetObserver != null) c.registerDataSetObserver(mDataSetObserver);
        }
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getCount() {
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public void setupMarker(int position, Marker marker) {
        setupMarker(position, marker, mCursor);
    }

    /**
     * Setup the marker that should show at a given position. The position will be between 0 and the value returned by {@link #getCount()}.
     *
     * @param position Position of the marker to show.
     * @param marker   The marker that will be used to display.
     * @param cursor   Cursor that is being used.
     */
    public abstract void setupMarker(int position, Marker marker, Cursor cursor);

    public Cursor getItem(int position) {
        if (mDataValid && mCursor != null) {
            mCursor.moveToPosition(position);
            return mCursor;
        } else {
            return null;
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        if (oldCursor != null) {
            if (mChangeObserver != null) oldCursor.unregisterContentObserver(mChangeObserver);
            if (mDataSetObserver != null) oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if (newCursor != null) {
            if (mChangeObserver != null) newCursor.registerContentObserver(mChangeObserver);
            if (mDataSetObserver != null) newCursor.registerDataSetObserver(mDataSetObserver);
            mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIDColumn = -1;
            mDataValid = false;
            // notify the observers about the lack of a data set
            notifyDataSetInvalidated();
        }
        return oldCursor;
    }

    @Suppress(names = "unused")
    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    protected void onContentChanged() {
        if (mAutoRequery && mCursor != null && !mCursor.isClosed()) {
            mDataValid = mCursor.requery();
        }
    }

    private class ChangeObserver extends ContentObserver {
        public ChangeObserver() {
            super(new Handler());
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            onContentChanged();
        }
    }

    private class MyDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            mDataValid = false;
            notifyDataSetInvalidated();
        }
    }
}
