package api.mappings.garage.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ClientRequest {

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private Integer phoneNumber;
    private String nif;
    private Date birthDate;
    private Date clientDate;
}
