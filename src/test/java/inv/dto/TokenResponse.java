package inv.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
    private String token;
    private long expires;
    private String expires_string;
}
