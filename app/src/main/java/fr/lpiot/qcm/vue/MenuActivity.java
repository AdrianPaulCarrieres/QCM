package fr.lpiot.qcm.vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import fr.lpiot.qcm.R;
import fr.lpiot.qcm.donnee.UtilisateurDAO;
import fr.lpiot.qcm.modele.Utilisateur;
import fr.lpiot.qcm.ui.gallery.ScoreFragment;
import fr.lpiot.qcm.ui.gallery.ScoreViewModel;

public class MenuActivity extends AppCompatActivity {

    //Résultats pour savoir d'où l'on vient
    static final public int ACTIVITE_QUIZZ = 1;

    private AppBarConfiguration mAppBarConfiguration;

    private String nomUtilisateur;
    private UtilisateurDAO accesseurDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle parametres = this.getIntent().getExtras();
        nomUtilisateur = parametres.get("nomUtilisateur").toString();
        afficherToast("Bienvenue " + nomUtilisateur);

        accesseurDAO = UtilisateurDAO.getInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int activite, int resultat, Intent data) {
        switch (activite) {
            case MenuActivity.ACTIVITE_QUIZZ:
                replaceFragment(ScoreFragment.class);
                break;
        }
        super.onActivityResult(activite, resultat, data);
    }

    public void replaceFragment(Class<ScoreFragment> fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment, new Bundle());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void afficherToast(String message){
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
        Log.i("login", "toasted message : " + message);
    }

    public void deconnecterUtilisteur(MenuItem item) {
        Utilisateur utilisateur = accesseurDAO.chercherUtilisateurParNom(nomUtilisateur);
        accesseurDAO.retirerToken(utilisateur);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}