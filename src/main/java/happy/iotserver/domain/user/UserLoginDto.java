package happy.iotserver.domain.user;

import lombok.Data;

@Data
public class UserLoginDto {

    private Long userId;
    private String password;

}
