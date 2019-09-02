package com.example.demo.hello;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.service.NotificationService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model){
        User user =(User)request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        if("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.listForUser(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if("reply".equals(action)){

            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("unreadCount",unreadCount);

        }
        
        return "profile";
    }
}
