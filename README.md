#### Description
A simple Gradle plugin inserts a VTL script to another VTL script. The plugin can be applied to any type of text file
, not limited to VTL scripts though.

#### Usage
The `filterVelocityScripts` should be executed after `processResorouces`. By default, it picks up all files in the
 resources which match `**/*.vtl`
 
```
// File: build.gradle.kts
// Specify the task to executed at the end of `processResources`
tasks {
    withType<ProcessResources> {
        finalizedBy("filterVelocityScripts")
    }
}

// Change to a different file pattern
configure<net.tmhung.plugin.VelocityPluginExtension> {
    filePattern = "velocity/*.vtl"
}
```



#### Syntax
To insert a script, use the `include` token.
```
_include: filepath_

 filepath - relative to the current file or the resources directory. It must not include './' 
```
For example:
```
_include: authenitcation.vtl_
_include: common/convert_json_response.vtl_
```
