package cg.controller;

import cg.model.user.User;
import cg.service.customer.ICustomerService;
import cg.service.user.IUserService;
import cg.utils.GooglePojo;
import cg.utils.GoogleUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {
    @Autowired
    private GoogleUtils googleUtils;

    @Autowired
    private IUserService userService;

    @GetMapping("/")
    public String showHomePage(){
        return "index";
    }
    @GetMapping("/home")
    public String showListProduct(){
        return "index";
    }
//    @GetMapping("/create")
//    public String showAddProduct(){
//        return "product/add-product";
//    }

    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        System.out.println("sdklákláklá");

        return "login";
    }

//    @GetMapping("/google")
//    public String toLoginGooglePage(HttpServletRequest request, HttpServletResponse response){
//        //đăng nhập thành công google => code xác thực trả về ở url => trả về GetMapping
//        return "login-goole";
//    }

}
