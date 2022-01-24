package api.mappings.garage.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
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
