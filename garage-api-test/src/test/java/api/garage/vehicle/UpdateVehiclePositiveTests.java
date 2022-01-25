package api.garage.vehicle;

import api.mappings.garage.vehicle.CreateVehicleRequest;
import api.mappings.garage.vehicle.GetVehicleResponse;
import api.retrofit.garage.Vehicle;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;

import static api.validators.ResponseBodyValidator.assertBodyNotNull;
import static api.validators.ResponseBodyValidator.assertID;
import static api.validators.ResponseCodeValidator.*;
import static api.validators.VehicleValidator.assertVehicleResponseWithCreateRequest;

public class UpdateVehiclePositiveTests {

}
