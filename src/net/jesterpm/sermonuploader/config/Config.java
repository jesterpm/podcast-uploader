/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.sermonuploader.config;

/**
 * Configuration and metadata parser.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class Config {
    private final String mFilename;
    private final Map<String, String> mConfig;

    /**
     * Create a new Config object based on the given file.
     */
    public Config(final String filename) {
        mFilename = filename;
        parse();
    }

    private void parse() {

    }

    public void save() {

    }

    public String get(final String key) {
        return mConfig.get(key);
    }

    public String put(final String key, final String obj) {
        mConfig.put(key, obj);
    }
}
