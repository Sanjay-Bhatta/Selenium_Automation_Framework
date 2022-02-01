package com.seleniumdemo.framework.pom.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    public static Properties propertyLoader(String filePath) throws IOException {
        Properties properties = new Properties();
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(filePath));
            try{
                properties.load(reader);
                reader.close();
            }
            catch(IOException ioe){
                ioe.printStackTrace();
                throw new RuntimeException("Abort! Could not load property file");
            }
        }
        catch(FileNotFoundException fe){
            fe.printStackTrace();
            throw new RuntimeException("Abort! Property file do not exists at" + filePath);
        }
        return properties;
    }
}
