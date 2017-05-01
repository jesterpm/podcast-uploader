/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.control;

import net.jesterpm.podcastuploader.model.Config;

import net.jesterpm.podcastuploader.ui.ConfigurationWindow;
import net.jesterpm.podcastuploader.ui.UIFactory;

import java.io.IOException;

/**
 * Controller for the ConfigurationWindow.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class ConfigureTask {
    private final Config mConfig;
    private final ConfigurationWindow mWin;

    public ConfigureTask(final Config config, final UIFactory uiFactory) {
        mConfig = config;
        mWin = uiFactory.createConfigurationWindow();

        mWin.addSaveAction(() -> {
            try {
                populateConfig();
                mConfig.save();
            } catch (IOException e) {
                // TODO
            }
            mWin.setVisible(false);
        });

        mWin.addCancelAction(() -> mWin.setVisible(false));

        populateWindow();
    }  

    /**
     * Set the fields in the configuration window.
     */
    private void populateWindow() {
        mWin.setAWSKey(mConfig.getProperty("AWSAccessKeyId"));
        mWin.setAWSSecret(mConfig.getProperty("AWSSecretKey"));
        mWin.setS3Bucket(mConfig.getProperty("S3Bucket"));
    }

    /**
     * Populate the config from the window.
     */
    private void populateConfig() {
        mConfig.put("AWSAccessKeyId", mWin.getAWSKey());
        mConfig.put("AWSSecretKey", mWin.getAWSSecret());
        mConfig.put("S3Bucket", mWin.getS3Bucket());
    }

    /**
     * Display the window.
     */
    public void run() {
        mWin.setVisible(true);
    }
}
