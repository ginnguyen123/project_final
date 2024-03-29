package cg.security.oauth2.user;

import java.util.Map;

public abstract class OAuth2UserInfo {
    // abstract class cho thông tin user trả về từ giao thức OAuth2
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
            return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
