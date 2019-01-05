import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val gradleWrapperVersion: String by project
val kotlinVersion: String by project

plugins {
  val kotlinVersion = "1.3.11"
  kotlin("jvm") version kotlinVersion
}

dependencies {
  implementation(kotlin("stdlib", kotlinVersion))
  implementation(kotlin("stdlib-jdk7", kotlinVersion))
  implementation(kotlin("stdlib-jdk8", kotlinVersion))
  implementation(kotlin("reflect", kotlinVersion))

  implementation(group = "com.amazon.alexa", name = "alexa-skills-kit", version = "1.3.0")
  implementation(group = "com.amazonaws", name = "aws-lambda-java-core", version = "1.1.0")
  runtimeOnly(group = "com.amazonaws", name = "aws-lambda-java-log4j", version = "1.0.0")
  implementation(group = "com.amazonaws", name = "aws-java-sdk-dynamodb", version = "1.11.125")
  implementation(group = "com.google.code.gson", name = "gson", version = "2.8.0")
  implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "3.7.0")

  testImplementation(kotlin("test", kotlinVersion))
  testImplementation(kotlin("test-junit5", kotlinVersion))
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
  }

  withType<Test> {
    testLogging.showStandardStreams = true
  }

  withType<Jar> {
    /*manifest {
        attributes["Main-Class"] = application.mainClassName
    }
    from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })*/
  }

  withType<GradleBuild> {
    finalizedBy("publishToMavenLocal")
  }

  withType<Wrapper> {
    gradleVersion = gradleWrapperVersion
    distributionType = Wrapper.DistributionType.ALL
  }

}

val fatJar = task("fatJar", type = Jar::class) {

  baseName = project.name + "-fat"
  version = "${project.version}"

  from(configurations["runtime"].files.map { if (it.isDirectory) it else zipTree(it) })
  with(tasks["jar"] as CopySpec)

}

// Run the standard task build with the task called "fatJar".
tasks {
  "assemble" {
    dependsOn(fatJar)
  }
  "build" {
    dependsOn(fatJar)
  }
}
