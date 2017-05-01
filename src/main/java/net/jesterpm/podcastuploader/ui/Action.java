/*
 * Copyright 2012-2017 Jesse Morgan <jesse@jesterpm.net>
 */

package net.jesterpm.podcastuploader.ui;

/**
 * Action handler for the UI.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
@FunctionalInterface
public interface Action {
    /**
     * This method is called when the action is performed.
     */
    void onAction();
}
