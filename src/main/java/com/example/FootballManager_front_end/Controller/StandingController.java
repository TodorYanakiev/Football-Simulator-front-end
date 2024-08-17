package com.example.FootballManager_front_end.Controller;

import com.example.FootballManager_front_end.DTO.response.StandingResponse;
import com.example.FootballManager_front_end.config.StandingClient;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/standings")
@RequiredArgsConstructor
public class StandingController {
    private final StandingClient standingClient;

    @GetMapping("/view/{leagueId}")
    public String viewStandingsForLeague(@PathVariable("leagueId") Long leagueId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        List<StandingResponse> standings = standingClient.getStandingsForLeague(leagueId);
        if (standings != null && !standings.isEmpty()) {
            model.addAttribute("standings", standings);
            return "/standings/view";
        } else {
            return "redirect:/league/get";
        }
    }
}
