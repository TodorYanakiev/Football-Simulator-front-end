package com.example.FootballManager_front_end.Controller;

import com.example.FootballManager_front_end.DTO.BaseFootballPlayerDTO;
import com.example.FootballManager_front_end.config.BasePlayerClient;
import feign.FeignException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/base-players")
@RequiredArgsConstructor
public class BasePlayerController {
    private final BasePlayerClient basePlayerClient;
    private static final String SESSION_NAME = "user";
    private static final String AUTH_HEADER = "Bearer ";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String BASE_PLAYER_DTO_NAME = "basePlayerDTO";
    private static final String REDIRECT_INDEX = "redirect:/";
    private static final String LOG_IN_MESSAGE = "You must log in!";
    private static final String PERMISSIONS_MESSAGE = "You do not have permissions for this page!";
    private static final String BASE_PLAYER_UPDATE = "base-players/update";
    private static final String BASE_PLAYER_ALL = "base-players/all";

    @GetMapping("/all")
    public String getAllBasePlayers(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String token = (String) session.getAttribute(SESSION_NAME);
            if (token == null) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, LOG_IN_MESSAGE);
                return REDIRECT_INDEX;
            }
            model.addAttribute("basePlayersDTOList", basePlayerClient.getAllFootballPlayers(AUTH_HEADER + token).getBody());
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, PERMISSIONS_MESSAGE);
                return REDIRECT_INDEX;
            }
        }
        return "/base-players/all";
    }

    @GetMapping("/add")
    public String addBasePlayer(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);
        if (token == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, LOG_IN_MESSAGE);
            return REDIRECT_INDEX;
        }
        if (!model.containsAttribute(BASE_PLAYER_DTO_NAME)) {
            model.addAttribute(BASE_PLAYER_DTO_NAME, new BaseFootballPlayerDTO());
        }
        return "/base-players/add";
    }

    @PostMapping("/add")
    public String submitBasePlayer(@Valid @ModelAttribute("basePlayerDTO") BaseFootballPlayerDTO basePlayerDTO,
                                   BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String token = (String) session.getAttribute(SESSION_NAME);
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.basePlayerDTO", bindingResult);
                redirectAttributes.addFlashAttribute(BASE_PLAYER_DTO_NAME, basePlayerDTO);
                return "redirect:/base-players/add";
            }
            basePlayerClient.createBaseFootballPlayer(AUTH_HEADER + token, basePlayerDTO);
            return REDIRECT_INDEX + BASE_PLAYER_ALL;
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        PERMISSIONS_MESSAGE);
                return REDIRECT_INDEX;
            }
        }
        return REDIRECT_INDEX + BASE_PLAYER_ALL;
    }

    @GetMapping("/update/{id}")
    public String showUpdateBasePlayerForm(@PathVariable Long id, Model model, HttpSession session
            , RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);
        if (token == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, LOG_IN_MESSAGE);
            return REDIRECT_INDEX;
        }
        try {
            BaseFootballPlayerDTO basePlayerDTO = basePlayerClient.getBaseFootballPlayerById(AUTH_HEADER + token, id).getBody();
            model.addAttribute(BASE_PLAYER_DTO_NAME, basePlayerDTO);
            return BASE_PLAYER_UPDATE;
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        PERMISSIONS_MESSAGE);
                return REDIRECT_INDEX;
            }
        }
        return "redirect:/base-players/all";
    }

    @PostMapping("/update/{id}")
    public String updateBasePlayer(@PathVariable Long id, @Valid @ModelAttribute("basePlayerDTO") BaseFootballPlayerDTO basePlayerDTO,
                                 BindingResult bindingResult, Model model, HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);

        if (bindingResult.hasErrors()) {
            model.addAttribute(BASE_PLAYER_DTO_NAME, basePlayerDTO);
            return BASE_PLAYER_UPDATE;
        }

        try {
            basePlayerClient.updateBaseFootballPlayerById(AUTH_HEADER + token, id, basePlayerDTO);
            return "redirect:/base-players/all";
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        PERMISSIONS_MESSAGE);
                return REDIRECT_INDEX;
            }
            model.addAttribute(ERROR_MESSAGE, "Failed to update player details.");
            return BASE_PLAYER_UPDATE;
        }
    }
}
