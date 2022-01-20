package api.retrofit.garage;

import api.calls.GenericCalls;
import api.mappings.garage.CreateGenericRequest;
import api.retrofit.RetrofitBuilder;
import lombok.NoArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Generic {

    private static final GenericCalls genericCalls = new RetrofitBuilder().getRetrofit().create(GenericCalls.class);

    public static Response<ResponseBody> getGenericCall() throws IOException {
        return genericCalls.getGenericCall().execute();
    }

    public static Response<ResponseBody> createGenericCall(CreateGenericRequest createGenericRequest) throws IOException {
        return genericCalls.createGenericCall(createGenericRequest).execute();
    }
}
