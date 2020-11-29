package com.example.getpet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ARView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r_view);

        goBackToProfile();
        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child("labrador.glb");

        ArFragment arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        findViewById(R.id.GetPet).setOnClickListener(v -> {

            try {
                File file = File.createTempFile("temp", "glb");
                modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        buildModel(file);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {

            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            // Create the transformable andy and add it to the anchor.
            TransformableNode beagle = new TransformableNode(arFragment.getTransformationSystem());
            beagle.setLocalScale(new Vector3((float)0.5, (float)0.5, (float)0.5));
            beagle.setParent(anchorNode);
            beagle.setRenderable(renderable);
            beagle.select();

        }));

    }

        private ModelRenderable renderable;

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void buildModel (File file){


            RenderableSource renderableSource = RenderableSource
                    .builder()
                    .setSource(this, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                    .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                    .build();

            ModelRenderable.builder().setSource(this, renderableSource).setRegistryId(file.getPath()).build().thenAccept(modelRenderable -> {
                Toast.makeText(this, "Model Built", Toast.LENGTH_SHORT).show();
                renderable = modelRenderable;
            });

        }


    private void goBackToProfile(){

        TextView gobacktoprofile = findViewById(R.id.gobacktoprofile);
        gobacktoprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}



