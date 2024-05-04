package happy.iotserver.user.api;

import happy.iotserver.user.domain.Address;
import happy.iotserver.user.domain.User;
import happy.iotserver.user.dto.*;
import happy.iotserver.user.service.UserService;
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

    @PostMapping("join")
    public Map<String, String> join(@RequestBody UserJoinDto userJoinDto) {
        User user = new User(userJoinDto.getUserId(), userJoinDto.getUserName(), userJoinDto.getPassword());
        userService.joinUser(user);
        return makeResponse(true);
    }

    @PostMapping("login")
    public Map<String, String> login(@RequestBody UserLoginDto userLoginDto) {
        User loginUser = userService.userLogin(userLoginDto.getUserId(), userLoginDto.getPassword());
        return makeResponse(loginUser != null);
    }

    @PostMapping("userModify/updatePassword")
    public Map<String, String> updatePassword(@RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUserPassword(userUpdateDto.getUserId(), userUpdateDto.getChangePassword());
        return makeResponse(true);
    }

    @PostMapping("userModify/updateAddress")
    public Map<String, String> updateAddress(@RequestBody UserAddressDto userAddressDto) {
        Address newAddress = new Address(userAddressDto.getCity(), userAddressDto.getStreet(), userAddressDto.getZip());
        userService.updateUserAddress(userAddressDto.getUserId(), newAddress);
        return makeResponse(true);
    }

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

    @PostMapping("userModify")
    public UserModifyDto userModify(@RequestBody UserModifyDto userModifyDto) {
        return userService.userModify(userModifyDto.getUserId());
    }

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
