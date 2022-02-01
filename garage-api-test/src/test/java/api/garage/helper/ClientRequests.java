package api.garage.helper;

import api.mappings.garage.client.ClientRequest;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ClientRequests {

    public static ClientRequest clientPositive() {
        return ClientRequest.builder()
                .firstName("Diogo")
                .lastName("Jesus")
                .address("Rua Principal, n12")
                .postalCode("3050-101")
                .city("Barcouço")
                .country("Portugal")
                .phoneNumber(912354789)
                .nif(260128857)
                .birthDate("1992-07-03")
                .clientDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())))
                .build();
    }
}
