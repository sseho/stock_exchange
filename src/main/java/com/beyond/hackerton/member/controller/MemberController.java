package com.beyond.hackerton.member.controller;

import com.beyond.hackerton.common.auth.JwtTokenProvider;
import com.beyond.hackerton.common.dto.CommonResDto;
import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.member.dto.MemberLoginDto;
import com.beyond.hackerton.member.dto.MemberSaveReqDto;
import com.beyond.hackerton.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MemberSaveReqDto dto) {
        Member member = memberService.createMember(dto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED,"member create successful!",member.getId());
        return new ResponseEntity(commonResDto,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDto dto){
        Member member = memberService.login(dto);

        String jwtToken = jwtTokenProvider.createToken(member.getEmail(),member.getRole().toString());
//        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail(),member.getRole().toString());

        Map<String,Object> map = new HashMap<>();
        map.put("id",member.getId());
        map.put("token",jwtToken);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK,"member login is successful",map);
        return new ResponseEntity(commonResDto,HttpStatus.OK);
    }
}
