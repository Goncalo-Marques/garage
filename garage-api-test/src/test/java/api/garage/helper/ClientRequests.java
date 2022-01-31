package api.garage.helper;

import api.mappings.garage.client.ClientRequest;

import java.time.LocalDate;

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
                .birthDate(LocalDate.of(1992, 07, 03))
                .clientDate(LocalDate.now())
                .build();
    }
}
