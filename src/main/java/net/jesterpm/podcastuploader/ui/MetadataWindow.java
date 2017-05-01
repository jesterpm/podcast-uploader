/*
 * Copyright 2017 Jesse Morgan <jesse@jesterpm.net>
 */

package net.jesterpm.podcastuploader.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * UI for gathering podcast metadata.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class MetadataWindow extends JFrame {

    private final JTextField mDate;
    private final JTextField mTitle;
    private final JTextField mSeries;
    private final JTextField mSpeaker;

    private File mFile;
    private final JTextField mFileField;
    private final JButton mFileButton;

    private final JButton mConfigButton;
    private final JButton mUploadButton;

    public MetadataWindow() {
        super("Podcast Metadata");

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridBagLayout());
        add(panel);

        mDate = new JTextField();
        mTitle = new JTextField();
        mSeries = new JTextField();
        mSpeaker = new JTextField();

        mFileField = new JTextField();
        mFileField.setEnabled(false);
        mFileField.setColumns(40);

        mFileButton = new JButton("Browse");
        mFileButton.addActionListener(e -> chooseFile());

        mConfigButton = new JButton("Configure");

        mUploadButton = new JButton("Upload");
        mUploadButton.setDefaultCapable(true);

        GridBagConstraints labelConstraint = new GridBagConstraints();
        labelConstraint.gridx = 0;
        labelConstraint.gridy = GridBagConstraints.RELATIVE;

        GridBagConstraints fieldConstraint = new GridBagConstraints();
        fieldConstraint.gridx = 1;
        fieldConstraint.gridy = GridBagConstraints.RELATIVE;
        fieldConstraint.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraint.weightx = 1;
        fieldConstraint.gridwidth = 2;

        panel.add(new JLabel("Date:", JLabel.RIGHT), labelConstraint);
        panel.add(mDate, fieldConstraint);

        panel.add(new JLabel("Title:", JLabel.RIGHT), labelConstraint);
        panel.add(mTitle, fieldConstraint);

        panel.add(new JLabel("Series:", JLabel.RIGHT), labelConstraint);
        panel.add(mSeries, fieldConstraint);

        panel.add(new JLabel("Speaker:", JLabel.RIGHT), labelConstraint);
        panel.add(mSpeaker, fieldConstraint);

        panel.add(new JLabel("File:", JLabel.RIGHT), labelConstraint);

        GridBagConstraints fileFieldConstraint = new GridBagConstraints();
        fileFieldConstraint.gridx = 1;
        fileFieldConstraint.gridy = 4;
        fileFieldConstraint.fill = GridBagConstraints.HORIZONTAL;
        fileFieldConstraint.weightx = 1;
        panel.add(mFileField, fileFieldConstraint);

        GridBagConstraints fileButtonConstraint = new GridBagConstraints();
        fileButtonConstraint.gridx = 2;
        fileButtonConstraint.gridy = 4;
        panel.add(mFileButton, fileButtonConstraint);

        GridBagConstraints buttonConstraint = new GridBagConstraints();
        buttonConstraint.gridx = 0;
        buttonConstraint.gridy = 7;
        buttonConstraint.gridwidth = 1;
        buttonConstraint.anchor = GridBagConstraints.LAST_LINE_END;
        panel.add(mConfigButton, buttonConstraint);

        buttonConstraint.gridx = 1;
        buttonConstraint.gridwidth = 2;
        buttonConstraint.gridy = 7;
        buttonConstraint.anchor = GridBagConstraints.LAST_LINE_END;
        panel.add(mUploadButton, buttonConstraint);

        pack();
        Dimension d = getPreferredSize();
        d.height += 20;
        d.width  += 50;
        setMinimumSize(d);
    }

    public void setDate(final LocalDate value) {
        mDate.setText(value.format(DateTimeFormatter.ISO_DATE));
    }

    public LocalDate getDate() {
        return LocalDate.parse(mDate.getText(), DateTimeFormatter.ISO_DATE);
    }

    public void setTitle(final String value) {
        mTitle.setText(value);
    }

    public String getTitle() {
        return mTitle.getText();
    }

    public void setSeries(final String value) {
        mSeries.setText(value);
    }

    public String getSeries() {
        return mSeries.getText();
    }

    public void setSpeaker(final String value) {
        mSpeaker.setText(value);
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(final File value) {
        mFile = value;
        mSpeaker.setText(value.getAbsolutePath());
    }

    public String getSpeaker() {
        return mSpeaker.getText();
    }

    public void addUploadAction(final Action a) {
        mUploadButton.addActionListener(e -> a.onAction());
    }

    public void addConfigButtonAction(final Action a) {
        mConfigButton.addActionListener(e -> a.onAction());
    }

    public void addCancelAction(final Action a) {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                a.onAction();
            }
        });
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Audio Files", "mp3", "wav"));
        int result = chooser.showDialog(this, "Select");
        if (result == JFileChooser.APPROVE_OPTION) {
            setFile(chooser.getSelectedFile());
        }
    }
}
