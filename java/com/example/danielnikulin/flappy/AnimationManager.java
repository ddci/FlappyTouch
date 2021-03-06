package com.example.danielnikulin.flappy;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by danielnikulin on 17.05.17.
 */

public class AnimationManager {
    private Animation[] animations;
    private int animationIndex;

    public AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    public  void playAnim(int index){
        for (int i = 0; i <animations.length ; i++) {
            if (i == index){
                if(!animations[animationIndex].isPlaying())
                animations[i].play();
        } else
                animations[i].stop();

        }
        animationIndex = index;
    }

    public void draw (Canvas canvas, Rect rect){
        if (animations[animationIndex].isPlaying()) {
            animations[animationIndex].draw(canvas, rect);
        }
       // animations[animationIndex].draw(canvas, rect); //be carful i put this method here to avoid blinking
    }

    public void update(){
        if (animations[animationIndex].isPlaying()) {
            animations[animationIndex].update();
        }
    }
}
