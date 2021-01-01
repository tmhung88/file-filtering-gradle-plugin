package net.tmhung.plugin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A VTL script with its dependent scripts
 */
public class DependencyVtlScript {

  private final List<DependencyVtlScript> dependencies = new LinkedList<>();

  private final VtlScript vtlScript;

  private boolean resolved = false;

  public DependencyVtlScript(VtlScript vtlScript) {
    this.vtlScript = vtlScript;
  }

  /**
   * Mark the script as resolved which means all of its dependencies resolved
   */
  public DependencyVtlScript markResolved() {
    resolved = true;
    return this;
  }

  public boolean isResolved() {
    return resolved;
  }

  public List<DependencyVtlScript> getDependencies() {
    return new ArrayList<>(dependencies);
  }

  /**
   * Get a list of unresolved dependencies
   */
  public List<DependencyVtlScript> getUnresolvedDependencies() {
    return dependencies.stream().filter(dependency -> !dependency.isResolved()).collect(Collectors.toList());
  }

  public DependencyVtlScript addDependency(DependencyVtlScript script) {
    dependencies.add(script);
    return this;
  }

  public List<IncludeToken> getDependencyTokens() {
    return vtlScript.getTokens();
  }

  public VtlScript getVtlScript() {
    return vtlScript;
  }

  private VtlScript getDependencyByToken(IncludeToken token) {
    for (var script : dependencies) {
      var absolutePath = script.vtlScript.getFilePath().toFile().getAbsolutePath().toLowerCase();
      if (absolutePath.contains(token.getScriptName())) {
        return script.vtlScript;
      }
    }
    throw new RuntimeException(String.format("No dependency found for %s", token.toString()));
  }
}
