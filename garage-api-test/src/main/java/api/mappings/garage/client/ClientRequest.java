package api.mappings.garage.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ClientRequest {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("phoneNumber")
    private Integer phoneNumber;
    @JsonProperty("nif")
    private String nif;
    @JsonProperty("birthDate")
    private Date birthDate;
    @JsonProperty("clientDate")
    private Date clientDate;
}
