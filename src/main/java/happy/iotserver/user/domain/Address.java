package happy.iotserver.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "zip")
    private String zip;

    protected Address() {
    }

    public Address(String city, String street, String zip) {
        this.city = city;
        this.street = street;
        this.zip = zip;
    }

}
