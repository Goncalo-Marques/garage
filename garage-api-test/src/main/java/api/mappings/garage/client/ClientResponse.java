package api.mappings.garage.client;

import api.mappings.garage.vehicle.GetVehicleResponse;

import java.util.Date;
import java.util.List;

public class ClientResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private Integer phoneNumber;
    private Integer nif;
    private Date birthDate;
    private Date clientDate;
    private List<GetVehicleResponse> vehicles;
}
