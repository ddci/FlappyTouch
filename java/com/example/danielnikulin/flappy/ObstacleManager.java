package com.example.danielnikulin.flappy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by danielnikulin on 16.05.17.
 */

public class ObstacleManager  {
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private  int score = 0;
    private long startTime;
    private long initTime;


    public ObstacleManager(int playerGap, int obstacleGap,int obstacleWidht, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleWidht;
        this.color = color;
        obstacles = new ArrayList<>();

        initTime = startTime = System.currentTimeMillis();

        populateObstacle();
    }

    public boolean playerCollide (RectPlayer player){
        for (Obstacle ob: obstacles) {
        if (ob.playerCollide(player))
            return true;
        }
        return false;
    }

    private void populateObstacle() {
        int currX = Constants.SCREEN_WIDHT;
          while(currX>Constants.SCREEN_WIDHT/2){
        //while(currX>0){
            int yStart = (int) (Math.random()*(Constants.SCREEN_HEIGHT/1.75 - playerGap));

            obstacles.add(new Obstacle(obstacleHeight,color,currX,yStart,playerGap));
            currX -= (obstacleHeight+obstacleGap);
        }

    }


    public void draw(Canvas canvas) {
        for (Obstacle ob:obstacles) {
            ob.draw(canvas);

            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            canvas.drawText("" + score,70,70,paint);
        }
    }

    public void update(RectPlayer player) {
        int elapsedTime = (int) (System.currentTimeMillis()- startTime);
        startTime =  System.currentTimeMillis();
        float speed = (float) ((Math.sqrt((1+ startTime-initTime)/2000.0))*(Constants.SCREEN_WIDHT/(10000.0f)));
        for (Obstacle ob:obstacles) {
            ob.decrementX(speed*elapsedTime); //fix (speed*elapsedTime)
        }

        if (obstacles.get(0).getRectangle().right <= Constants.SCREEN_WIDHT){//// STOPSHIP: 18.05.17
            int yStart= (int) (Math.random()*(Constants.SCREEN_HEIGHT/1.2 - playerGap));

            obstacles.add(0, new Obstacle(obstacleHeight, color
                    ,obstacles.get(0).getRectangle().left + obstacleHeight + obstacleGap ,
                    yStart ,
                    playerGap));

        }
        if (obstacles.get(obstacles.size()-1).getRectangle().right <= 0){
            for (int i = 0; i < obstacles.size(); i++) {
                Obstacle ob = obstacles.get(i);
                if(ob.getRectangle().right<=0) {
                    obstacles.remove(i);
               score++;
                }
            }
           /* for (int i = 0; i < obstacles.size(); i++) {
                Obstacle ob = obstacles.get(i);
                Rect rectBetween = new Rect(ob.getRectangle().left,
                        ob.getRectangle().bottom,
                        ob.getRectangle().right,
                        ob.getRectangle().bottom+playerGap);
                if(player.getRectangle().right>=rectBetween.right && !ob.isAlreadyPassed) {
                    ob.isAlreadyPassed=true;
                    score++;
                }
            }*/



           //obstacles.remove(obstacles.size()-1);
            Runtime.getRuntime().gc();
            //System.gc();
             //перепесать метод

        }

    }
}
