package com.jinmlee.articleProject.config.security;

import com.jinmlee.articleProject.dto.member.CustomUserDetails;
import com.jinmlee.articleProject.enums.Department;
import org.springframework.stereotype.Component;

@Component
public class Checker {

    public boolean validDepartmentFrontend(CustomUserDetails customUserDetails){
        return customUserDetails.getMember().getDepartment() == Department.FRONTEND;
    }
}
