/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.model;

import java.io.*;

import java.text.ParseException;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

/**
 * Configuration and metadata parser.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class Config extends Properties {
    private static final String COMMENT = "Podcast Uploader Configuration";

    private final String mFilename;

    /**
     * Create a new Config object based on the given file.
     */
    public Config(final String filename) throws IOException {
        mFilename = filename;
        load();
    }

    /**
     * Load the config file.
     *
     * @throws IOException
     */
    public void load() throws IOException {
        try {
            final FileReader reader = new FileReader(mFilename);
            load(reader);
        } catch (FileNotFoundException e) {
            // No big deal, just start with an empty config.
        }
    }

    /**
     * Save the config file.
     *
     * @throws IOException
     */
    public void save() throws IOException {
        final FileWriter writer = new FileWriter(mFilename);
        store(writer, COMMENT);
    }
}
