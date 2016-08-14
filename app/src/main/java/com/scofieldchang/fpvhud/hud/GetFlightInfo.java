package com.scofieldchang.fpvhud.hud;

import dji.sdk.FlightController.DJICompass;
import dji.sdk.FlightController.DJIFlightController;
import dji.sdk.FlightController.DJIFlightControllerDataType;
import dji.sdk.Products.DJIAircraft;

/**
 * Created by ScofieldChang on 16/6/11.
 */
public class GetFlightInfo extends Thread {
    private boolean isConnection = false;
    private long samplingTime = 10;
    private HUDDisplayData data = HUDDisplayData.getInstance();
    private DJIAircraft djiAircraft;
    private DJICompass compass;
    private DJIFlightController djiFlightController;
    private DJIFlightControllerDataType.DJIFlightControllerCurrentState state;
    private DJIFlightControllerDataType.DJILocationCoordinate3D location;

    @Override
    public void run() {
        while (true){
            if (isConnection){
                getHeading();
                getGroundSpeed();

                getRoll();
                getAltitude();
                getPitch();
            }
            try {
                Thread.sleep(samplingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void connectionEstablish(boolean isConnection, DJIAircraft djiAircraft){
        this.isConnection = isConnection;
        if (isConnection){
            setDjiAircraft(djiAircraft);
        }
    }
    private void setDjiAircraft(DJIAircraft djiAircraft){
        this.djiAircraft = djiAircraft;
        this.compass = djiAircraft.getFlightController().getCompass();
        this.djiFlightController = djiAircraft.getFlightController();
        this.state = djiFlightController.getCurrentState();
        this.location = state.getAircraftLocation();
    }
    private void getHeading(){
        data.heading = (float) compass.getHeading();
    }
    private void getGroundSpeed(){
        float speedX = state.getVelocityX();
        float speedY = state.getVelocityY();
        float speedZ = state.getVelocityZ();

        data.speedX = speedX;
        data.speedY = speedY;
        data.speedZ = speedZ;

        double speed = Math.sqrt(speedX*speedX + speedY*speedY);
        data.speed = ((int)(speed*10)) /10;
    }
    private void getRoll(){
        data.roll = (float) state.getAttitude().roll;
    }
    private void getAltitude(){
//        data.altitude = location.getAltitude();
        if (state.isUltrasonicBeingUsed()){
            data.altitude = state.getUltrasonicHeight();
        } else {
            data.altitude = location.getAltitude();
        }

    }
    private void getPitch(){
        data.pitch = (float)state.getAttitude().pitch;
    }
}