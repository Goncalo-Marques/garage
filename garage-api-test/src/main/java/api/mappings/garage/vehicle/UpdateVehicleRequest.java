package api.mappings.garage.vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateVehicleRequest {
    private String brand;
    private String model;
    private String plate;
    private Boolean active;
}
