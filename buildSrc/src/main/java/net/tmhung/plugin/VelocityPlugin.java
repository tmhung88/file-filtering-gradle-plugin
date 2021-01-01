package net.tmhung.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * <p>A velocity plugin which supports:</p>
 * - filter out tokens: _include: "filename"_
 */
public class VelocityPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    var extension = project.getExtensions().create("velocity", VelocityPluginExtension.class);
    project.task("filterVelocityScripts").doLast(task -> filterVelocityScripts(task, project, extension));
  }

  /**
   * <p>A task which is to filter out tokens: </p>
   * <p>
   * - include: insert a script into another script
   */
  private void filterVelocityScripts(Task task, Project project, VelocityPluginExtension extension) {
    ScriptScanner scriptScanner = new ScriptScanner();
    var vtlScripts = scriptScanner.scan(project, extension.getFilePattern());
    var tokenFilter = new TokenFilter();
    tokenFilter.filter(vtlScripts);
    System.out.println(String.format("# FilterVelocityScriptsTask: %s VTL scripts resolved", vtlScripts.size()));
  }
}