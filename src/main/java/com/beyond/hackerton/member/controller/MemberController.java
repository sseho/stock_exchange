package com.beyond.hackerton.member.controller;

import com.beyond.hackerton.common.auth.JwtTokenProvider;
import com.beyond.hackerton.common.dto.CommonResDto;
import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.member.dto.MemberLoginDto;
import com.beyond.hackerton.member.dto.MemberSaveReqDto;
import com.beyond.hackerton.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    @Qualifier("refreshtoken")
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider, @Qualifier("refreshtoken") RedisTemplate<String, Object> redisTemplate){

        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
    }

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
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail(),member.getRole().toString());

        redisTemplate.opsForValue().set(member.getEmail(), refreshToken,240, TimeUnit.HOURS);

        Map<String,Object> map = new HashMap<>();
        map.put("id",member.getId());
        map.put("accessToken",jwtToken);
        map.put("refreshToken",refreshToken);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK,"member login is successful",map);
        return new ResponseEntity(commonResDto,HttpStatus.OK);
    }
}
