package com.example.ar1;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomArFragment extends ArFragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArSceneView arSceneView = getArSceneView();
        if (arSceneView != null) {
            // Perform any additional scene setup here
        }
    }

}
