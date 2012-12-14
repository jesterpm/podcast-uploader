/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.jesterpm.podcastuploader.config.Config;
import net.jesterpm.podcastuploader.ui.ProgressInterface;

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
    private final ProgressInterface mProgressInterface;

    /**
     * Application config.
     */
    private final Config mAppConfig;

    /**
     * Podcast metadata file.
     */
    private final Config mMetadata;

    /**
     * Thread Pool used for and by the UploadTasks.
     */
    private final ThreadPoolExecutor mExecutor;

    /**
     * UploadTask Constructor.
     * @param appconfig The application config
     * @param win The progress window user interface.
     * @param dir The directory to upload.
     */
    public UploadTask(final Config appconfig, final ProgressInterface progressInterface,
           final String dir) {

        mProgressInterface = progressInterface;
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

        // Build a list of files to upload.
        Map<String, S3UploadTask> files = getFilesToUpload(baseFilename);
        for (S3UploadTask task : files.values()) {
            mExecutor.submit(task);
            mProgressInterface.monitorTask(task);
        }

        // Wait until all uploads complete.

        // Publish the podcast metadata.
        Map<String, String> metadata = new HashMap<String, String>(mMetadata.getMap());

        for (Map.Entry<String, S3UploadTask> entry : files.entrySet()) {
            metadata.put(entry.getKey(), entry.getValue().getS3Key());
        }

        PublishPodcastTask task = new PublishPodcastTask(mAppConfig, metadata);
        task.run();
    }

    /**
     * Build the list of S3UploadTasks to execute.
     */
    private Map<String, S3UploadTask> getFilesToUpload(final String basename) {
        Map<String, S3UploadTask> files = new HashMap<String, S3UploadTask>();

        String localFile;
        String remoteFile;

        localFile = mMetadata.get("video");
        if (localFile != null) {
            remoteFile = basename + "-video" + fileExtension(localFile);
            files.put("video", new S3UploadTask(mAppConfig, localFile, 
                        remoteFile, mExecutor));
        }

        localFile = mMetadata.get("video_lowres");
        if (localFile != null) {
            remoteFile = basename + "-videolow" + fileExtension(localFile);
            files.put("video_lowres", new S3UploadTask(mAppConfig, localFile,
                    remoteFile, mExecutor));
        }

        localFile = mMetadata.get("audio");
        if (localFile != null) {
            remoteFile = basename + "-audio" + fileExtension(localFile);
            files.put("audio", new S3UploadTask(mAppConfig, localFile,
                    remoteFile, mExecutor));
        }
 
        localFile = mMetadata.get("image");
        if (localFile != null) {
            remoteFile = basename + "-image" + fileExtension(localFile);
            files.put("image", new S3UploadTask(mAppConfig, localFile, 
                    remoteFile, mExecutor));
        }

        localFile = mMetadata.get("mobileimage");
        if (localFile != null) {
            remoteFile = basename + "-mobileimage" + fileExtension(localFile);
            files.put("mobileimage", new S3UploadTask(mAppConfig, localFile,
                    remoteFile, mExecutor));
        }

        return files;
    }

    /**
     * @return the extension from the given filename.
     */
    private String fileExtension(final String file) {
        int pos = file.lastIndexOf('.');
 
        if (pos == -1) {
            return "";

        } else {
            return file.substring(pos);
        }
    }

    /**
     * Transform str into a URL safe string by substituting spaces with dashes
     * and dropping all other non-alphanumeric characters.
     *
     * @param str The String to transform.
     * @return The transformed string.
     */
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
