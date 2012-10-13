/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.sermonuploader.control;

import net.jesterpm.sermonuploader.config.Config;

import net.jesterpm.sermonuploader.ui.Action;
import net.jesterpm.sermonuploader.ui.ConfigurationWindow;

/**
 * Controller for the ConfigurationWindow.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class ConfigureTask {
    private final Config mAppConfig;
    private final ConfigurationWindow mWin;

    public ConfigureTask(final Config appconfig, final ConfigurationWindow win) {
        mAppConfig = appconfig;
        mWin = win;

        mWin.setSaveAction(new Action() {
            public void onAction() {
                populateConfig();
                mAppConfig.save();
                mWin.setVisible(false);
            });

        mWin.setCancelAction(new Action() {
            public void onAction() {
                mWin.setVisible(false);
            });

        mWin.setAuthorizeAction(new Action() {
            public void onAction() {
                populateConfig();
                getAuthorization();
            });

        populateWindow();
    }  

    /**
     * Set the fields in the configuration window.
     */
    private void populateWindow() {
        mWin.setAWSKey(Config.get("AWSAccessKeyId"));
        mWin.setAWSSecret(Config.get("AWSSecretKey"));
        mWin.setS3Bucket(Config.get("S3Bucket"));
        mWin.setMetadataServer(Config.get("MetadataURL"));
        mWin.setHasAuthKey(Config.get("MetadataAuthKey") != null);
    }

    /**
     * Populate the config from the window.
     */
    private void populateConfig() {
        Config.put("AWSAccessKeyId", mWin.getAWSKey());
        Config.put("AWSSecretKey", mWin.getAWSSecret());
        Config.put("S3Bucket", mWin.getS3Bucket());
        Config.put("MetadataURL", mWin.getMetadataServer());
    }

    /**
     * Display the window.
     */
    public void run() {
        mWin.setVisible(true);
    }

    /**
     * Get an authorization token from the metadata service.
     */
    private void getAuthorization() {

    }
}
