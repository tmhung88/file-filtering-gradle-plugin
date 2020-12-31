plugins {
    java
    id("net.tmhung.plugin")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())
}

tasks.register<net.tmhung.plugin.GreetingToFileTask>("greetToFile") {
    destination = { project.extra["greetingFile"]!! }
}

tasks.register("sayGreeting") {
    dependsOn("greetToFile")
    doLast {
        println(file(project.extra["greetingFile"]!!).readText())
    }
}

extra["greetingFile"] = "$buildDir/hello.txt"