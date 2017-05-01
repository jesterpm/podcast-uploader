package net.jesterpm.podcastuploader.ui;

/**
 * Implementation of UIFactory which produces a GUI.
 */
public class GUIFactory implements UIFactory {
    @Override
    public MetadataWindow createMetadataWindow() {
        return new MetadataWindow();
    }

    @Override
    public ProgressWindow createProcessWindow() {
        return new ProgressWindow();
    }

    @Override
    public ConfigurationWindow createConfigurationWindow() {
        return new ConfigurationWindow();
    }
}
