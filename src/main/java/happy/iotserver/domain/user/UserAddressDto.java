package happy.iotserver.domain.user;

import lombok.Data;

@Data
public class UserAddressDto {

    private Long userId;
    private String city;
    private String street;
    private String zip;

}
