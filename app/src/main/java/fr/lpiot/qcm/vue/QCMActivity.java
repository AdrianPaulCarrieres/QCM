package fr.lpiot.qcm.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import fr.lpiot.qcm.R;
import fr.lpiot.qcm.R.color;

public class QCMActivity extends AppCompatActivity {

    private ProgressBar pbar;
    private TextView tps_restant;
    private TextView scorej1;
    private TextView scorej2;
    private int temps_timer = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);
        pbar = findViewById(R.id.determinateBar);
        tps_restant = findViewById(R.id.temps_restant);
        scorej1 = findViewById(R.id.Scorejoueur1) ;
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
                Toast t = Toast.makeText(getApplicationContext(),"Too slow !", Toast.LENGTH_LONG );
                t.show();
            }
        };


        timer.start();
    }

    public void choixReponse(View v) {
        if (((Button) v).getText()==getResources().getString(R.string.reponse4)) {
            v.setBackgroundColor(v.getContext().getResources().getColor(color.green));
            //à voir si on fait plus compliqué après
            String score = ""+temps_timer;
            scorej2.setText(score);
        }
        else
            v.setBackgroundColor(v.getContext().getResources().getColor(color.red));

    }
}