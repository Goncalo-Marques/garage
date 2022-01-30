package api.mappings.garage.client;

import api.mappings.garage.vehicle.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
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
    private LocalDate birthDate;
    private LocalDate clientDate;
    private List<VehicleResponse> vehicles;
}
