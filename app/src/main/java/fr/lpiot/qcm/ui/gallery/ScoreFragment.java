package fr.lpiot.qcm.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import fr.lpiot.qcm.R;
import fr.lpiot.qcm.donnee.ScoreDAO;
import fr.lpiot.qcm.modele.Score;

public class ScoreFragment extends Fragment {

    private ScoreViewModel scoreViewModel;
    private int scoreActuel = 0;

    private ScoreDAO accesseurScore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scoreViewModel =
                new ViewModelProvider(this).get(ScoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_score, container, false);

        accesseurScore = ScoreDAO.getInstance();

        ArrayList<Score> scores = accesseurScore.listerScores();

        ArrayAdapter<Score> itemsAdapter = new ArrayAdapter<Score>(getContext(), android.R.layout.simple_list_item_1, scores);

        ListView listView = root.findViewById(R.id.listeScores);
        listView.setAdapter(itemsAdapter);

        return root;
    }
}