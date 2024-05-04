package happy.iotserver.domain.user;

import happy.iotserver.domain.Address;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Embedded
    private Address address;

    protected User() {
    }

    public User(Long userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

}
