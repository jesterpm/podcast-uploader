/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.podcastuploader.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * UI for the configuration window.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class ConfigurationWindow extends JFrame {
    private final JTextField mAWSKey;
    private final JTextField mAWSSecret;
    private final JTextField mS3Bucket;
    private final JTextField mMetadataServer;
    private final JButton mAuthorize;
    private final JButton mSave;

    public ConfigurationWindow() {
        super("Podcast Uploader Configuration");

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridBagLayout());
        add(panel);

        mAWSKey = new JTextField();
        mAWSSecret = new JTextField();
        mS3Bucket = new JTextField();
        mMetadataServer = new JTextField();
        mAuthorize = new JButton("Authorize App");
        mSave = new JButton("Save");
        mSave.setDefaultCapable(true);

        GridBagConstraints labelConstraint = new GridBagConstraints();
        GridBagConstraints fieldConstraint = new GridBagConstraints();

        labelConstraint.gridx = 0;
        labelConstraint.gridy = GridBagConstraints.RELATIVE;

        fieldConstraint.gridx = 1;
        fieldConstraint.gridy = GridBagConstraints.RELATIVE;
        fieldConstraint.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraint.weightx = 1;

        panel.add(new JLabel("Podcast Server:", JLabel.RIGHT), labelConstraint);
        panel.add(mMetadataServer, fieldConstraint);

        panel.add(new JLabel("AWS Access Key:", JLabel.RIGHT), labelConstraint);
        panel.add(mAWSKey, fieldConstraint);
        
        panel.add(new JLabel("AWS Secret Key:", JLabel.RIGHT), labelConstraint);
        panel.add(mAWSSecret, fieldConstraint);

        panel.add(new JLabel("S3 Bucket:", JLabel.RIGHT), labelConstraint);
        panel.add(mS3Bucket, fieldConstraint);

        GridBagConstraints buttonConstraint = new GridBagConstraints();
        buttonConstraint.gridy = 5;
        buttonConstraint.gridwidth = 2;
        buttonConstraint.weighty = 1;
        panel.add(mAuthorize, buttonConstraint);

        buttonConstraint.gridx = 1;
        buttonConstraint.gridy = 7;
        buttonConstraint.gridwidth = 1;
        buttonConstraint.anchor = GridBagConstraints.LAST_LINE_END;
        panel.add(mSave, buttonConstraint);

        pack();
        Dimension d = getPreferredSize();
        d.height += 20;
        d.width  += 50;
        setMinimumSize(d);
    } 

    public void setAWSKey(final String value) {
        mAWSKey.setText(value);
    }

    public String getAWSKey() {
        return mAWSKey.getText();
    }

    public void setAWSSecret(final String value) {
        mAWSSecret.setText(value);
    }

    public String getAWSSecret() {
        return mAWSSecret.getText();
    }

    public void setS3Bucket(final String value) {
        mS3Bucket.setText(value);
    }

    public String getS3Bucket() {
        return mS3Bucket.getText();
    }

    public void setMetadataServer(final String value) {
        mMetadataServer.setText(value);
    }

    public String getMetadataServer() {
        return mMetadataServer.getText();
    }

    public void setHasAuthKey(final boolean value) {
        if (value) {
            mAuthorize.setText("Reauthorize App");
        
        } else {
            mAuthorize.setText("Authorize App");
        }
    }

    public void addAuthorizeAction(final Action a) {
        mAuthorize.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                a.onAction();
            }
        });
    }
    
    public void addSaveAction(final Action a) {
        mSave.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                a.onAction();
            }
        });
    }

    public void addCancelAction(final Action a) {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                a.onAction();
            }
        }); 
    }
}
