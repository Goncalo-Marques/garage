package api.retrofit.garage;


import api.mappings.garage.ErrorResponse;
import api.retrofit.RetrofitBuilder;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Can use this class to convert normal body to errorBody to check errors
 */
public class Error {
    private static final Retrofit retrofit = new RetrofitBuilder().getRetrofit();

    public static ErrorResponse getErrorResponse(Response response) throws IOException {
        Converter<ResponseBody, ErrorResponse> errorConverter =
                retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        return errorConverter.convert(response.errorBody());
    }
}
