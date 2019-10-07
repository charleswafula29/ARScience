  package com.example.arscience;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

  public class MainActivity extends AppCompatActivity {

      private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        assert arFragment != null;
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor= hitResult.createAnchor();
            ModelRenderable.builder().setSource(this, Uri.parse("model.sfb"))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor,modelRenderable))
                    .exceptionally(throwable -> {
                        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                        return null;
                    });
        });

    }

      private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
          AnchorNode anchorNode= new AnchorNode(anchor);
          TransformableNode transformableNode= new TransformableNode(arFragment.getTransformationSystem());
          transformableNode.setParent(anchorNode);
          transformableNode.setRenderable(modelRenderable);
          arFragment.getArSceneView().getScene().addChild(anchorNode);
          transformableNode.select();
      }
}
