import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val gradleWrapperVersion: String by project
val kotlinVersion: String by project
val springBootVersion: String by project
val spekVersion: String by project
val junitVersion: String by project

plugins {
  val kotlinVersion = "1.3.11"

  groovy
  application
  kotlin("jvm") version kotlinVersion
  kotlin("kapt") version kotlinVersion
  kotlin("plugin.allopen") version kotlinVersion
  id("io.spring.dependency-management") version "1.0.6.RELEASE"
  id("com.github.johnrengelman.shadow") version "4.0.2"
}

dependencyManagement {
  imports {
    mavenBom("io.micronaut:micronaut-bom:1.0.2")
  }
}

dependencies {
  /** kotlin */
  implementation(kotlin("stdlib-jdk8", kotlinVersion))
  implementation(kotlin("reflect", kotlinVersion))
  implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.9.7")
  implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.9.7")

  /** alexa */
  implementation(group = "com.amazon.alexa", name = "ask-sdk", version = "2.11.1") {
    exclude("org.apache.logging.log4j")
  }

  /** micronaut */
  annotationProcessor("io.micronaut:micronaut-inject-java")
  implementation("io.micronaut:micronaut-http-client")
  implementation("io.micronaut:micronaut-http-server-netty")
  compile("io.micronaut:micronaut-inject")
  implementation("io.micronaut:micronaut-runtime")
  kapt("io.micronaut:micronaut-inject-java")
  kapt("io.micronaut:micronaut-validation")

  implementation("org.apache.commons:commons-lang3:3.8.1")
  implementation("org.mapdb:mapdb:3.0.7")

  runtime("ch.qos.logback:logback-classic:1.2.3")

  /** test */
  kaptTest("io.micronaut:micronaut-inject-java")
  testImplementation("io.micronaut:micronaut-inject-java")
  testImplementation("io.micronaut:micronaut-inject-groovy")
  testImplementation("org.spockframework:spock-core:1.2-groovy-2.5")
  testImplementation( "cglib:cglib-nodep:3.2.10")
  testImplementation("org.objenesis:objenesis:3.0.1")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    kotlinOptions.javaParameters = true
  }

  withType<Test> {
    testLogging {
      events("passed", "skipped", "failed")
    }
  }

  withType<ShadowJar> {
    mergeServiceFiles()
  }

  withType<Jar> {
    enabled = true
  }

  withType<GradleBuild> {
    finalizedBy("publishToMavenLocal")
  }

  withType<Wrapper> {
    gradleVersion = gradleWrapperVersion
    distributionType = Wrapper.DistributionType.ALL
  }

  withType<JavaExec> {
    application {
      mainClassName = "io.anaxo.alexa.napster.Application"
    }
  }
}
