package com.example.danielnikulin.flappy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by danielnikulin on 17.05.17.
 */

public class GameplayScene implements Scene{
  private Rect r = new Rect();
    private RectPlayer player;
    private Panel view;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean movingPlayer = false;
    private long gameOverTime;
    private boolean gameOver = false;
    private double privxPos;
    private double privyPos;
    private Bitmap idleImg;


    public GameplayScene(Panel view) {
        this.view = view;
        BitmapFactory bitmapFactory = new BitmapFactory();
        idleImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.lava);

        player = new RectPlayer(new Rect(0,0, (int) (Constants.SCREEN_WIDHT/6), (int) (Constants.SCREEN_HEIGHT/12.5)), Color.rgb(255,255,0));
        playerPoint = new Point(Constants.SCREEN_WIDHT/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager((int) (Constants.SCREEN_HEIGHT/2.7),Constants.SCREEN_WIDHT/3, (int) (Constants.SCREEN_WIDHT/5.4),Color.GREEN);
        view.playSound(0);
    }

    private void reset() {
     playerPoint = new Point(Constants.SCREEN_WIDHT/2,3*Constants.SCREEN_HEIGHT/4);
     player.update(playerPoint);
        obstacleManager = new ObstacleManager((int) (Constants.SCREEN_HEIGHT/2.7),Constants.SCREEN_WIDHT/3, (int) (Constants.SCREEN_WIDHT/5.4),Color.GREEN);
     movingPlayer = false;
        view.playSound(0);

   }

       @Override
      public void update() {
        if(!gameOver){
           player.update();
             player.update(playerPoint);
             obstacleManager.update(player);
               if(obstacleManager.playerCollide(player)){
                 gameOver= true;
                   view.playSound(1);
                 gameOverTime = System.currentTimeMillis();
               }
        }
      }


 @Override
 public void draw(Canvas canvas) {

     canvas.drawBitmap(idleImg,null,new Rect(0,0,Constants.SCREEN_WIDHT,Constants.SCREEN_HEIGHT),new Paint()); //// STOPSHIP: 18.05.17
     //   canvas.drawColor(Color.WHITE);
      /* Paint textPaint = new Paint();
       textPaint.setARGB(200, 254, 0, 0);
       textPaint.setTextAlign(Paint.Align.RIGHT);
       textPaint.setTextSize(90);
       int xPos = (canvas.getWidth());
       int yPos = canvas.getHeight() ;
       canvas.drawText(String.valueOf(MainThread.averageFPS),  xPos, yPos, textPaint);*/


        obstacleManager.draw(canvas);
        player.draw(canvas);
          if (gameOver){
            Paint paint = new Paint();
            paint.setTextSize(120);
            paint.setColor(Color.RED);
            drawCenterText(canvas,paint,"Game Over");
          }
      }

   @Override
   public void terminate() {
    SceneManager.ACTIVE_SCENE = 0;
   }

   @Override
   public void recieveTouch(MotionEvent event) {
       switch (event.getAction()){
        case MotionEvent.ACTION_DOWN:
         if(!gameOver && player.getRectangleTouch().contains((int)event.getX(), (int) event.getY())){
            //if(!gameOver){
                privxPos = event.getX();
                privyPos = event.getY();
          movingPlayer = true;}
         if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
          reset();
          gameOver = false;
         }
         break;
        case MotionEvent.ACTION_MOVE:
         if (!gameOver && movingPlayer)
            // playerPoint.set((int) (playerPoint.x + (event.getX()- privxPos)), (int) (playerPoint.y + (event.getY()- privyPos)));
          playerPoint.set((int) event.getX(), (int) event.getY());
         break;
        case MotionEvent.ACTION_UP:
         movingPlayer = false;
         break;
       }
    }

       private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
   }

}
