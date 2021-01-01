package net.tmhung.plugin;

import java.util.regex.Pattern;

/**
 * A token to include a script into another script.<br/>
 * <p><b>_include: "filePath"_</b></p>
 * <br/> filepath - relative to the resources directory and doesn't include './' </br>
 * <p>
 * Example:<br/> _include: organization_admin_authorization.vtl_<br/> _include common/authentication.vtl_<br/> _include:
 * common/utility/log_identity.vtl_<br/>
 * </p>
 */
public class IncludeToken {

  /**
   * A regex that matches the whole Include token: _include: "filename"_
   */
  public static String INCLUDED_REGEX = "(\\w*)(?<=_include).*?(?=_)(\\w*)";

  /**
   * A regex that matches file name of the Include token: "filename"
   */
  public static String EXCLUDED_REGEX = "(?<=_include:).*?(?=_)";
  public static Pattern INCLUDED_PATTERN = Pattern.compile(INCLUDED_REGEX);
  public static Pattern EXCLUDED_PATTERN = Pattern.compile(EXCLUDED_REGEX);

  private String token;

  public IncludeToken(String token) {
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
    return "IncludeToken{" +
        "token='" + token + '\'' +
        '}';
  }
}
