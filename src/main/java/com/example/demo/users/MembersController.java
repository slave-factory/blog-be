package com.example.demo.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api")
@Controller
public class MembersController {

    private final MembersService membersService;

    @GetMapping("/signup")
    public String signup(MembersCreateForm membersCreateForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MembersCreateForm membersCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup";
        }

        if(!membersCreateForm.getPassword().equals(membersCreateForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordIncorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "signup";
        }

        MembersDTO membersDTO = new MembersDTO();
        membersDTO.setUserId(membersCreateForm.getUserId());
        membersDTO.setPassword(membersCreateForm.getPassword());
        membersDTO.setNickname(membersCreateForm.getUsername());
        membersDTO.setCreatedAt(LocalDateTime.now());

        membersService.create(membersDTO);

        return "hello";
    }
}
