package happy.iotserver.service;

import happy.iotserver.domain.*;
import happy.iotserver.domain.user.User;
import happy.iotserver.domain.user.UserModifyDto;
import happy.iotserver.repository.UserRepository;
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

    // 회원가입, 중복 검사 후 user 저장
    public void joinUser(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        log.info("id: {}, join", user.getUserId());
    }

    // userId로 User 객체를 찾고 비밀번호 대조 후 User 반환, 없으면 null 반환
    public User userLogin(Long userId, String password) {
        return userRepository.findByLoginId(userId)
                .filter(u -> u.getPassword().equals(password)).orElse(null);
    }

    // 유저 아이디로 회원 정보를 모두 찾아 DTO 에 담기
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

    // 비밀번호 변경
    public void updateUserPassword(Long userId, String password) {
        User findUser = userRepository.findOneByUserId(userId);
        findUser.updatePassword(password);
        userRepository.update(findUser);
        log.info("id: {}, password update", findUser.getUserId());
    }

    // 주소 업데이트
    public void updateUserAddress(Long userId, Address address) {
        User findUser = userRepository.findOneByUserId(userId);
        findUser.updateAddress(address);
        userRepository.update(findUser);
        log.info("id: {}, address update", findUser.getUserId());
    }

    // 아이디 중복 검사
    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findUsersById(user.getUserId());
        if (!findUsers.isEmpty()) {
            log.warn("id: {}, This id has already been registered.", user.getUserId());
            throw new IllegalStateException();
        }
    }

}
