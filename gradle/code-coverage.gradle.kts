import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport

apply {
    plugin<JacocoPlugin>()
}

tasks.withType<Test> {
    testLogging.events("passed", "skipped", "failed")
    testLogging.showStandardStreams = true
    testLogging.setExceptionFormat("full")
}

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

configure<JacocoPluginExtension> {
    toolVersion = "0.8.2"
}


