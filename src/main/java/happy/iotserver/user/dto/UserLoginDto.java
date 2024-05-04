package happy.iotserver.user.dto;

import lombok.Data;

@Data
public class UserLoginDto {

    private Long userId;
    private String password;

}
