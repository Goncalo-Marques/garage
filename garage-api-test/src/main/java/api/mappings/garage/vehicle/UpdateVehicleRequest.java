package api.mappings.garage.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateVehicleRequest {
    private String brand;
    private String model;
    private String plate;
    private Boolean active;
}
