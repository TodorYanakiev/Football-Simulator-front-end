package com.example.FootballManager_front_end.Controller;

import com.example.FootballManager_front_end.DTO.UserDTO;
import com.example.FootballManager_front_end.DTO.request.AuthenticationRequest;
import com.example.FootballManager_front_end.DTO.request.RegisterRequest;
import com.example.FootballManager_front_end.DTO.response.AuthenticationResponse;
import com.example.FootballManager_front_end.auth.AuthClient;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthClient authClient;
    private static final String SESSION_NAME = "user";
    private static final String AUTH_HEADER = "Bearer ";
    private static final String USERS = "users";
    private static final String USER_REGISTER_FILE = "/user/register";

    @GetMapping("/register")
    public String createUser(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return USER_REGISTER_FILE;
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerRequest", new RegisterRequest());
            return USER_REGISTER_FILE;
        }
        try {
            authClient.register(request);
        } catch (FeignException e) {
            if (e.status() == 400) {
                model.addAttribute("registerRequest", new RegisterRequest());
                model.addAttribute("errorMessage", "User with such email already exists!");
                return USER_REGISTER_FILE;
            }
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showUserLogin(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        return "/user/login";
    }

    @PostMapping("/login")
    public String authenticateUser(@Valid AuthenticationRequest request, HttpSession session
            , Model model, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<AuthenticationResponse> authResponse = authClient.authenticate(request);
            String token = Objects.requireNonNull(authResponse.getBody()).getToken();
            ResponseEntity<UserDTO> userDTO = authClient.getUserInfo(AUTH_HEADER + token);
            session.setAttribute(SESSION_NAME, token);
            session.setAttribute("info", userDTO.getBody());
            return "redirect:/auth/get-info";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid credentials or error");
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        return "redirect:/index";
    }

    @GetMapping("/get-info")
    public String userInfo(Model model, HttpSession session) {
        String token = (String) session.getAttribute(SESSION_NAME);
        model.addAttribute("userDTO", authClient.getUserInfo(AUTH_HEADER + token).getBody());
        return "/user/me";
    }
}
