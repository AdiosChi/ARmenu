package com.example.ar1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;
import java.io.IOException;
import java.util.Collection;

public class B extends AppCompatActivity {

    private CustomArFragment arFragment;
    private boolean isModelPlaced = false;
    private Session arSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_b);

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.customArFragment);
        try {
            arSession = new Session(this);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap fish01 = BitmapFactory.decodeStream(getAssets().open("fish.png"), null, options);

            AugmentedImageDatabase imageDatabase = new AugmentedImageDatabase(arSession);
            imageDatabase.addImage("fish", fish01);

            Config config = arSession.getConfig();
            config.setAugmentedImageDatabase(imageDatabase);
            arSession.configure(config);

        } catch (UnavailableArcoreNotInstalledException e) {
            e.printStackTrace();
        } catch (UnavailableApkTooOldException e) {
            e.printStackTrace();
        } catch (UnavailableSdkTooOldException e) {
            e.printStackTrace();
        } catch (UnavailableDeviceNotCompatibleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            Session session = arFragment.getArSceneView().getSession();
            if (session == null) {
                return;
            }

            Collection<AugmentedImage> updatedAugmentedImages = session.getAllTrackables(AugmentedImage.class);
            for (AugmentedImage augmentedImage : updatedAugmentedImages) {
                if (augmentedImage.getTrackingMethod() == AugmentedImage.TrackingMethod.FULL_TRACKING && !isModelPlaced) {
                    if (augmentedImage.getName().equals("fish")) {
                        placeModel(augmentedImage.createAnchor(augmentedImage.getCenterPose()));
                        isModelPlaced = true;
                    }
                }
            }
        });
    }

    private void placeModel(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("Fish01.glb"))
                .build()
                .thenAccept(modelRenderable -> {
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    TransformableNode modelNode = new TransformableNode(arFragment.getTransformationSystem());
                    modelNode.setParent(anchorNode);
                    modelNode.setRenderable(modelRenderable);
                    arFragment.getArSceneView().getScene().addChild(anchorNode);
                    modelNode.select();
                });
    }
}
