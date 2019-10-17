package com.example.arscience;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.SkeletonNode;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class Animation extends AppCompatActivity {

    String model;
    Button animate;
    private ModelAnimator modelAnimator;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Intent intent = getIntent();
        model=intent.getStringExtra("modelname");

        animate=findViewById(R.id.Animatebutton);

        ArFragment arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragmentAnimation);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            createModel(hitResult.createAnchor(), arFragment);

        });

    }

    private void createModel(Anchor anchor, ArFragment arFragment) {

        ModelRenderable
                .builder()
                .setSource(this,Uri.parse(model))
                .build()
                .thenAccept(modelRenderable -> {

                    AnchorNode anchorNode = new AnchorNode(anchor);

                    SkeletonNode skeletonNode = new SkeletonNode();
                    skeletonNode.setParent(anchorNode);
                    skeletonNode.setRenderable(modelRenderable);

                    arFragment.getArSceneView().getScene().addChild(anchorNode);

                    animate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            animateModel(modelRenderable);
                        }
                    });

                });

    }

    private void animateModel(ModelRenderable modelRenderable) {

        if(modelAnimator != null && modelAnimator.isRunning()){
            modelAnimator.end();
        }

        int animationCount = modelRenderable.getAnimationDataCount();

        if(i==animationCount){
            i=0;
        }
        AnimationData animationData = modelRenderable.getAnimationData(i);

        modelAnimator=new ModelAnimator(animationData,modelRenderable);
        modelAnimator.start();
        i++;

    }
}
