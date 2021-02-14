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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import fr.lpiot.qcm.R;
import fr.lpiot.qcm.R.color;
import fr.lpiot.qcm.donnee.ScoreDAO;
import fr.lpiot.qcm.donnee.UtilisateurDAO;
import fr.lpiot.qcm.donnee.apiCalls.GetDataService;
import fr.lpiot.qcm.donnee.apiCalls.RetrofitClientInstance;
import fr.lpiot.qcm.modele.ApiResponse;
import fr.lpiot.qcm.modele.Question;
import fr.lpiot.qcm.modele.Score;
import fr.lpiot.qcm.modele.Utilisateur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QCMActivity extends AppCompatActivity {

    static final private int NOMBRE_QUESTIONS = 5;
    static final private String TYPE_QUESTIONS = "multiple";

    private ProgressBar pbar;
    private TextView tps_restant;
    private TextView joueur;
    private TextView scoreJ;
    private TextView textViewQuestion;
    private int temps_timer = 30;

    //Questions (et réponses) !
    private Stack<Question> stackQuestions = new Stack<>();
    private Question questionActuelle;
    private ArrayList<String> reponses;
    private Button[] boutons;

    //Timer
    CountDownTimer timer;

    //Score
    private int score = 0;

    //Persistence
    private ScoreDAO accesseurScore;
    private UtilisateurDAO accesseurUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);
        pbar = findViewById(R.id.determinateBar);
        tps_restant = findViewById(R.id.temps_restant);

        joueur = findViewById(R.id.joueur2);
        scoreJ = findViewById(R.id.Scorejoueur2);

        textViewQuestion = findViewById(R.id.question);

        accesseurScore = ScoreDAO.getInstance();
        accesseurUtilisateur = UtilisateurDAO.getInstance();
        joueur.setText(accesseurUtilisateur.getUtilisateurConnecte().getNom());
        boutons = new Button[]{
                findViewById(R.id.reponse1), findViewById(R.id.reponse2), findViewById(R.id.reponse3), findViewById(R.id.reponse4)
        };

        callApi();
    }

    private void callApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<ApiResponse> call = service.getQuestions(NOMBRE_QUESTIONS, TYPE_QUESTIONS);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Questions
                Question[] questions = response.body().getResults();
                for (Question question : questions) {
                    stackQuestions.push(question);
                }

                nouvelleQuestion();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                afficherToast("Une erreur est survenue.");
            }
        });
    }

    private void nouvelleQuestion() {
        if (timer != null) {
            timer.cancel();
            temps_timer = 30;
            pbar.setProgress(0);
        }

        if (!stackQuestions.empty()) {
            questionActuelle = stackQuestions.pop();
            reponses = new ArrayList<>();

            reponses.add(questionActuelle.getBonneReponse());
            reponses.addAll(Arrays.asList(questionActuelle.getListeFaussesReponses()));
            Collections.shuffle(reponses);

            for (int i = 0; i < reponses.size(); i++) {
                boutons[i].setText(reponses.get(i));
            }

            textViewQuestion.setText(questionActuelle.getQuestion());

            timer = new CountDownTimer(30000, 1000) {
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
                    temps_timer = 30;
                    s = "" + temps_timer;
                    tps_restant.setText(s);
                    Toast t = Toast.makeText(getApplicationContext(), "Too slow !", Toast.LENGTH_LONG);
                    t.show();
                    nouvelleQuestion();
                }


            }.start();
        } else {
            //Passer au score !
            Utilisateur utilisateur = accesseurUtilisateur.getUtilisateurConnecte();
            accesseurScore.ajouterScore(new Score(utilisateur.getNom(), score));
            this.finish();
        }
    }

    public void choixReponse(View v) {
        if (((Button) v).getText() == questionActuelle.getBonneReponse()) {
            v.setBackgroundColor(v.getContext().getResources().getColor(color.green));
            score += temps_timer;
            String scoreAsString = "" + score;
            scoreJ.setText(scoreAsString);
        } else {
            v.setBackgroundColor(v.getContext().getResources().getColor(color.red));
        }

        CountDownTimer timer = new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                v.setBackgroundColor(v.getContext().getResources().getColor(color.white));
                nouvelleQuestion();
            }
        };
        timer.start();
    }

    private void afficherToast(String message) {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
        Log.i("login", "toasted message : " + message);
    }
}