package be.miliueinfo.midas.ui.healthcheck;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * @since 13/06/14
 */
public final class VersionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionUtil.class);

    private static final String DEFAULT_BUILD = "N/A";
    private static final String DEFAULT_VERSION = "0.0.0";
    private static final String NOW_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String BUILD_NOT_FILTERED = "${buildNumber}";
    private static final String TIMESTAMP_NOT_FILTERED = "${timestamp}";

    private static final String VERSION = "Implementation-Version";
    private static final String BUILD = "Implementation-Build";
    private static final String TIME = "Build-Time";

    private static final String VERSION_FILE = "be/milieuinfo/midas/ui/version.properties";

    private static Properties properties;

    /**
     * Constructor VersionUtil creates a new VersionUtil instance.
     */
    private VersionUtil() {
    }

    /**
     * Method getVersion returns the version of this VersionUtil object.
     *
     * @return the version (type String) of this VersionUtil object.
     */
    public static String getVersion() {
        String version = DEFAULT_VERSION;
        if (!Strings.isNullOrEmpty(VersionUtil.getAttribute(VERSION))) {
            version = VersionUtil.getAttribute(VERSION);
        }
        return version;
    }

    /**
     * Method getBuild returns the build of this VersionUtil object.
     *
     * @return the build (type String) of this VersionUtil object.
     */
    public static String getBuild() {
        String build = DEFAULT_BUILD;
        if (!Strings.isNullOrEmpty(VersionUtil.getAttribute(BUILD)) && !VersionUtil.getAttribute(BUILD).equals(BUILD_NOT_FILTERED)) {
            build = VersionUtil.getAttribute(BUILD);
        }

        return build;
    }

    /**
     * Method getBuildTime returns the buildTime of this VersionUtil object.
     *
     * @return the buildTime (type String) of this VersionUtil object.
     */
    public static String getBuildTime() {
        SimpleDateFormat simpleNowDateFormat = new SimpleDateFormat(NOW_FORMAT);
        String buildTime;
        if (!Strings.isNullOrEmpty(VersionUtil.getAttribute(TIME)) && !VersionUtil.getAttribute(TIME).equals(TIMESTAMP_NOT_FILTERED)) {
            buildTime = VersionUtil.getAttribute(TIME);
        } else {
            buildTime = simpleNowDateFormat.format(Calendar.getInstance().getTime());
        }

        return buildTime;
    }

    /**
     * Method getAttribute ...
     *
     * @param attributeName of type String
     * @return String
     */
    private static String getAttribute(String attributeName) {
        String attribute = null;
        Properties versionProperties = getVersionProperties();
        if (versionProperties != null) {
            attribute = versionProperties.getProperty(attributeName);
        }
        return attribute;
    }

    /**
     * Method getVersionProperties will try to load the VERSION_FILE from the {@link ClassLoader} of this class.
     * If this works it will make {@link java.util.Properties} out of the content of the file.
     * It will be only loaded once from the {@link ClassLoader}!
     *
     * @return Manifest
     */
    private static synchronized Properties getVersionProperties() {
        if (properties == null) {
            ClassLoader classLoader = VersionUtil.class.getClassLoader();
            if (classLoader != null) {
                LOGGER.debug("Trying to find [" + VERSION_FILE + "] using " + classLoader + " class loader.");
                URL url = classLoader.getResource(VERSION_FILE);
                properties = new Properties();
                LOGGER.debug("Reading configuration from URL " + url);
                try {
                    properties.load(url.openStream());
                } catch (IOException e) {
                    LOGGER.error("Could not read configuration file from URL [" + url + "].", e);
                }
            }
        }
        return properties;
    }

}
