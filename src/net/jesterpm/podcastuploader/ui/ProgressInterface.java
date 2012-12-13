/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import net.jesterpm.podcastuploader.control.ObserableTask;

/**
 * ProgressInterface is an abstract based class for any interface that
 * provides progress notification. The class tracks the progress of multiple
 * simultaneous tasks and aggregates their progress.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public abstract class ProgressInterface implements Observer {
    private Map<ObservableTask, Float> mProgressMap;

    /**
     * Creates a new ProgressInterface monitoring nothing.
     */
    public ProgressInterface() {
        mProgressMap = new HashMap<ObservableTask, Float>();
    }
    
    /**
     * Begin monitoring the given task.
     *
     * @param task The ObservableTask to monitor.
     */
    public void monitorTask(final ObservableTask task) {
        task.addObserver(this);
        mProgressMap.put(task, 0);
        setProgress(getProgress());
    }

    /**
     * Called when the progress changes.
     *
     * @param progress The current percentage complete,
     *                 indicated by a float ranging from 0 to 1.
     */
    protected abstract void setProgress(float percentComplete);

    /**
     * Called when the progress for a task changes.
     * @param task The task that changed.
     * @param arg  Unused.
     */
    @Override
    public void update(Observable task, Object arg) {
        mProgressMap.put(task, task.getProgress());
        setProgress(getProgress());
    }

    /**
     * @return A float ranging from 0 to 1 representing the aggregate progress.
     *         If the ProgressInterface is not monitoring any tasks, this will return 0.
     */
    public float getProgress() {
        if (mProgressMap.size() == 0) {
            return 0;
        }

        float totalProgress = 0;
        for (float taskProgress : mProgressMap.getValues()) {
            totalProgress += taskProgress;
        }

        return totalProgress / mProgressMap.size();
    }
}
