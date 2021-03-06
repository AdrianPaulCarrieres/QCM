package fr.lpiot.qcm.donnee.apiCalls;

import java.util.List;

import fr.lpiot.qcm.modele.ApiResponse;
import fr.lpiot.qcm.modele.Question;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("api.php")
    Call<ApiResponse> getQuestions(@Query("amount") int quantite, @Query("type") String type);
}
