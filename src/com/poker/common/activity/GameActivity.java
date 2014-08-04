package com.poker.common.activity;

import com.poker.common.layer.GameLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.R;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 *
 * 类说明
 *
 * @author RinfonChen:
 * @Day 2014年8月1日 
 * @Time 下午2:21:43
 * @Declaration :
 *
 */
public class GameActivity extends Activity{

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        CCDirector.sharedDirector().onResume();
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
           }
    }

    private CCGLSurfaceView mView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mView = new CCGLSurfaceView(this);
        CCDirector director = CCDirector.sharedDirector();
        director.attachInView(mView);
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
        setContentView(mView);

        // show FPS
        CCDirector.sharedDirector().setDisplayFPS(true);

        // frames per second
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 30);

        CCScene scene = CCScene.node();
        scene.addChild(new GameLayer());

        // Make the Scene active
        CCDirector.sharedDirector().runWithScene(scene);
    }
    

}
