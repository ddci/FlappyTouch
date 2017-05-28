package com.example.danielnikulin.flappy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by danielnikulin on 16.05.17.
 */

public class RectPlayer implements GameObject {
    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private  AnimationManager animationManager;
    Bitmap idleImg;
    Bitmap walk1;
    Bitmap walk2;

    public  Rect getRectangle(){
        return new Rect(rectangle);

    }
    public  Rect getRectangleCollision(){
        return new Rect(rectangle.left+30,rectangle.top+30,rectangle.right-30,rectangle.bottom-30);

    }

    public  Rect getRectangleTouch(){
        return new Rect(rectangle.left-50,rectangle.top-50,rectangle.right+50,rectangle.bottom+50);

    }

    public RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bitmapFactory = new BitmapFactory();
        idleImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.frame);
        walk1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.frame1);
        walk2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.frame2);

        idle = new Animation(new Bitmap[]{idleImg,walk1},0.5f);
        walkRight = new Animation(new Bitmap[]{walk1,walk2},1f);
            //vertical reverse  the image here
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
            walk2 = Bitmap.createBitmap(walk1, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1,walk2},1f);
        animationManager = new AnimationManager(new Animation[]{idle, walkRight,walkLeft});
        //animationManager = new AnimationManager(new Animation[]{idle});
    }

    @Override
    public void draw(Canvas canvas) {
        //Paint paint  = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle,paint);
        //canvas.drawBitmap(idleImg,null,rectangle,new Paint());

        animationManager.draw(canvas,rectangle);
    }

    @Override
    public void update() {
      animationManager.update();


    }

    public void update(Point point) {

        float oldLeft = rectangle.left;
        int state = 0;

        rectangle.set(point.x-rectangle.width()/2,
                point.y - rectangle.height()/2,
                point.x + rectangle.width()/2,
                point.y + rectangle.height()/2);

        if (rectangle.left - oldLeft > 5){
            state = 1;}
        else if (rectangle.left - oldLeft < -5){
            state = 2;
        }
        animationManager.playAnim(state);
        animationManager.update();

    }
}
