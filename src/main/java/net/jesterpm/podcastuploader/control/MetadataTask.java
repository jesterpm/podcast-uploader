/*
 * Copyright 2017 Jesse Morgan <jesse@jesterpm.net>
 */

package net.jesterpm.podcastuploader.control;

import net.jesterpm.podcastuploader.model.Config;

import net.jesterpm.podcastuploader.model.Metadata;
import net.jesterpm.podcastuploader.ui.Action;
import net.jesterpm.podcastuploader.ui.MetadataWindow;
import net.jesterpm.podcastuploader.ui.UIFactory;

import java.time.LocalDate;

/**
 * Controller for the MetadataWindow.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class MetadataTask {
    private final Config mConfig;
    private final UIFactory mUIFactory;
    private final MetadataWindow mWin;
    private Action mOnComplete;

    public MetadataTask(final Config config, final UIFactory uiFactory) {
        mConfig = config;
        mUIFactory = uiFactory;
        mWin = mUIFactory.createMetadataWindow();

        mWin.addUploadAction(() -> {
            Metadata metadata = getMetadata();
            UploadTask task = new UploadTask(config, uiFactory, metadata);
            mWin.setVisible(false);
            task.run(mOnComplete);
        });

        mWin.addCancelAction(() -> {
            mWin.setVisible(false);
            mOnComplete.onAction();
        });

        mWin.addConfigButtonAction(() -> {
            ConfigureTask task = new ConfigureTask(config, uiFactory);
            task.run();
        });

        populateWindow();
    }

    private Metadata getMetadata() {
        return new Metadata(
                mWin.getDate(),
                mWin.getTitle(),
                mWin.getSeries(),
                mWin.getSpeaker(),
                mWin.getFile());
    }

    /**
     * Set the fields in the configuration window.
     */
    private void populateWindow() {
        mWin.setDate(LocalDate.now());
        mWin.setTitle(mConfig.getProperty("lastTitle"));
        mWin.setSeries(mConfig.getProperty("lastSeries"));
        mWin.setSpeaker(mConfig.getProperty("lastSpeaker"));
    }

    /**
     * Populate the config from the window.
     */
    private void updateConfig() {
        mConfig.put("lastTitle", mWin.getTitle());
        mConfig.put("lastSeries", mWin.getSeries());
        mConfig.put("lastSpeaker", mWin.getSpeaker());
    }

    /**
     * Display the window.
     */
    public void run(final Action onComplete) {
        mWin.setVisible(true);
        mOnComplete = onComplete;
    }
}
