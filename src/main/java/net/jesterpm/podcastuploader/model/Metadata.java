package net.jesterpm.podcastuploader.model;

import java.io.File;
import java.time.LocalDate;

/**
 * Podcast Metadata.
 */
public class Metadata {
    private final LocalDate mDate;
    private final String mTitle;
    private final String mSeries;
    private final String mSpeaker;
    private final File mFile;

    public Metadata(LocalDate date, String title, String series, String speaker, File file) {
        this.mDate = date;
        this.mTitle = title;
        this.mSeries = series;
        this.mSpeaker = speaker;
        this.mFile = file;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSeries() {
        return mSeries;
    }

    public String getSpeaker() {
        return mSpeaker;
    }

    public File getFile() {
        return mFile;
    }
}
