package info.gianlucacosta.aurora.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.rules.TemporaryFolder

class DslTest extends GroovyTestCase {
    private TemporaryFolder tempFolder
    private Project project

    @Override
    void setUp() {
        super.setUp()

        tempFolder = new TemporaryFolder()
        tempFolder.create()

        project = ProjectBuilder.builder()
                .withName("AuroraTest")
                .withProjectDir(tempFolder.getRoot())
                .build()

        project.plugins.apply("scala")
        project.plugins.apply("info.gianlucacosta.aurora")

        project.group = "alpha"
        project.archivesBaseName = "beta"


        project.description = "A test project"
    }


    private void applyDefaultWith(Closure closure) {
        project.aurora {
            gitHubUser = "anyUser"

            author {
                name = "TestAuthor"
                email = "test@localhost"
                url = "localhost"
            }

            closure.delegate = delegate
            closure.resolveStrategy = Closure.DELEGATE_FIRST
            closure()
        }
    }


    @Override
    void tearDown() {
        tempFolder.delete()

        super.tearDown();
    }

    void test_defaultConfiguration() {
        applyDefaultWith {}
    }

    void test_auroraSettingsAvailability() {
        applyDefaultWith {}

        assertEquals(
                "anyUser",
                project.auroraSettings.gitHubUser
        )
    }

    void test_requiredJavaVersion() {
        applyDefaultWith {
            javaVersion {
                major = 1
                minor = 7
                build = 5
                update = 64
            }
        }

        assertEquals(
                1,
                project.auroraSettings.requiredJavaVersion.major
        )

        assertEquals(
                7,
                project.auroraSettings.requiredJavaVersion.minor
        )

        assertEquals(
                5,
                project.auroraSettings.requiredJavaVersion.build
        )

        assertEquals(
                64,
                project.auroraSettings.requiredJavaVersion.update
        )
    }


    void test_requiredJavaVersionWithDefaults() {
        applyDefaultWith {
            javaVersion {
                major = 1
                minor = 8
                update = 91
            }
        }

        assertEquals(
                1,
                project.auroraSettings.requiredJavaVersion.major
        )

        assertEquals(
                8,
                project.auroraSettings.requiredJavaVersion.minor
        )

        assertEquals(
                0,
                project.auroraSettings.requiredJavaVersion.build
        )

        assertEquals(
                91,
                project.auroraSettings.requiredJavaVersion.update
        )
    }
}
