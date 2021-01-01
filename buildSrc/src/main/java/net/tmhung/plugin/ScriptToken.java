package net.tmhung.plugin;

import java.util.regex.Pattern;

public class ScriptToken {

  /**
   * Include _include:...._
   */
  public static String INCLUDED_REGEX = "(\\w*)(?<=_include).*?(?=_)(\\w*)";
  /**
   * Not include _include:...._
   */
  public static String EXCLUDED_REGEX = "(?<=_include:).*?(?=_)";
  public static Pattern INCLUDED_PATTERN = Pattern.compile(INCLUDED_REGEX);
  public static Pattern EXCLUDED_PATTERN = Pattern.compile(EXCLUDED_REGEX);

  private String token;

  public ScriptToken(String token) {
    this.token = token;
  }

  public String getScriptName() {
    var matcher = EXCLUDED_PATTERN.matcher(token);
    matcher.find();
    return matcher.group().toLowerCase().trim();
  }

  public String getToken() {
    return token;
  }

  @Override
  public String toString() {
    return "ScriptToken{" +
        "token='" + token + '\'' +
        '}';
  }
}
