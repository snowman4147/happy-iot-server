package happy.iotserver.user.dto;

import lombok.Data;

@Data
public class UserUpdateDto {

    private Long userId;
    private String changePassword;

}
