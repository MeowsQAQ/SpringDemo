package com.example.demo.hello;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.dto.PaginationDTO;
import com.example.demo.enums.NotificationTagEnum;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id,
                          Model model){
        User user =(User)request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);

        if (NotificationTagEnum.REPLY_COMMENT.getType()==notificationDTO.getType()
                || NotificationTagEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else {
            return  "redirect:/";
        }

    }
}
