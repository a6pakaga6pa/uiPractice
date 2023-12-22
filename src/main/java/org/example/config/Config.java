package org.example.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.extern.slf4j.*;
import org.example.config.entity.ConfigEntity;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * General configurations mapped to the config.yaml file. If you add a key/value property or object to the
 * config.yaml, be sure to add a method here to parse the data. If the config should be handled on the fly,
 * e.g. continuous integration, include a system property for it in build.gradle under the tasks.withType(Test) section
 * <p>
 * This config is where the magic lives in tying all moving parts of the framework together.
 */
@Slf4j
@Getter
public class Config {

    private String baseUrl;
    private List<String> resolutions;
    private final ConfigEntity config;
    private static Config instance = null;
    private static final String PATH_CONFIG_YAML = "src/test/resources/config.yaml";


    /**
     * Constructor
     */
    public Config() {
        config = parseToObject();
        setBaseUrl(config.getBaseUrl());
        setResolutions(config.getResolutions());
    }

    /**
     * Explicitly instantiate singleton Config object if one does not exist
     *
     * @return Config object
     */
    public static Config getInstance() {

        if (instance == null) {

            instance = new Config();

        }

        return instance;

    }

    /**
     * Parses config.yaml and returns HashMap of file
     *
     * @return JsonPath
     */
    private ConfigEntity parseToObject() {

        Gson gson = new Gson();
        Yaml yaml = new Yaml();
        HashMap<String, Object> configData;

        final String defaultFileName = PATH_CONFIG_YAML;

        log.debug("Locating configuration file, \"{}\"...", defaultFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(defaultFileName))) {

            log.debug("Configuration file, \"{}\" found. Parsing yaml file...", defaultFileName);
            configData = yaml.load(reader);

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        JsonElement jsonElement = gson.toJsonTree(configData);

        return gson.fromJson(jsonElement, ConfigEntity.class);

    }

    /*********************************************************************************************
     * 								SERVICE SPECIFIC CONFIGS BEGIN                         		 *
     *********************************************************************************************/

    /**
     * Set all services URL from config.yaml or Gradle command line argument for Continuous Integration testing
     */
    private void setBaseUrl(String baseUrl) {

        log.debug("Setting base URL...");

        this.baseUrl = baseUrl;
        log.debug("baseUrl: \"{}\"...", baseUrl);
    }

    /**
     * Set testable resolutions
     */
    private void setResolutions(List<String> resolutionValues) {

        log.debug("Setting resolutions...");

        resolutions = resolutionValues;
        log.debug("resolutions: \"{}\"...", resolutions);
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public List<String> getResolutions() {
        return this.resolutions;
    }

    public ConfigEntity getConfig() {
        return this.config;
    }


/*********************************************************************************************
 * 								SERVICE SPECIFIC CONFIGS END                         		 *
 *********************************************************************************************/

}
