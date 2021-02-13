package fr.lpiot.qcm.donnee.apiCalls;

import java.util.List;

import fr.lpiot.qcm.modele.Question;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenTDB {
    @GET("/")
    Call<List<Question>> grouList(@Query("amount") int quantite);
}
