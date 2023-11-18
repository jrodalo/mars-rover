package es.leanmind.marsrover;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "app")
public record AppConfiguration(
    int width,
    int height,
    int initialNumberOfRocks
) {
}
