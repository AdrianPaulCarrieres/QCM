package fr.lpiot.qcm.vue;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.lpiot.qcm.R;
import fr.lpiot.qcm.R.color;
import fr.lpiot.qcm.donnee.apiCalls.GetDataService;
import fr.lpiot.qcm.donnee.apiCalls.RetrofitClientInstance;
import fr.lpiot.qcm.modele.ApiResponse;
import fr.lpiot.qcm.modele.Question;
import fr.lpiot.qcm.modele.Reponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QCMActivity extends AppCompatActivity {

    static final private int NOMBRE_QUESTIONS = 5;

    private ProgressBar pbar;
    private TextView tps_restant;
    private TextView scorej1;
    private TextView scorej2;
    private int temps_timer = 30;


    //Questions (et réponses) !
    //Stack<>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);
        pbar = findViewById(R.id.determinateBar);
        tps_restant = findViewById(R.id.temps_restant);
        scorej1 = findViewById(R.id.Scorejoueur1);
        scorej2 = findViewById(R.id.Scorejoueur2);
        CountDownTimer timer = new CountDownTimer(30000, 1000) {
            String s;

            @Override
            public void onTick(long millisUntilFinished) {
                pbar.incrementProgressBy(1);
                --temps_timer;
                s = "" + temps_timer;
                tps_restant.setText(s);
            }

            @Override
            public void onFinish() {

                pbar.incrementProgressBy(1);
                --temps_timer;
                s = "" + temps_timer;
                tps_restant.setText(s);
                Toast t = Toast.makeText(getApplicationContext(), "Too slow !", Toast.LENGTH_LONG);
                t.show();
            }
        };

        callApi();

        timer.start();
    }

    private void callApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<ApiResponse> call = service.getQuestions(NOMBRE_QUESTIONS);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Questions
                Question[] questions = response.body().getResults();



            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                afficherToast("error");
            }
        });
    }

    public void choixReponse(View v) {
        if (((Button) v).getText() == getResources().getString(R.string.reponse4)) {
            v.setBackgroundColor(v.getContext().getResources().getColor(color.green));
            //à voir si on fait plus compliqué après
            String score = "" + temps_timer;
            scorej2.setText(score);
        } else
            v.setBackgroundColor(v.getContext().getResources().getColor(color.red));

    }

    private void afficherToast(String message) {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
        Log.i("login", "toasted message : " + message);
    }
}