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

configure<net.tmhung.plugin.GreetingPluginExtension> {
    greeter = "Gradle"
    message = "Hello World"
}
