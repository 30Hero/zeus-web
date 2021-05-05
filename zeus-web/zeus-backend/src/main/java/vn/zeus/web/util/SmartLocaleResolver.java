package vn.zeus.web.util;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class SmartLocaleResolver extends CookieLocaleResolver {

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    Locale locale = super.determineDefaultLocale(request);
    if (null == locale) {
      String acceptLanguage = request.getHeader("Accept-Language");
      if (acceptLanguage != null && !acceptLanguage.trim().isEmpty()) {
        locale = request.getLocale();
      }
    }
    return locale;
  }
}
