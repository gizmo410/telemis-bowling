// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.turbo.MarkerFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.spi.FilterReply

import static ch.qos.logback.classic.Level.*

//def USER_HOME = System.getProperty("user.home")
def LOG_DIR = System.getProperty("logging.dir")

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}
// https://code.google.com/p/log4jdbc-log4j2/
appender("log4jdbc_file", FileAppender) {
    file = "${LOG_DIR}/log4jdbc.log"
    turboFilter(MarkerFilter) {
        marker = "LOG4JDBC_OTHER"
        onMatch = FilterReply.DENY
        onMismatch = FilterReply.NEUTRAL
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%t] %level - %m%n%ex%n"
    }
}
logger("be.milieuinfo.midas", DEBUG)
logger("org.axonframework", DEBUG)
logger("org.springframework.beans", WARN)
logger("org.springframework.context", WARN)
logger("org.springframework.scheduling", WARN)
logger("org.springframework.jmx", WARN)
logger("org.springframework.core.env", WARN)
logger("org.springframework.web.servlet.mvc", DEBUG)
logger("org.springframework.web.servlet", DEBUG)
logger("org.springframework.web.socket", DEBUG)
logger("org.springframework.web.socket.sockjs.support", DEBUG)
logger("org.springframework.data", DEBUG)
logger("org.springframework.security", WARN)
logger("org.hibernate", DEBUG)
logger("log4jdbc.log4j2", ERROR, ["log4jdbc_file"], false)
root(INFO, ["CONSOLE"])