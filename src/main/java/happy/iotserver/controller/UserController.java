package happy.iotserver.controller;

import happy.iotserver.domain.*;
import happy.iotserver.domain.user.*;
import happy.iotserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 웹 - 컨트롤러간 Dto 로 주고받아야함

    // 회원가입 api
    @PostMapping("join")
    public Map<String, String> join(@RequestBody UserJoinDto userJoinDto) {
        User user = new User(userJoinDto.getUserId(), userJoinDto.getUserName(), userJoinDto.getPassword());
        userService.joinUser(user);
        return makeResponse(true);
    }

    // 로그인 api
    @PostMapping("login")
    public Map<String, String> login(@RequestBody UserLoginDto userLoginDto) {
        User loginUser = userService.userLogin(userLoginDto.getUserId(), userLoginDto.getPassword());
        return makeResponse(loginUser != null);
    }

    // 비밀번호 변경 api
    @PostMapping("userModify/updatePassword")
    public Map<String, String> updatePassword(@RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUserPassword(userUpdateDto.getUserId(), userUpdateDto.getChangePassword());
        return makeResponse(true);
    }

    // 주소 변경 api
    @PostMapping("userModify/updateAddress")
    public Map<String, String> updateAddress(@RequestBody UserAddressDto userAddressDto) {
        Address newAddress = new Address(userAddressDto.getCity(), userAddressDto.getStreet(), userAddressDto.getZip());
        userService.updateUserAddress(userAddressDto.getUserId(), newAddress);
        return makeResponse(true);
    }

    // 회원탈퇴 api
    @PostMapping("userModify/delete")
    public Map<String, String> deleteUser(@RequestBody UserLoginDto userLoginDto) {
        User findUser = userService.userLogin(userLoginDto.getUserId(), userLoginDto.getPassword());
        if (findUser != null) {
            userService.deleteUser(userLoginDto.getUserId());
            return makeResponse(true);
        } else {
            return makeResponse(false);
        }
    }

    // 내 정보 불러오기 api
    @PostMapping("userModify")
    public UserModifyDto userModify(@RequestBody UserModifyDto userModifyDto) {
        return userService.userModify(userModifyDto.getUserId());
    }

    // 조건에 따라 성공하면 1, 실패하면 0을 클라이언트에게 전송함
    public Map<String, String> makeResponse(boolean bool) {
        Map<String, String> resp = new HashMap<>();
        if (bool) {
            resp.put("resp", "1");
        } else {
            resp.put("resp", "0");
        }
        return resp;
    }

}
