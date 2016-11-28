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
    private boolean isLateParsingQueryActivated = false;
    private boolean isGeoKMeansActivated = false;
    private boolean isUsageStatisticsActivated = false;
    private boolean isXMLActivated = false;
    private File file;




    protected Configurator(){

        openOrCreateFile();
        loadConfiguration();
    }

    public static Configurator getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new Configurator();
            INSTANCE.loadConfiguration();
        }
        else INSTANCE.loadConfiguration();
        return INSTANCE;

    }

    private void openOrCreateFile() {

                try {
                    file = new File("feature.config");
                    if (!file.exists()) {
                        System.out.println("feature.config file does not exist");
                        file.createNewFile();
                        saveConfiguration();
                        System.out.println("default values feature.config file created");
                        System.out.println(file.getAbsolutePath());
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
    }


    /**
     * provides functionality for saving to the configuration file from potential external sources.
     */
    public void saveConfiguration() {
        Properties prop = new Properties();

                try {

                    OutputStream output = new FileOutputStream(file);

                    // set the properties value
                    prop.setProperty("LateParsingQuery", String.valueOf(isLateParsingQueryActivated) );
                    prop.setProperty("GeoKMeans", String.valueOf(isGeoKMeansActivated) );
                    prop.setProperty("UsageStatistics", String.valueOf(isUsageStatisticsActivated) );
                    prop.setProperty("XML", String.valueOf(isXMLActivated) );

                    // save properties to project root folder
                    prop.store(output, null);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
    }

    public void loadConfiguration(){
    Properties prop = new Properties();

                try {

                    InputStream input =new FileInputStream(file);
                    // load a properties file
                    prop.load(input);

                    // get the property value and print it out
                    isLateParsingQueryActivated = Boolean.valueOf(prop.getProperty("LateParsingQuery"));
                    isGeoKMeansActivated = Boolean.valueOf(prop.getProperty("GeoKMeans"));
                    isUsageStatisticsActivated = Boolean.valueOf(prop.getProperty("UsageStatistics"));
                    isXMLActivated = Boolean.valueOf(prop.getProperty("XML"));
                    //printValues();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


    }
    public void printValues(){
        System.out.println("LateParsingQuery Activated: "+isLateParsingQueryActivated);
        System.out.println("GeoKMeans Activated: "+isGeoKMeansActivated);
        System.out.println("UsageStatistics Activated: "+isUsageStatisticsActivated);
        System.out.println("XML Activated: "+isXMLActivated);
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
