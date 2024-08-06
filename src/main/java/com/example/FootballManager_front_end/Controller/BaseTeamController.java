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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/add")
    public String addBaseTeam(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute(SESSION_NAME);
        if (token == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "You must log in!");
            return REDIRECT_INDEX;
        }
        if (!model.containsAttribute(BASE_TEAM_DTO_NAME)) {
            model.addAttribute(BASE_TEAM_DTO_NAME, new BaseTeamDTO());
        }
        return "/base-teams/add";
    }

    @PostMapping("/add")
    public String submitBaseTeam(@Valid @ModelAttribute("baseTeamDTO") BaseTeamDTO baseTeamDTO
            , BindingResult bindingResult, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
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
                        "You do not have permissions for this page!");
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
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "You must log in!");
                return REDIRECT_INDEX;
            }
            model.addAttribute("baseTeamDTOList", baseTeamClient.getAllBaseTeams(AUTH_HEADER + token).getBody());
        } catch (FeignException exception) {
            if (exception.status() == 401) {
                redirectAttributes.addFlashAttribute(ERROR_MESSAGE,
                        "You do not have permissions for this page!");
                return REDIRECT_INDEX;
            }
        }
        return "/base-teams/all";
    }


}
