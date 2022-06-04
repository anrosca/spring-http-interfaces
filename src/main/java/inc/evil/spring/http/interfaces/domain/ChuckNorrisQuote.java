package inc.evil.spring.http.interfaces.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChuckNorrisQuote {
    private String value;
    private String url;
    private String id;
    @JsonProperty("icon_url")
    private String iconUrl;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
}
