package com.codelabs.dokter_mobil_customer.connection;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception error";

    }
}
