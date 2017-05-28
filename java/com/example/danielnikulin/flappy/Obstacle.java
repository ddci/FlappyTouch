package com.example.danielnikulin.flappy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by danielnikulin on 16.05.17.
 */

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;
    private Animation idle;
    Bitmap idleImg;
    Bitmap idleImg2;
    private  AnimationManager animationManager;
    public boolean isAlreadyPassed=false;

    public  Rect getRectangle(){
        return rectangle;

    }
    public void decrementX(float x){

        rectangle.left -= x;
        rectangle.right -= x;
        rectangle2.left -= x;
        rectangle2.right -= x;


    }

    public Obstacle(int rectWidth ,int color, int startX,int startY,int playerGap) {
        this.color = color;
        //rectangle= new Rect(0,startY,startX,startY+rectWidth);
        //rectangle2 = new Rect(startX + playerGap,startY,Constants.SCREEN_WIDHT, startY + rectWidth);
        rectangle = new Rect(startX-rectWidth,0,startX,startY);
        rectangle2 = new Rect(startX-rectWidth,startY+playerGap,startX,Constants.SCREEN_HEIGHT);
        BitmapFactory bitmapFactory = new BitmapFactory();
        idleImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.pipe);
        idleImg2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.pipe2);
        //idle = new Animation(new Bitmap[]{idleImg},1);
        //animationManager = new AnimationManager(new Animation[]{idle});


    }
    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(rectangle,player.getRectangleCollision())  || Rect.intersects(rectangle2,player.getRectangleCollision());

    }

    @Override
    public void draw(Canvas canvas) {
        // Paint paint = new Paint();
        //paint.setColor(color);
        canvas.drawBitmap(idleImg2,null,rectangle,new Paint());
        canvas.drawBitmap(idleImg,null,rectangle2,new Paint());
       // animationManager.draw(canvas,rectangle);
       // animationManager.draw(canvas,rectangle2);
    }

    @Override
    public void update() {
       // animationManager.playAnim(0);
        //animationManager.update();

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
