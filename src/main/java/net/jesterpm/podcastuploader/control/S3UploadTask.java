/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.control;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client; 

import java.util.concurrent.ExecutorService;

import net.jesterpm.podcastuploader.model.Config;

/**
 * Task for uploading a single file to S3.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class S3UploadTask extends ObservableTask implements Runnable {
    private final ExecutorService mExecutor;

    private final AmazonS3Client mClient;
    
    private final String mBucket;
    private final String mLocalFile;
    private final String mS3Key;

    private int mCurrentChunk;
    private int mTotalChunks; 

    /**
     * Prepare a new S3UploadTask.
     *
     * @param appConfig The current running appConfig.
     * @param localFile The name of the local file to upload.
     * @param remoteFile The key to use for the file in S3.
     */
    public S3UploadTask(final Config appConfig, final String localFile,
           final String remoteFile, final ExecutorService executor) {

        mExecutor = executor;

        mClient = new AmazonS3Client(new BasicAWSCredentials(
                    appConfig.getProperty("AWSAccessKeyId"), appConfig.getProperty("AWSSecretKey")));

        mBucket = appConfig.getProperty("S3Bucket");
        mLocalFile = localFile;
        mS3Key = remoteFile;

        mCurrentChunk = 0;
        mTotalChunks = 1;  // Avoid div-by-0 in getProgress()
    }

    /**
     * Start the upload.
     */
    @Override
    public void run() {
        // Create bucket if needed
        createBucket();

        // Start Upload
    }

    /**
     * Create the S3 bucket if it doesn't already exist.
     */
    private void createBucket() {

    }

    /**
     * @return The number of file parts uploaded over the total number of file parts.
     */
    @Override
    public float getProgress() {
        return (float) mCurrentChunk / mTotalChunks;
    }

    /**
     * @return The S3 key this object uploads to.
     */
    public String getS3Key() {
        return mS3Key;
    }
}
