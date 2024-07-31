package com.beyond.hackerton.member.dto;

import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveReqDto {
    private String name;
    private String email;
    private String password;
    private Role role = Role.USER;

    public Member toEntity(String password){
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .password(password)
                .role(this.role).build();
    }
}
