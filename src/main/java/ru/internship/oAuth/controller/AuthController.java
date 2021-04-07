package ru.internship.oAuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.internship.oAuth.exceptions.JWTSecurityException;
import ru.internship.oAuth.services.*;
import ru.internship.oAuth.util.GoogleAPIQueryBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class AuthController {

    private final GoogleAPIQueryBuilder googleAPIQueryBuilder;

    private final SecurityService securityService;

    private final RegistrationService registrationService;

    @Autowired
    public AuthController(GoogleAPIQueryBuilder googleAPIQueryBuilder,
                          SecurityService securityService,
                          RegistrationService registrationService) {
        this.googleAPIQueryBuilder = googleAPIQueryBuilder;
        this.securityService = securityService;
        this.registrationService = registrationService;
    }


    @GetMapping("/signout")
    public String getLoginPage(HttpServletResponse response) {
        securityService.DeleteCoockies(response);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getHomePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("name","new user");
        model.addAttribute("alreadySignedIn", false);
        try {
            if (securityService.isSignedIn(request)) {
                model.addAttribute("name",
                        securityService.getUserAuthentication(request).getName());
                model.addAttribute("alreadySignedIn", true);
            }
        }
        catch (JWTSecurityException e){
            securityService.DeleteCoockies(response);
        }
        return "home";
    }

    @GetMapping("auth/google/callback")
    public String callBackUri(@RequestParam String code, HttpServletResponse response, Model model)  {
        if(code != null)
            try {
                registrationService.GoogleSignIn(code, response);
                return "redirect:/home";
            }
        catch (Exception e){
                model.addAttribute("error", e.getMessage());
                return "error";
        }
        return "redirect:/home";
    }


    @GetMapping("auth/o2login")
    public String redirectToGoogleAuthApi() {
        return googleAPIQueryBuilder.buildClientIdRedirectQuery();
    }

    @GetMapping("/signin")
    public String signinPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("alreadySignedIn", false);
        try {
            if (securityService.isSignedIn(request)) {
                model.addAttribute("name",
                        securityService.getUserAuthentication(request).getName());
                model.addAttribute("alreadySignedIn", true);
            }
        }
        catch (JWTSecurityException e){
            securityService.DeleteCoockies(response);
        }
        return "signin";
    }


}
