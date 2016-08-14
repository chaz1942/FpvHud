package com.scofieldchang.fpvhud.hud;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

/**
 * Created by ScofieldChang on 16/6/10.
 */
public class HUDDisplay {
    private int width, hight;
    private final float half_unit = 48;
    public HUDDisplay(int width, int hight){
        this.width = width;
        this.hight = hight;
    }
    public void paint(SurfaceHolder surfaceHolder, HUDDisplayData data){
        Paint mPaint = new Paint();
        mPaint.setColor(Color.GREEN);// 画笔为绿色
        mPaint.setStrokeWidth(4);// 设置画笔粗细
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// 清除画布
        drawFlightPathMarker(canvas, mPaint);
        drawAirSeepdScaleplate((float)data.speed,(float) 1.0, 2*half_unit, canvas,mPaint);
        drawAltitudeScale((float)data.altitude, (float)10,2*half_unit,canvas,mPaint);
//        drawAltitudeScale((float)14.6, (float)10,2*half_unit,canvas,mPaint);
        //for debug
//        drawDebugParams(canvas,mPaint,data.heading,data.speed,data.roll,data.pitch,data.altitude,data.speedX,data.speedY,data.speedZ);


        drawHorizonalScale(width/2, hight/2 - 10*half_unit, 20*half_unit, data.heading, 10, half_unit*2,canvas,mPaint);
        drawPichLadder(canvas, mPaint,data.roll,data.pitch);
        surfaceHolder.unlockCanvasAndPost(canvas);

    }
    public void paint(SurfaceHolder surfaceHolder){
        clearDraw(surfaceHolder);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.GREEN);// 画笔为绿色
        mPaint.setStrokeWidth(4);// 设置画笔粗细
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// 清除画布
        drawFlightPathMarker(canvas, mPaint);
        drawAirSeepdScaleplate((float)3.2,(float) 1.0, 2*half_unit, canvas,mPaint);
        drawAltitudeScale((float)26.8, (float)10,2*half_unit,canvas,mPaint);
        drawHorizonalScale(width/2, hight/2 - 10*half_unit, 20*half_unit, 183, 10, half_unit*2,canvas,mPaint);
        drawPichLadder(canvas, mPaint,20,0);
        surfaceHolder.unlockCanvasAndPost(canvas);

    }
    private void drawFlightPathMarker(Canvas canvas, Paint paint){
        float aX = width/2 - half_unit*2;
        float baseY = hight/2;
        float bX = width/2 - half_unit;
        float cX = width/2;
        float cY = hight/2 + half_unit;
        float b1X = width/2 + half_unit;
        float a1X = width/2 + half_unit*2;
        canvas.drawLine(aX, baseY, bX, baseY, paint);
        canvas.drawLine(bX,baseY, cX, cY, paint);
        canvas.drawLine(cX,cY,b1X,baseY,paint);
        canvas.drawLine(b1X,baseY,a1X,baseY,paint);
    }
    private void drawPichLadder(Canvas canvas, Paint paint, float rotate, float pitch){
        float levelUp = (hight/2 - 5*half_unit) - (pitch*half_unit)/6;
        float levelBase = (hight/2) - (pitch*half_unit)/6;
        float levelDown = (hight/2 + 5*half_unit) - (pitch*half_unit)/6;
        float eX = width/2 - 7*half_unit;
        float fX = width/2 - 3*half_unit;
        float e1X = width/2 + 7*half_unit;
        float f1X = width/2 + 3*half_unit;



        paint.setStyle(Paint.Style.STROKE);

        canvas.rotate(rotate,width/2,hight/2);

        canvas.drawText("30", eX - half_unit, levelUp,paint);
        canvas.drawText("0", eX - half_unit, levelBase,paint);
        canvas.drawText("-30", eX - half_unit, levelDown,paint);

        canvas.drawText("30", (float)(e1X + 0.5*half_unit), levelUp,paint);
        canvas.drawText("0",(float)(e1X + 0.5*half_unit), levelBase,paint);
        canvas.drawText("-30",(float)(e1X + 0.5*half_unit), levelDown,paint);

        drawDotLine(eX,levelUp,fX,levelUp,paint,canvas);
        drawDotLine(e1X,levelUp,f1X,levelUp,paint,canvas);
        drawDotLine(eX,levelBase,fX,levelBase,paint,canvas);
        drawDotLine(e1X,levelBase,f1X,levelBase,paint,canvas);
        drawDotLine(eX,levelDown,fX,levelDown,paint,canvas);
        drawDotLine(e1X,levelDown,f1X,levelDown,paint,canvas);

    }
    private void drawDotLine(float startX, float startY, float endX, float endY, Paint paint, Canvas canvas){
        Path path = new Path();
        path.moveTo(startX,startY);
        path.lineTo(endX,endY);
        PathEffect effect = new DashPathEffect(new float[]{5,5,5,5},1);
        paint.setPathEffect(effect);
        canvas.drawPath(path,paint);
    }

    private void drawAirSeepdScaleplate(float speed, float speedUnit, float unitLength,Canvas canvas, Paint paint){
        float xAxis = width/2 - 16*half_unit;
        float yUp = hight/2 - 10*half_unit;
        float yDown = hight/2 + 10*half_unit;
        float x1Axis = width/2 + 16*half_unit;
        canvas.drawLine(xAxis,yUp, xAxis, yDown,paint);
        paint.setTextSize(40);
        drawVerticalUnitScale(xAxis, hight/2, speed, speedUnit, unitLength, false,canvas, paint);

    }
    private void drawAltitudeScale(float altitude, float altitudeUnit, float unitLength, Canvas canvas, Paint paint){
        float xAxis = width/2 + 16*half_unit;
        float yUp = hight/2 - 10*half_unit;
        float yDown = hight/2 + 10*half_unit;
        paint.setTextSize(40);
        canvas.drawLine(xAxis, yUp, xAxis, yDown, paint);
        drawVerticalUnitScale(xAxis, hight/2,altitude, altitudeUnit, unitLength, true, canvas, paint);

    }

    private void drawVerticalUnitScale(float x, float y, float displayValue, float displayScale, float displayUnitLength,boolean isRight,Canvas canvas, Paint paint){
        float dirX = isRight ? x-half_unit/2 : x+half_unit/2;
        float scaleX = isRight ?x+half_unit/2 : x-half_unit/2;
        float textDistance = isRight ? (float) 0.5*half_unit : (float) -2*half_unit;


        float unitLength = displayUnitLength/displayScale;
        int per = (int)(displayValue/displayScale);
//        draw up
        float yTop = hight/2 - 10*half_unit;
        float drawY = y - ((per+1)*displayScale - displayValue)*unitLength;
        float textY = (per+1)*displayScale;

        while(drawY > yTop){

            canvas.drawLine(scaleX, drawY, x, drawY, paint);
            canvas.drawText(textY + "",scaleX + textDistance,drawY,paint);
            drawY -= displayScale*unitLength;
            textY += displayScale;
        }
//        draw down
        textY = (per)*displayScale;
        drawY = y - (per*displayScale - displayValue)*unitLength;
        canvas.drawLine(dirX, y, x, y, paint);
        if (isRight){
            canvas.drawText(formatSpeedOrAltitude(displayValue) + "",x - textDistance*5, y+20, paint);
        }else{
            canvas.drawText(formatSpeedOrAltitude(displayValue) + "",x - textDistance/4, y, paint);
        }

        canvas.drawText(per*displayScale + "",scaleX + textDistance,drawY,paint);
        float yBottom = hight/2 + 10*half_unit;
        while ((drawY < yBottom) && (textY >= 0)){
            canvas.drawLine(scaleX, drawY, x, drawY, paint);
            canvas.drawText(textY + "",scaleX + textDistance,drawY,paint);
            drawY += displayScale*unitLength;
            textY -= displayScale;
        }
    }
    private void drawHorizonalScale(float x, float y, float length, float displayData, float displayScale, float displayUnitLength, Canvas canvas, Paint paint){
        float xLeft = x - length/2;
        float xRight = x + length/2;
        canvas.drawLine(xLeft, y, xRight, y, paint);
        float scaleMark = half_unit/2;
        canvas.drawLine(x, y+scaleMark, x, y, paint);
        canvas.drawText(formatHeading(displayData)+"",x-scaleMark,y+scaleMark*3,paint);
        float unitLength = displayUnitLength/displayScale;
        int per = (int)(displayData/displayScale);
//        draw left
        float drawX = x - (displayData-per*displayScale)*unitLength;
        float textX = per*displayScale;
        while(drawX > xLeft){

            canvas.drawLine(drawX, y - scaleMark, drawX, y, paint);
            canvas.drawText(checkHeading(textX),drawX,y-scaleMark*2,paint);
            drawX -= displayScale*unitLength;
            textX -= displayScale;
        }
//        draw right
        drawX = x + ((per+1)*displayScale-displayData)*unitLength;
        textX = (per+1)*displayScale;
        while (drawX < xRight){
            canvas.drawLine(drawX, y - scaleMark, drawX, y, paint);
            canvas.drawText(checkHeading(textX),drawX,y-scaleMark*2,paint);
            drawX += displayScale*unitLength;
            textX += displayScale;
        }

    }
    private String checkHeading(float heading){
        String [] heading_word = {"N","E","S","W"};
        int headingInt = (int)((heading + 360)%360);
        if (headingInt == 0){
            return heading_word[0];
        }
        if (headingInt == 90){
            return heading_word[1];
        }
        if (headingInt == 180){
            return heading_word[2];
        }
        if (headingInt == 270){
            return heading_word[3];
        }
        return headingInt + "";
    }

    private void clearDraw(SurfaceHolder sfh) {
        Canvas canvas = sfh.lockCanvas(null);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// 清除画布
        sfh.unlockCanvasAndPost(canvas);
    }

    private void drawDebugParams(Canvas canvas, Paint paint, float heading, float speed, float roll, float pitch, float altitute, float speedX, float speedY, float speedZ){
        canvas.drawText("heading: " + heading,(width/2)-16*half_unit,(hight/2)+10*half_unit, paint);
        canvas.drawText("roll: " + roll,(width/2)-8*half_unit,(hight/2)+10*half_unit, paint);
        canvas.drawText("speed: " + speed,(width/2),(hight/2)+10*half_unit, paint);
        canvas.drawText("pitch: " + pitch,(width/2)+6*half_unit,(hight/2)+10*half_unit, paint);
        canvas.drawText("altitute: " + altitute,(width/2)+10*half_unit,(hight/2)+10*half_unit, paint);
        canvas.drawText("speedX: " + speedX, (width/2) -16*half_unit, (hight/2)+11*half_unit, paint);
        canvas.drawText("speedY: " + speedY, (width/2), (hight/2)+11*half_unit, paint);
        canvas.drawText("speedZ: " + speedZ, (width/2) +10*half_unit, (hight/2)+11*half_unit, paint);
    }

    private String formatSpeedOrAltitude(float data){
        return String.format("%#.1f", data);
    }
    private String formatHeading(float data){
        int dataInt =(int)((data+360)%360);
        return dataInt+"";
    }

}

