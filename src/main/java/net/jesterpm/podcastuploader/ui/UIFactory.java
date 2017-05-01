package net.jesterpm.podcastuploader.ui;

/**
 * Factory which produces various views.
 */
public interface UIFactory {

    MetadataWindow createMetadataWindow();

    ProgressWindow createProcessWindow();

    ConfigurationWindow createConfigurationWindow();
}
