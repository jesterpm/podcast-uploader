/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.sermonuploader.config;

import java.util.Map;
import java.util.HashMap;

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
        mConfig = new HashMap<String, String>();
        parse();
    }

    private void parse() {

    }

    public void save() {

    }

    public String get(final String key) {
        return mConfig.get(key);
    }

    public void put(final String key, final String obj) {
        mConfig.put(key, obj);
    }
}
