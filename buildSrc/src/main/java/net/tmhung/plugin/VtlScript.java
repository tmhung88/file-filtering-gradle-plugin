package net.tmhung.plugin;

import java.nio.file.Path;
import java.util.List;

public class VtlScript {

  private final Path filePath;
  private final List<ScriptToken> tokens;

  public VtlScript(Path filePath, List<ScriptToken> tokens) {
    this.filePath = filePath;
    this.tokens = tokens;
  }

  public Path getFilePath() {
    return filePath;
  }

  public List<ScriptToken> getTokens() {
    return tokens;
  }
}
