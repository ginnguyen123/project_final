package cg.api;

import cg.dto.roleDTO.RoleDTO;
import cg.dto.userDTO.UserClientResRegisterDTO;
import cg.dto.userDTO.UserLoginReqDTO;
import cg.exception.DataInputException;
import cg.exception.EmailExistsException;
import cg.model.JwtResponse;
import cg.model.user.Role;
import cg.model.user.User;
import cg.service.customer.ICustomerService;
import cg.service.jwt.JwtService;
import cg.service.role.IRoleService;
import cg.service.user.IUserService;
import cg.utils.AppConstant;
import cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
    @RequestMapping("/api/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/client/register")
    public ResponseEntity<?> register(@RequestBody @Validated UserClientResRegisterDTO userResRegister){
        RoleDTO role = userResRegister.getRole();
        role.setId(3l);
        role.setCode("CUSTOMER");
        userResRegister.setRole(role);
        Boolean isUserName = userService.existsByUsername(userResRegister.getUsername());
        Boolean isEmail = customerService.isEmail(userResRegister.getCustomer().getEmail());

        if (isUserName)
            throw new EmailExistsException(AppConstant.ACCOUNT_EXISTS);

        if (isEmail)
            throw new EmailExistsException(AppConstant.EMAIL_EXISTS);

        Optional<Role> roleOp = roleService.findById(userResRegister.getRole().getId());

        if (!roleOp.isPresent()){
            throw new DataInputException("Invalid account role");
        }

        try{
            userService.register(userResRegister);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e){
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginReqDTO userLogin){
        try{
//        tạo đối tượng xác thực authentication thông qua authenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
//        lưu trữ đối tượng xác thực vào SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
//        tạo JWT mới cho đối tượng xác thực authentication
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        call ra User đang đăng nhập
            User currentUser = userService.getByUsername(userDetails.getUsername());
//
            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    currentUser.getUsername(),
                    userDetails.getAuthorities()
            );
//        lưu mã xác thực JWT xuống cookie + set time + set domain
            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 1000) //time sống 1h
                    .domain("localhost")
                    .build();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
