import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val gradleWrapperVersion: String by project
val kotlinVersion: String by project

plugins {
    val kotlinVersion = "1.3.10"
    kotlin("jvm") version kotlinVersion
}

dependencies {
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("stdlib-jdk7", kotlinVersion))
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))

    testImplementation(kotlin("test", kotlinVersion))
    testImplementation(kotlin("test-junit5", kotlinVersion))
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    }

    withType<Test> {
        testLogging.showStandardStreams = true
    }

    withType<Jar> {
        /*manifest {
            attributes["Main-Class"] = application.mainClassName
        }
        from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })*/
        finalizedBy(sourcesJar)
    }

    withType<GradleBuild> {
        finalizedBy("publishToMavenLocal")
    }

    withType<Wrapper> {
        gradleVersion = gradleWrapperVersion
        distributionType = Wrapper.DistributionType.ALL
    }

}

/*gradle.taskGraph.whenReady {
    def createIfNotExists = { File dir ->
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    String sourceSetName = project.plugins.hasPlugin("groovy") ? "groovy" : "java"
    sourceSets.main[sourceSetName].srcDirs.each(createIfNotExists)
    sourceSets.test[sourceSetName].srcDirs.each(createIfNotExists)
    sourceSets.main.resources.srcDirs.each(createIfNotExists)
    sourceSets.test.resources.srcDirs.each(createIfNotExists)
}

wrapper {
    gradleVersion = '5.0'
}*/
