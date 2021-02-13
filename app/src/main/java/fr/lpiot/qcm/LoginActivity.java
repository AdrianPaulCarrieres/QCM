package fr.lpiot.qcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.lpiot.qcm.donnee.BaseDeDonnees;
import fr.lpiot.qcm.donnee.UtilisateurDAO;
import fr.lpiot.qcm.modele.Utilisateur;

public class LoginActivity extends AppCompatActivity {

    //View
    protected TextView textViewIdentifiant;
    protected TextView textViewMotDePasse;

    //UtilisateurDAO
    protected UtilisateurDAO accesseurUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BaseDeDonnees.getInstance(getApplicationContext());

        accesseurUtilisateur = UtilisateurDAO.getInstance();

        textViewIdentifiant = findViewById(R.id.identifiant);
        textViewMotDePasse = findViewById(R.id.mdp);
    }

    public void buttonClick(View v){
        String nom = textViewIdentifiant.getText().toString();
        String motDePasse = textViewMotDePasse.getText().toString();

        Utilisateur utilisateur = accesseurUtilisateur.chercherUtilisateurParNom(nom);

        if(utilisateur == null) {
            afficherToast("Mauvais identifiant");
        }
        else if(utilisateur.getMotDePasse().equals(motDePasse)){
            afficherToast("Utilisateur connecté");
            Intent intent = new Intent(this, MenuActivity.class);
           // Bundle bundle = new Bundle();
            //intent.putExtras(bundle);
            startActivity(intent);
        } else {
            afficherToast("Mauvais mot de passe");
        }
    }

    private void afficherToast(String message){
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
        Log.i("login", "toasted message : " + message);
    }
}