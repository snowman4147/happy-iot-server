package happy.iotserver.user.dto;

import lombok.Data;

@Data
public class UserAddressDto {

    private Long userId;
    private String city;
    private String street;
    private String zip;

}
