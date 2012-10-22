/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import net.jesterpm.podcastuploader.config.Config;
import net.jesterpm.podcastuploader.ui.ProgressWindow;

/**
 * 
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class UploadTask {
    /**
     * This is the filename of the metadata file with a path separator prefixed.
     */
    private static final String METADATA_FILE =
        System.getProperty("file.separator") + "/metadata.txt";

    /**
     * Progress window.
     */
    private final ProgressWindow mProgressWindow;

    /**
     * Application config.
     */
    private final Config mAppConfig;

    /**
     * Podcast metadata file.
     */
    private final Config mMetadata;

    /**
     * UploadTask Constructor.
     * @param appconfig The application config
     * @param win The progress window user interface.
     * @param dir The directory to upload.
     */
    public UploadTask(final Config appconfig, final ProgressWindow win, final String dir) {
        mProgressWindow = win;
        mAppConfig = appconfig;

        mMetadata = new Config(dir + METADATA_FILE);
    }

    /**
     * Uploads the podcast described in the metadata file.
     *
     * Expected keys:
     *   date             The podcast date.
     *   title            The podcast title.
     *   author           The podcast author.
     *
     * Optional keys:
     *   series           The series the video is part of.
     *   video            Name of the video file.
     *   audio            Name of the audio file.
     *   video_lowres     Name of the low-res video file
     *   image            Image associated with the podcast
     *   mobileimage      Image for mobile phones
     *   description      Podcast description
     *
     * Video and/or audio is required, as without one of them there is nothing
     * to upload.
     */
    public void run() {
        DateFormat fmt = DateFormat.getDateInstance(DateFormat.SHORT);
        final Date date = fmt.parse(mMetadata.get("date"));

        fmt = new SimpleDateFormat("yyyyMMdd");
        final String baseFilename = fmt.format(date) + "-" 
            + safeString(mMetadata.get("title"));
    }

    private String safeString(String str) {
        char[] newStr = str.trim().toLowerCase().toCharArray();
        boolean firstSpace = true;
        int p = 0;
        for (int i = 0; i < newStr.length; i++) {
            if (Character.isWhitespace(newStr[i])) {
                if (firstSpace) {
                    newStr[p++] = '-';
                    firstSpace = false;
                }

            } else if (Character.isLetterOrDigit(newStr[i])) {
                newStr[p++] = newStr[i];
                firstSpace = true;

            } else {
                firstSpace = true;
            }
        }
         
        return new String(newStr, 0, p);       
    }
}
