/*
 * Copyright 2012 Jesse Morgan
 */

package net.jesterpm.sermonuploader;

/**
 * Application entry-point.
 *
 * @author Jesse Morgan <jesse@jesterpm.net>
 */
public class SermonUploader {
    private static final String DEFAULT_CONFIG = System.getProperty("user.home") 
        + System.getProperty("file.separator") + ".sermonuploader";

    public static void main(String... args) {
        final Config appconfig = new Config(DEFAULT_CONFIG);

        if (args.length == 0) {
            startConfigure(appconfig);

        } else {
            if (args[0].equals("--help")) {
                printHelp();
            }

            startUpload(appconfig, args[0]);
        }
    } 

    private static void printHelp() {
        System.out.println("SermonUploader - Podcast upload utility.");
        System.out.println("Created by Jesse Morgan <jesse@jesterpm.net>");
        System.out.println("http://jesterpm.net/projects/sermonuploader");
        System.out.println();
        System.out.println("Usage: SermonUploader [directory]");
        System.out.println(
                "\tWhen started with no arguments, the configuration dialog is opened.");
        System.out.println("\tWhen started with one argument, it is assumed to "
                + "be a directory with a metadata.txt file with upload instructions."); 
        System.out.println();
    }

    private static void startConfigure(final Config appconfig) {
        ConfigurationWindow win = new ConfigurationWindow();
        ConfigureTask task = new ConfigureTask(appconfig, win);

        task.run();
    }

    public static void startUpload(final Config appconfig, final String dir) {
        ProgressWindow win = new ProgressWindow();
        UploadTask task = new UploadTask(appconfig, win, dir);

        task.run();
    }
}
