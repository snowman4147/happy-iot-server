package happy.iotserver.service;

import happy.iotserver.user.domain.Address;
import happy.iotserver.user.domain.User;
import happy.iotserver.user.dto.UserModifyDto;
import happy.iotserver.user.domain.repository.UserRepository;
import happy.iotserver.user.service.UserService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserServiceTest(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Test
    void joinUser() {
        //given
        User user = new User(1111L, "John", "1234");
        //when
        userService.joinUser(user);
        User findUser = userRepository.findOneByUserId(1111L);
        //then
        Assertions.assertThat(findUser.getUserId()).isEqualTo(1111L);
    }

    @Test
    void userLogin() {
        //given
        User user = new User(1111L, "John", "1234");
        //when
        userService.joinUser(user);
        User loginUser = userService.userLogin(1111L, "1234");
        User loginUserDifferentPassword = userService.userLogin(1111L, "12345");
        User loginUserDifferentIdAndPassword = userService.userLogin(1211L, "123534");
        //then
        assertNotNull(loginUser);
        assertNull(loginUserDifferentPassword);
        assertNull(loginUserDifferentIdAndPassword);
    }

    @Test
    void userModify() {
        //given
        User user = new User(1111L, "John", "1234");
        Address newAddress = new Address("창원시 마산합포구", "월영서 5길", "21342");
        //when
        userService.joinUser(user);
        userService.updateUserAddress(user.getUserId(), newAddress);
        UserModifyDto userModifyDto = userService.userModify(user.getUserId());
        //then
        Assertions.assertThat(userModifyDto.getUserId()).isEqualTo(1111L);
        Assertions.assertThat(userModifyDto.getUserName()).isEqualTo("John");
        Assertions.assertThat(userModifyDto.getCity()).isEqualTo("창원시 마산합포구");
        Assertions.assertThat(userModifyDto.getStreet()).isEqualTo("월영서 5길");
        Assertions.assertThat(userModifyDto.getZip()).isEqualTo("21342");
    }

    @Test
    void deleteUser() {
        //given
        User user = new User(1111L, "John", "1234");
        //when
        userService.joinUser(user);
        userService.deleteUser(user.getUserId());
        User findUser = userRepository.findOneByUserId(1111L);
        //then
        assertNull(findUser);
    }

    @Test
    void updateUserPassword() {
        //given
        User user = new User(1111L, "John", "1234");
        //when
        userService.joinUser(user);
        userService.updateUserPassword(user.getUserId(), "5678");
        //then
        Assertions.assertThat(user.getPassword()).isEqualTo("5678");
    }

    @Test
    void updateUserAddress() {
        //given
        User user = new User(1111L, "John", "1234");
        Address newAddress = new Address("창원시 마산합포구", "월영서 5길", "21342");
        //when
        userService.joinUser(user);
        userService.updateUserAddress(user.getUserId(), newAddress);
        //then
        Assertions.assertThat(user.getAddress()).isEqualTo(newAddress);
    }

}