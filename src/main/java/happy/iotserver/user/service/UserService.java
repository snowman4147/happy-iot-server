package happy.iotserver.user.service;

import happy.iotserver.user.domain.Address;
import happy.iotserver.user.domain.User;
import happy.iotserver.user.domain.repository.UserRepository;
import happy.iotserver.user.dto.UserModifyDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void joinUser(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        log.info("id: {}, join", user.getUserId());
    }

    public User userLogin(Long userId, String password) {
        return userRepository.findByLoginId(userId)
                .filter(u -> u.getPassword().equals(password)).orElse(null);
    }

    public UserModifyDto userModify(Long userId) {
        User findUser = userRepository.findOneByUserId(userId);
        Address userAddress = findUser.getAddress();
        UserModifyDto userModifyDto = new UserModifyDto();
        userModifyDto.setUserId(findUser.getUserId());
        userModifyDto.setUserName(findUser.getUserName());
        userModifyDto.setCity(userAddress.getCity());
        userModifyDto.setStreet(userAddress.getStreet());
        userModifyDto.setZip(userAddress.getZip());
        return userModifyDto;
    }

    public void deleteUser(Long userId) {
        User findUser = userRepository.findOneByUserId(userId);
        userRepository.delete(findUser);
        log.info("id: {}, delete", userId);
    }

    public void updateUserPassword(Long userId, String password) {
        User findUser = userRepository.findOneByUserId(userId);
        findUser.updatePassword(password);
        userRepository.update(findUser);
        log.info("id: {}, password update", findUser.getUserId());
    }

    public void updateUserAddress(Long userId, Address address) {
        User findUser = userRepository.findOneByUserId(userId);
        findUser.updateAddress(address);
        userRepository.update(findUser);
        log.info("id: {}, address update", findUser.getUserId());
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findUsersById(user.getUserId());
        if (!findUsers.isEmpty()) {
            log.warn("id: {}, This id has already been registered.", user.getUserId());
            throw new IllegalStateException();
        }
    }

}
