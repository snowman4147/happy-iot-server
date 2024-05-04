package happy.iotserver.repository;

import happy.iotserver.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    public void save(User user) {
        em.persist(user);
        log.info("saved user to DB: {}", user);
    }

    public void update(User user) {
        em.merge(user);
        log.info("updated user to DB: {}", user);
    }

    public void delete(User user) {
        em.remove(user);
        log.info("deleted user from DB: {}", user);
    }

    public User findOneByUserId(Long userId) {
        return em.find(User.class, userId);
    }

    // 저장된 모든 유저 찾기
    public List<User> findAll() {
        return em.createQuery("from User u", User.class).getResultList();
    }

    // 아이디로 모든 레코드에서 존재하는지 검색함
    public List<User> findUsersById(Long userId) {
        return em.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", userId).getResultList();
    }

    // null 값을 제어하기 위해서 Optional 로 반환, findUsersById 와 같은 역할
    public Optional<User> findByLoginId(Long userId) {
        return findAll().stream().filter(u -> u.getUserId().equals(userId)).findFirst();
    }

}
