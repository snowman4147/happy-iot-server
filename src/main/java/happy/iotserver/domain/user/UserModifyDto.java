package happy.iotserver.domain.user;

import lombok.Data;

@Data
public class UserModifyDto {

    private Long userId;
    private String userName;
    private String city;
    private String street;
    private String zip;

}
