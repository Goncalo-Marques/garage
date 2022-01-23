package api.mappings.garage.vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetVehicleResponse {
    private Integer id;
    private Integer client;
    private String brand;
    private String model;
    private Integer year;
    private String type;
    private String plate;
    private Boolean active;
}
