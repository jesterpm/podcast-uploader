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

        mWin.addSaveAction(new Action() {
            public void onAction() {
                populateConfig();
                mAppConfig.save();
                mWin.setVisible(false);
                System.exit(0);
            }
        });

        mWin.addCancelAction(new Action() {
            public void onAction() {
                mWin.setVisible(false);
                System.exit(0);
            }
        });

        mWin.addAuthorizeAction(new Action() {
            public void onAction() {
                populateConfig();
                getAuthorization();
            }
        });

        populateWindow();
    }  

    /**
     * Set the fields in the configuration window.
     */
    private void populateWindow() {
        mWin.setAWSKey(mAppConfig.get("AWSAccessKeyId"));
        mWin.setAWSSecret(mAppConfig.get("AWSSecretKey"));
        mWin.setS3Bucket(mAppConfig.get("S3Bucket"));
        mWin.setMetadataServer(mAppConfig.get("MetadataURL"));
        mWin.setHasAuthKey(mAppConfig.get("MetadataAuthKey") != null);
    }

    /**
     * Populate the config from the window.
     */
    private void populateConfig() {
        mAppConfig.put("AWSAccessKeyId", mWin.getAWSKey());
        mAppConfig.put("AWSSecretKey", mWin.getAWSSecret());
        mAppConfig.put("S3Bucket", mWin.getS3Bucket());
        mAppConfig.put("MetadataURL", mWin.getMetadataServer());
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
