package com.scofieldchang.fpvhud.hud;

import android.view.SurfaceHolder;

/**
 * Created by ScofieldChang on 16/6/10.
 */
public class HUDDisplayThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private HUDDisplayData data = HUDDisplayData.getInstance();
    private boolean updateInfo = false;
    private long frameTime = 15;
    private HUDDisplay display;
    private int width = 0, height = 0;
    public HUDDisplayThread (int width, int height){
        this.width = width;
        this.height = height;
    }
    @Override
    public void run() {
        display = new HUDDisplay(width,height);
        while(true){
            if (updateInfo){
                display.paint(surfaceHolder, data);
            }
            try{
                Thread.sleep(frameTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setSurface(SurfaceHolder surfaceHolder){
        this.surfaceHolder = surfaceHolder;
        updateInfo = true;
    }
    public void surfaceDestory(){
        updateInfo = false;
    }
}
