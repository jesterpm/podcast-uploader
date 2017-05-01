/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader;

import net.jesterpm.podcastuploader.model.Config;
import net.jesterpm.podcastuploader.control.MetadataTask;
import net.jesterpm.podcastuploader.ui.GUIFactory;

import java.io.*;

/**
 * Application entry-point.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class PodcastUploader {
    private static final String DEFAULT_CONFIG = System.getProperty("user.home") 
        + System.getProperty("file.separator") + ".podcastuploader";

    public static void main(String... args) {
        // Load the configuration.
        final Config config;
        try {
            config = new Config(DEFAULT_CONFIG);
        } catch (IOException e) {
            System.err.println("Exception loading config! " + e.getMessage());
            System.exit(1);
            return;
        }

        GUIFactory uiFactory = new GUIFactory();
        MetadataTask task = new MetadataTask(config, uiFactory);
        task.run(() -> System.exit(0));
    }
}
