package happy.iotserver.repository;

import happy.iotserver.domain.user.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void save() {
        //given
        User user1 = new User(11111L, "park", "1234");
        //when
        userRepository.save(user1);
        //then
        Assertions.assertThat(user1.getUserId()).isEqualTo(11111L);
    }

    @Test
    void update() {
        //given
        User user1 = new User(11111L, "park", "1234");
        //when
        user1.updatePassword("5678");
        userRepository.update(user1);
        //then
        Assertions.assertThat(user1.getPassword()).isEqualTo("5678");
    }

    @Test
    void delete() {
        //given
        User user1 = new User(11111L, "park", "1234");
        //when
        userRepository.save(user1);
        userRepository.delete(user1);
        //then
        Assertions.assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    void findOneByUserId() {
        //given
        User user1 = new User(11111L, "park", "1234");
        //when
        userRepository.save(user1);
        User findUser = userRepository.findOneByUserId(11111L);
        //then
        Assertions.assertThat(findUser).isEqualTo(user1);
    }

    @Test
    void findAll() {
        //given
        User user1 = new User(11111L, "park", "1234");
        User user2 = new User(22222L, "kim", "1234");
        User user3 = new User(33333L, "james", "1234");
        //when
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        List<User> users = userRepository.findAll();
        //then
        assertEquals(3, users.size());
    }

    @Test
    void findUsersById() {
        //given
        User user1 = new User(11111L, "park", "1234");
        User user2 = new User(22222L, "kim", "1234");
        User user3 = new User(33333L, "james", "1234");
        //when
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        List<User> findUser = userRepository.findUsersById(22222L);
        //then
        assertEquals(1, findUser.size());
        Assertions.assertThat(findUser.get(0)).isEqualTo(user2);
    }

    @Test
    void findByLoginId() {
        //given
        User user1 = new User(11111L, "park", "1234");
        //when
        userRepository.save(user1);
        Optional<User> findUser = userRepository.findByLoginId(11111L);
        //then
        Assertions.assertThat(findUser.isPresent()).isTrue();
        Assertions.assertThat(findUser.get().getUserId()).isEqualTo(11111L);
    }

}