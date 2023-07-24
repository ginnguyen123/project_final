package cg.security.oauth2;

import cg.exception.OAuth2AuthenticationProcessingException;
import cg.model.customer.Customer;
import cg.model.enums.EAuthProvider;
import cg.model.enums.ERole;
import cg.model.user.Role;
import cg.model.user.User;
import cg.repository.CustomerRepository;
import cg.repository.UserRepository;
import cg.security.oauth2.user.OAuth2UserInfo;
import cg.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        System.out.println("-------------loadUser----------");
        OAuth2User oAuth2User =  super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User); // check, xử lý thông tin người dùng đăng nhập qua OAuth2UserRequest đã có ở db hay chưa
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User){
        // phân loại user đăng nhập qua facebook hay google + lấy thông tin user
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId()
                                                                                ,oAuth2User.getAttributes());
        //check user có username hay không
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        //check username đã có ở db hay chưa
        Optional<User> userOp = userRepository.findByUsername(oAuth2UserInfo.getEmail());
        User user;
        if (userOp.isPresent()){
            user = userOp.get();
            //check username đã đăng nhập qua facebook hay email trước đó chưa, rồi thì bắt đăng nhập qua tài khoản trước
            if (!user.getProvider().equals(EAuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))){
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            // nếu username đăng nhập qua facebook hoặc google lần đầu, usernaem đã có ở db => update lại tài khoản
            user = updateExistingUser(user, oAuth2UserInfo);
        }else {
            //tài khoản mới hoàn toàn
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return null;
    }


    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        //tạo 1 tài khoản mới nếu username không có ở db
        User newUser = new User();
        Customer newCustomer = new Customer();

        newUser.setId(null);
        newUser.setUsername(oAuth2UserInfo.getEmail());
        newUser.setPassword("");
        newUser.setProvider(EAuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        Role role = new Role(3L, null, ERole.ROLE_CUSTOMER);
        newUser.setRole(role);

        newCustomer.setUser(newUser);
        newCustomer.setId(null);
        newCustomer.setFullName(oAuth2UserInfo.getName());
        newCustomer.setEmail(oAuth2UserInfo.getEmail());

        newUser = userRepository.save(newUser);
        customerRepository.save(newCustomer);

        return newUser;
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        //cập nhật lại thông tin nếu user đã tồn tại ở db

        return userRepository.save(existingUser);

    }
}
