package net.tmhung.plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class TokenFilter {

  public void filter(List<VtlScript> vtlScripts) {
    var unresolvedScripts = setUpDependencies(vtlScripts);
    while (unresolvedScripts.size() > 0) {
      boolean hasResolved = false;
      for (var unresolvedScript : unresolvedScripts) {
        if (unresolvedScript.getUnresolvedDependencies().size() == 0) {
          filter(unresolvedScript);
          hasResolved = true;
        }
      }

      if (!hasResolved) {
        throw new RuntimeException("Not able to resolve remaining tokens");
      }
      unresolvedScripts = unresolvedScripts.stream().filter(token -> !token.isResolved()).collect(Collectors.toList());
    }
  }

  private void filter(DependencyVtlScript script) {
    var vtlScript = script.getVtlScript();
    if (vtlScript.getTokens().size() == 0) {
      script.markResolved();
      return;
    }

    try {
      String scriptContent = Files.readString(vtlScript.getFilePath());
      for (var scriptToken : vtlScript.getTokens()) {
        var dependencyScript = this.getDependencyByToken(scriptToken, script.getDependencies());
        var dependencyContent = Files.readString(dependencyScript.getVtlScript().getFilePath());
        scriptContent = scriptContent.replaceAll(scriptToken.getToken(), dependencyContent);
      }
      Files.writeString(vtlScript.getFilePath(), scriptContent);
      script.markResolved();
    } catch (IOException e) {
      throw new RuntimeException(String.format("Failed to resolve vtlScript %s", vtlScript.getFilePath()), e);
    }
  }

  private List<DependencyVtlScript> setUpDependencies(List<VtlScript> scripts) {
    var dependencyScripts = scripts.stream().map(DependencyVtlScript::new).collect(Collectors.toList());
    dependencyScripts.stream().forEach(script -> {
      List<DependencyVtlScript> dependencies =
          script.getDependencyTokens().stream().map(token -> getDependencyByToken(token,
              dependencyScripts)).collect(Collectors.toList());
      dependencies.forEach(script::addDependency);
    });
    return dependencyScripts;
  }

  private DependencyVtlScript getDependencyByToken(ScriptToken token, List<DependencyVtlScript> allDependencies) {
    for (var script : allDependencies) {
      var absolutePath = script.getVtlScript().getFilePath().toFile().getAbsolutePath().toLowerCase();
      if (absolutePath.contains(token.getScriptName())) {
        return script;
      }
    }
    throw new RuntimeException(String.format("No dependency found for %s", token.toString()));
  }
}
