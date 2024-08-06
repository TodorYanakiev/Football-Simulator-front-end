package com.example.FootballManager_front_end.Controller;

import com.example.FootballManager_front_end.DTO.BaseTeamDTO;
import com.example.FootballManager_front_end.config.BaseTeamClient;
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
@RequestMapping("/base-teams")
@RequiredArgsConstructor
public class BaseTeamController {
    private final BaseTeamClient baseTeamClient;
    private static final String SESSION_NAME = "user";
    private static final String AUTH_HEADER = "Bearer ";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String BASE_TEAM_DTO_NAME = "baseTeamDTO";
    private static final String REDIRECT_INDEX = "redirect:/";
    private static final String LOG_IN_MESSAGE = "You must log in!";
    private static final String PERMISSIONS_MESSAGE = "You do not have permissions for this page!";
    private static final String BASE_TEAM_UPDATE = "base-teams/update";

    @GetMapping("/add")
    public String addBaseTeam(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);
        if (token == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, LOG_IN_MESSAGE);
            return REDIRECT_INDEX;
        }
        if (!model.containsAttribute(BASE_TEAM_DTO_NAME)) {
            model.addAttribute(BASE_TEAM_DTO_NAME, new BaseTeamDTO());
        }
        return "/base-teams/add";
    }

    @PostMapping("/add")
    public String submitBaseTeam(@Valid @ModelAttribute("baseTeamDTO") BaseTeamDTO baseTeamDTO
            , BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String token = (String) session.getAttribute(SESSION_NAME);
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.baseTeamDTO", bindingResult);
                redirectAttributes.addFlashAttribute(BASE_TEAM_DTO_NAME, baseTeamDTO);
                return "redirect:/base-teams/add";
            }
            baseTeamClient.createBaseTeam(AUTH_HEADER + token, baseTeamDTO);
            return "redirect:/base-teams/get";
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        PERMISSIONS_MESSAGE);
                return REDIRECT_INDEX;
            }
        }
        return "redirect:/base-teams/all";
    }

    @GetMapping("/get")
    public String getAllBaseTeams(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String token = (String) session.getAttribute(SESSION_NAME);
            if (token == null) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, LOG_IN_MESSAGE);
                return REDIRECT_INDEX;
            }
            model.addAttribute("baseTeamDTOList", baseTeamClient.getAllBaseTeams(AUTH_HEADER + token).getBody());
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        PERMISSIONS_MESSAGE);
                return REDIRECT_INDEX;
            }
        }
        return "/base-teams/all";
    }

    @GetMapping("/update/{id}")
    public String showUpdateBaseTeamForm(@PathVariable Long id, Model model, HttpSession session
            , RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);
        if (token == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, LOG_IN_MESSAGE);
            return REDIRECT_INDEX;
        }
        try {
            BaseTeamDTO baseTeamDTO = baseTeamClient.getBaseTeamById(AUTH_HEADER + token, id).getBody();
            model.addAttribute(BASE_TEAM_DTO_NAME, baseTeamDTO);
            return BASE_TEAM_UPDATE;
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        PERMISSIONS_MESSAGE);
                return REDIRECT_INDEX;
            }
        }
        return "redirect:/base-teams/all";
    }

    @PostMapping("/update/{id}")
    public String updateBaseTeam(@PathVariable Long id, @Valid @ModelAttribute("baseTeamDTO") BaseTeamDTO baseTeamDTO,
                                 BindingResult bindingResult, Model model, HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);

        if (bindingResult.hasErrors()) {
            model.addAttribute(BASE_TEAM_DTO_NAME, baseTeamDTO);
            return BASE_TEAM_UPDATE;
        }

        try {
            baseTeamClient.updateBaseTeam(AUTH_HEADER + token, id, baseTeamDTO);
            return "redirect:/base-teams/get";
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        PERMISSIONS_MESSAGE);
                return REDIRECT_INDEX;
            }
            model.addAttribute(ERROR_MESSAGE, "Failed to update team details.");
            return BASE_TEAM_UPDATE;
        }
    }
}
