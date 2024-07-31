package com.beyond.hackerton.member.service;

import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.member.dto.MemberLoginDto;
import com.beyond.hackerton.member.dto.MemberSaveReqDto;
import com.beyond.hackerton.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(MemberSaveReqDto dto) {
        return memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
    }

    public Member login(MemberLoginDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(()->new EntityNotFoundException("member not found"));
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return member;
    }
}
