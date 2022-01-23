package api.mappings.garage.vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateVehicleRequest {
    private String brand;
    private String model;
    private Integer year;
    private String type;
    private String plate;
    private Boolean active;
}
