package com.example.demo.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersCreateForm {

    @NotEmpty(message = "블로그에서 사용할 이름을 적어주세요.")
    private String username;

    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자 ID는 필수 항목입니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordConfirm;

}
