public interface RetrofitApi {
    @POST("auth/login")
    RetrofitApi postJson(@Body User body);
}
