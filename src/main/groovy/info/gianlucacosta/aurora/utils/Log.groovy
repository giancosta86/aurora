package info.gianlucacosta.aurora.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Log {
    static Logger logger = LoggerFactory.getLogger('aurora')

    static def debug(String message) {
        logger.debug(message)
    }

    static def info(String message) {
        logger.info(message)
    }
}
