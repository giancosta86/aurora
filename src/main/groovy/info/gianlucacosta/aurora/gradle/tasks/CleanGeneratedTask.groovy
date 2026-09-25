package info.gianlucacosta.aurora.gradle.tasks

import org.gradle.api.tasks.Delete

/**
 * Deletes <b>src/generated</b>
 */
class CleanGeneratedTask extends Delete {
    CleanGeneratedTask() {
        setDelete('src/generated')
    }
}
