val gradleWrapperVersion: String by project

buildscript {
  repositories {
     jcenter()
  }

  dependencies {
     classpath("org.kt3k.gradle.plugin:coveralls-gradle-plugin:2.6.3")
     classpath("nl.javadude.gradle.plugins:license-gradle-plugin:0.11.0")
     classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.6")
     classpath("org.ajoberstar:gradle-git:1.3.2")
     classpath("org.kordamp.gradle:stats-gradle-plugin:0.2.0")
     classpath("com.github.ben-manes:gradle-versions-plugin:0.12.0")
     classpath("net.nemerosa:versioning:2.1.0")
  }
}

allprojects {
}

subprojects {

  repositories {
     jcenter()
  }

  apply(plugin="idea")
  apply(plugin="java")
  apply(plugin="com.github.kt3k.coveralls")
  apply(plugin="build-dashboard")
  apply(plugin="org.kordamp.gradle.stats")
  apply(plugin="com.github.ben-manes.versions")
  apply(from="../gradle/publishing.gradle")
  apply(from="../gradle/code-coverage.gradle.kts")
  apply(from="../gradle/code-quality.gradle")

}

tasks {
  withType<Wrapper> {
    gradleVersion = gradleWrapperVersion
    distributionType = Wrapper.DistributionType.ALL
  }
}
