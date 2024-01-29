package de.fewobacher.properties;

import de.fewobacher.constant.ErrorLevel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = ApplicationProperties.PROPERTIES_PREFIX)
public class ApplicationProperties
{
  static final String PROPERTIES_PREFIX = "qr";

  Integer defaultSize;

  ErrorLevel defaultErrorLevel;
}
