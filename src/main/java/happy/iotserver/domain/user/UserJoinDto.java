package happy.iotserver.domain.user;

import lombok.Data;

@Data
public class UserJoinDto {

    private Long userId;
    private String userName;
    private String password;

}
