package com.jinmlee.articleProject.dto.member;

import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.entity.member.MemberInfo;
import com.jinmlee.articleProject.enums.Department;
import com.jinmlee.articleProject.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMemberDto {

    @NotBlank(message = "아이디는 필수 입력사항입니다.")
    @Size(min = 5, max = 20, message = "아이디는 5글자 이상 20글자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디에는 공백이나 특수문자가 들어올 수 없습니다.")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d{5,})(?=.*[*!@#$%])[A-Za-z\\d*!@#$%]{8,}$", message = "비밀번호는 특수문자 2개 이상, 숫자 5개 이상을 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력사항입니다.")
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호는 -로 구분해서 적어주세요")
    private String phoneNumber;

    @Email(message = "이메일 형식을 지켜주세요")
    private String email;

    @Setter
    private Role role;

    @Nullable
    private String adminCode;

    @Setter
    private Department department;

    public Member toEntityMember(BCryptPasswordEncoder bc) {
        return Member.builder()
                .loginId(loginId)
                .password(bc.encode(password))
                .role(role)
                .department(department)
                .build();
    }

    public MemberInfo toEntityMemberInfo(Member member) {
        return MemberInfo.builder()
                .member(member)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }
}
