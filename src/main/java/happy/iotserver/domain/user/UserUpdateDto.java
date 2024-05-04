package happy.iotserver.domain.user;

import lombok.Data;

@Data
public class UserUpdateDto {

    private Long userId;
    private String changePassword;

}
