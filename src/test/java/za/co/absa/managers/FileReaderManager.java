package za.co.absa.managers;

import za.co.absa.dataProviders.ConfigFileReader;

public class FileReaderManager {

    private static FileReaderManager fileReaderManager = new FileReaderManager();
    private static ConfigFileReader configFileReader = new ConfigFileReader();

    private FileReaderManager(){}

    public static FileReaderManager getInstance(){
        return fileReaderManager;
    }

    public ConfigFileReader getConfigFileReader(){
        return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
    }
}
