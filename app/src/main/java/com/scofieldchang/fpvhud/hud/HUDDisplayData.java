package com.scofieldchang.fpvhud.hud;

/**
 * Created by ScofieldChang on 16/6/10.
 */
public class HUDDisplayData {
    private static HUDDisplayData myData;
    private HUDDisplayData (){}
    public static HUDDisplayData getInstance(){
        if (myData == null){
            myData = new HUDDisplayData();
        }
        return myData;
    }
    public float pitch = 0;
    public float roll = 0;
    public float speed = 0;
    public float altitude = 0;
    public float heading = 0;
    public float speedX = 0;
    public float speedY = 0;
    public float speedZ = 0;
}
