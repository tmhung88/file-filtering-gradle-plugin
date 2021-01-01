package net.tmhung.plugin;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import org.gradle.api.Project;

/**
 * Scan a project's directory with the given file pattern for vtl scripts
 */
public class ScriptScanner {

  public List<VtlScript> scan(Project project, String filePattern) {
    var buildDir = project.getBuildDir();
    Path resourcesPath = Paths.get(buildDir.getAbsolutePath(), "resources");
    List<Path> paths = findRelevantFiles(resourcesPath, filePattern);
    return paths.stream().map(this::analyze).collect(Collectors.toList());
  }

  public VtlScript analyze(Path filePath) {
    try {
      String fileContent = Files.readString(filePath);
      Matcher matcher = IncludeToken.INCLUDED_PATTERN.matcher(fileContent);
      List<IncludeToken> scriptTokens = new LinkedList<>();
      while (matcher.find()) {
        scriptTokens.add(new IncludeToken(matcher.group()));
      }
      return new VtlScript(filePath, scriptTokens);
    } catch (IOException e) {
      throw new RuntimeException("Failed to analyze a VTL script", e);
    }
  }

  /**
   * Recursively find all matching files under the given directory
   *
   * @param rootPath
   * @param filePattern
   * @return
   */
  private List<Path> findRelevantFiles(Path rootPath, String filePattern) {
    var pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + filePattern);
    try {
      return Files.walk(rootPath)
          .filter(Files::isRegularFile)
          .filter(pathMatcher::matches)
          .sorted()
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(String.format("Failed to search directory [%s] for file pattern [%s]",
          rootPath.toFile().getAbsolutePath(), filePattern));
    }
  }
}
