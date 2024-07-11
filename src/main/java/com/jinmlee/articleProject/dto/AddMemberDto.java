package com.jinmlee.articleProject.dto;

import com.jinmlee.articleProject.entity.Member;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMemberDto {

    @NotBlank(message = "이름은 필수 입력사항입니다.")
    private String name;

    @NotBlank(message = "아이디는 필수 입력사항입니다.")
    @Size(min = 5, max = 20, message = "아이디는 5글자 이상 20글자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디에는 공백이나 특수문자가 들어올 수 없습니다.")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d{5,})(?=.*[*!@#$%])[A-Za-z\\d*!@#$%]{8,}$", message = "비밀번호는 특수문자 2개 이상, 숫자 5개 이상을 포함해야 합니다.")
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호는 -로 구분해서 적어주세요")
    private String phoneNumber;

    @Email(message = "이메일 형식을 지켜주세요")
    private String email;

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .loginId(loginId)
                .password(password)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }
}
