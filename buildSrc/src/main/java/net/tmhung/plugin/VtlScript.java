package net.tmhung.plugin;

import java.nio.file.Path;
import java.util.List;

/**
 * A vtl script with its tokens
 */
public class VtlScript {

  private final Path filePath;
  private final List<IncludeToken> tokens;

  public VtlScript(Path filePath, List<IncludeToken> tokens) {
    this.filePath = filePath;
    this.tokens = tokens;
  }

  public Path getFilePath() {
    return filePath;
  }

  public List<IncludeToken> getTokens() {
    return tokens;
  }
}
