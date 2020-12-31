package net.tmhung.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GreetingPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    GreetingPluginExtension extension = project.getExtensions().create("greeting", GreetingPluginExtension.class);

    project.task("hello").doLast(task -> {
          System.out.println(extension.getGreeter());
          System.out.println(extension.getMessage());
        }
    );
  }
}