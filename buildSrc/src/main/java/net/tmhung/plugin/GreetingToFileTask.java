package net.tmhung.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

public class GreetingToFileTask extends DefaultTask {

  public Object destination = null;

  @OutputFile
  File getDestination() {
    return this.getProject().file(destination);
  }

  @TaskAction
  void greet() {
    var file = getDestination();

    try(FileWriter fileWriter = new FileWriter(file);) {
      file.getParentFile().mkdirs();
      fileWriter.write("Hello Writing");
      fileWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
