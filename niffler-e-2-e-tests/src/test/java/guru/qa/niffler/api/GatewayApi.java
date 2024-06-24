package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GatewayApi {

    @GET("/api/categories/all")
    Call<List<CategoryJson>> getCategories(@Header("Authorization") String bearerToken);

    @GET("/api/friends/all")
    Call<List<UserJson>> friends(@Header("Authorization") String bearerToken);

    @POST("/api/categories/add")
    Call<CategoryJson> addCategory(@Header("Authorization") String bearerToken,
                                       @Body CategoryJson category);

    @DELETE("/api/friends/remove")
    Call<Void> removeFriend(@Header("Authorization") String bearerToken,
                                @Query("username") String targetUsername);

    @GET("/api/invitations/income")
    Call<List<UserJson>> incomeInvitations(@Header("Authorization") String bearerToken);

    @GET("/api/invitations/outcome")
    Call<List<UserJson>> outcomeInvitations(@Header("Authorization") String bearerToken);


    @GET("/api/spends/all")
    Call<List<SpendJson>> getSpends(@Header("Authorization") String bearerToken);

    @POST("/api/spends/add")
    Call<SpendJson> addSpend(@Body SpendJson spend,
                             @Header("Authorization") String bearerToken);

    @PATCH("/api/spends/edit")
    Call<SpendJson> editSpend(@Body SpendJson spend,
                              @Header("Authorization") String bearerToken);

    @DELETE("/api/spends/remove")
    Call<Void> deleteSpends(@Header("Authorization") String bearerToken,
                            @Query("ids") List<String> ids);

    @GET("/api/users/current")
    Call<UserJson> currentUser(@Header("Authorization") String bearerToken);

    @GET("/api/users/all")
    Call<List<UserJson>> allUsers(@Header("Authorization") String bearerToken);

    @POST("/api/users/update")
    Call<UserJson> updateUserInfo(@Header("Authorization") String bearerToken);
}
