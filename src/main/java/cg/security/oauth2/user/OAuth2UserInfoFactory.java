package cg.security.oauth2.user;

import cg.exception.OAuth2AuthenticationProcessingException;
import cg.model.enums.EAuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    //class patten phân loại tài khoản đăng nhập thông qua google hay facebook
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(EAuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(EAuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
