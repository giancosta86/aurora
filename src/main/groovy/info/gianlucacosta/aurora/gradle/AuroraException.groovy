package info.gianlucacosta.aurora.gradle

import org.gradle.api.GradleScriptException

/**
 * Fatal exception related to the Aurora build process
 */
class AuroraException extends GradleScriptException {
    AuroraException(String message) {
        super(message, null)
    }

    AuroraException(String message, Throwable cause) {
        super(message, cause)
    }
}
