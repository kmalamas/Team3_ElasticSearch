package org.elasticsearch.configurator;

import org.elasticsearch.SpecialPermission;

import java.io.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Properties;

/**
 * Created by kmala on 15-Nov-16.
 */
public class Configurator {

    private static Configurator INSTANCE = null;
    private boolean isLateParsingQueryActivated;
    private boolean isGeoKMeansActivated;
    private boolean isUsageStatisticsActivated;
    private boolean isXMLActivated;
    private File file;




    protected Configurator(){
        isLateParsingQueryActivated = true;
        isGeoKMeansActivated = false;
        isUsageStatisticsActivated = false;
        isXMLActivated = false;

            //openOrCreateFile();
            //loadConfiguration();

    }

    private void openOrCreateFile() {
        try {

                file = AccessController.doPrivileged((PrivilegedExceptionAction<File>)() -> new File("feature.config")
                );
                if (!file.exists())
                    AccessController.doPrivileged((PrivilegedExceptionAction<Boolean>)() -> file.createNewFile());
        }


        catch (PrivilegedActionException e) { }


    }

    public static Configurator getInstance(){
    if (INSTANCE == null)
        INSTANCE = new Configurator();

        return INSTANCE;

    }



    public void saveConfiguration() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(file);

            // set the properties value
            prop.setProperty("LateParsingQuery", String.valueOf(isLateParsingQueryActivated) );
            prop.setProperty("GeoKMeans", String.valueOf(isGeoKMeansActivated) );
            prop.setProperty("UsageStatistics", String.valueOf(isUsageStatisticsActivated) );
            prop.setProperty("XML", String.valueOf(isXMLActivated) );

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void loadConfiguration(){
    Properties prop = new Properties();
    InputStream input = null;

    try {
        input = AccessController.doPrivileged((PrivilegedExceptionAction<FileInputStream>)
                        () -> new FileInputStream(file));

        // load a properties file
        prop.load(input);

        // get the property value and print it out
        isLateParsingQueryActivated = Boolean.valueOf(prop.getProperty("LateParsingQuery"));
        isGeoKMeansActivated = Boolean.valueOf(prop.getProperty("GeoKMeans"));
        isUsageStatisticsActivated = Boolean.valueOf(prop.getProperty("UsageStatistics"));
        isXMLActivated = Boolean.valueOf(prop.getProperty("XML"));

    } catch (PrivilegedActionException ex) {
        ex.printStackTrace();
    }
    catch (IOException ioe) {}
    finally {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

    public boolean getisLateParsingQueryActivated(){return isLateParsingQueryActivated;}
    public void setLateParsingQueryActivated(boolean state){isLateParsingQueryActivated = state;}
    public boolean getisGeoKMeansActivated(){return isGeoKMeansActivated;}
    public void setGeoKMeansActivated(boolean state){ isGeoKMeansActivated = state;}
    public boolean getisUsageStatisticsActivated(){return isUsageStatisticsActivated;}
    public void setUsageStatisticsActivated(boolean state){ isUsageStatisticsActivated = state;}
    public boolean getisXMLActivated(){return isXMLActivated;}
    public void setXMLActivated(boolean state){ isXMLActivated = state;}

}
