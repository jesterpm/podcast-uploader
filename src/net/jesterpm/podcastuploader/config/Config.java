/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

import java.text.ParseException;

import java.util.Map;
import java.util.HashMap;

/**
 * Configuration and metadata parser.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class Config {
    private final String mFilename;
    private Map<String, String> mConfig;

    /**
     * States the parser can be in.
     */
    private enum TokenizerState {
        OUT_OF_ENTRY, IN_ENTRY;
    }

    /**
     * Create a new Config object based on the given file.
     */
    public Config(final String filename) {
        mFilename = filename;
        mConfig = new HashMap<String, String>();
        parse();
    }

    private void parse() {
        try {
            final File configfile = new File(mFilename);
            if (configfile.isFile()) {
                BufferedReader in = new BufferedReader(new FileReader(mFilename));

                int lineno = 0;
                String line;
                String key = "";
                String value = "";
                TokenizerState state = TokenizerState.OUT_OF_ENTRY;
                while ((line = in.readLine()) != null) {
                    lineno++;
                    if (line.length() == 0) {
                        continue;
                    }

                    switch (state) {
                        case IN_ENTRY:
                            if (Character.isWhitespace(line.charAt(0))) {
                                value += "\n" + line.trim();
                                break;

                            } else {
                                // Beginning new entry. Save old and pass through.
                                mConfig.put(key, value);
                                key = value = "";
                                state = TokenizerState.OUT_OF_ENTRY;
                                // NB Intentionally falling through...
                            }

                        case OUT_OF_ENTRY:
                            if (line.charAt(0) == '#') {
                                continue;
                            }

                            final int pos = line.indexOf(':');
                            if (pos == -1) {
                                throw new ParseException("Missing : at line " + lineno, lineno);
                            }
                            key = line.substring(0, pos).trim().toLowerCase();
                            if (key.length() == 0) {
                                throw new ParseException("Zero-length key on line " + lineno,
                                       lineno);
                            }
                            value = line.substring(pos + 1).trim();
                            state = TokenizerState.IN_ENTRY;
                            break;
                    }
                }
                
                // Catch last key/value
                if (state == TokenizerState.IN_ENTRY) {
                    mConfig.put(key, value);
                }

                in.close();
            }

        } catch (final Exception e) {
            System.err.println("[ERROR] Failed to load config from " + mFilename + ". "
                    + e.getMessage());
        }
    }

    public void save() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(mFilename));

            for (Map.Entry<String, String> entry : mConfig.entrySet()) {
                out.write(entry.getKey());
                out.write(": ");
                out.write(entry.getValue());
                out.newLine();
            }

            out.close();

        } catch (final Exception e) {
            System.err.println("[ERROR] Failed to save configuration: " + e.getMessage());
        }
    }

    public String get(final String key) {
        return mConfig.get(key);
    }

    public void put(final String key, final String obj) {
        mConfig.put(key, obj);
    }

    public Map<String, String> getMap() {
        return mConfig;
    }
}
