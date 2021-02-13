package fr.lpiot.qcm.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fr.lpiot.qcm.R;

public class ScoreFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private int scoreActuel = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_score, container, false);

        if(getActivity().getIntent().getExtras() != null){
            scoreActuel = getActivity().getIntent().getExtras().getInt("scoreActuel");
        }
        String message;
        if(scoreActuel == 0){
            message = "Jouez une partie pour voir votre score ici !";
        } else {
            message = "Votre dernier score est de : " + scoreActuel;
        }




        final TextView textViewDernierScore = root.findViewById(R.id.dernierScore);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textViewDernierScore.setText(message);
            }
        });
        return root;
    }
}