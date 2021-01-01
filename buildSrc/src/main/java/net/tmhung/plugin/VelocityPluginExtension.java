package net.tmhung.plugin;

/**
 * Configuration for Velocity plugin. <br/>
 * <p>
 * - filePattern: ant-style glob pattern
 */
public class VelocityPluginExtension {

  private String filePattern = "**/*.vtl";

  public String getFilePattern() {
    return filePattern;
  }

  public void setFilePattern(String filePattern) {
    this.filePattern = filePattern;
  }
}