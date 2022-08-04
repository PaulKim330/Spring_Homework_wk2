package com.sparta.springcore.service;

import com.sparta.springcore.dto.SignupRequestDto;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordcheck = requestDto.getPasswordcheck();

// 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
//username condition check 시작
        if(!usercheck(username)){
            throw new RuntimeException("4-12자의 영문 대문자,소문자,숫자만 가능합니다");
        }

        if(!password.equals(passwordcheck)){
            throw new RuntimeException("비밀번호가 틀립니다");
        }
        //password check 시작
        if(!passwordcheck(password)){
            throw new RuntimeException("4자이상의 영문 소문자만 가능합니다");
        }

        String encodepassword = passwordEncoder.encode(requestDto.getPassword());

        requestDto.setPassword(encodepassword);

        User user = new User(requestDto);
        userRepository.save(user);
    }


    private boolean usercheck(String username){

        char[] chars = username.toCharArray();

        if(chars.length<4 || chars.length >12){
            return false;
        }

        for (char ch : chars) {
            if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9'))) {
                return false;
            }

        }
        return true;
    }

    private boolean passwordcheck(String password){

        char[] pwcheck = password.toCharArray();

        if(pwcheck.length<4) {
            return false;
        }

        for (char ch1 : pwcheck){
            if(!((ch1>='a'&& ch1<='z')||(ch1>='0'&&ch1<='9'))){
                return false;
            }
        }
        return true;
    }
}