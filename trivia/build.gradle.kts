import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val gradleWrapperVersion: String by project
val kotlinVersion: String by project
val springBootVersion: String by project

plugins {
  val kotlinVersion = "1.3.11"

  application
  kotlin("jvm") version kotlinVersion
  id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
  id("org.springframework.boot") version "2.1.1.RELEASE"
  id("io.spring.dependency-management").version("1.0.6.RELEASE")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
  /** kotlin */
  compile(kotlin("stdlib-jdk8", kotlinVersion))
  compile(kotlin("reflect", kotlinVersion))
  compile(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.9.7")
  compile(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.9.7")

  /** alexa */
  compile(group = "com.amazon.alexa", name = "alexa-skills-kit", version = "1.8.1"){
    exclude("org.slf4j")
  }

  /** alexa */
  compile(group ="org.springframework.boot", name = "spring-boot-starter-web", version = springBootVersion)


  /** test */
  testCompile("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
  }

  withType<Test> {
    testLogging {
      events("passed", "skipped", "failed")
    }
  }

  withType<Jar> {
    enabled = true
  }

  withType<BootJar> {
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
      mainClassName = "io.anaxo.alexa.trivia.AlexSpringBootWebApplicationKt"
    }
  }
}
