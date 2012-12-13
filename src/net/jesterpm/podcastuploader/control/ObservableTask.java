/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.control;

import java.util.Observable;

/**
 * ProgressInterface is an abstract based class for any interface that
 * provides progress notification. The class tracks the progress of multiple
 * simultaneous tasks and aggregates their progress.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public abstract class ObservableTask extends Observable {
    private float mProgress;

    public ObservableTask() {
        mProgress = 0;
    }

    /**
     * @return The percentage complete as a float between 0 and 1.
     */
    public float getProgress() {
        return mProgress;
    }

    /**
     * Set the current progress.
     *
     * @param percentComplete The percentage complete as a float between 0 and 1.
     */
    protected void setProgress(float percentComplete) {
        mProgress = percentComplete;
        setChanged();
        notifyObservers();
    }
}
