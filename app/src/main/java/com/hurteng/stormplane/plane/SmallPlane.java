package com.hurteng.stormplane.plane;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.constant.GameConstant;
import com.hurteng.stormplane.myplane.R;

import java.util.Random;

/**
 * 小型机
 */
public class SmallPlane extends EnemyPlane {
    private static int currentCount = 0;
    private Bitmap smallPlane;
    public static int sumCount = GameConstant.SMALLPLANE_COUNT;

    public SmallPlane(Resources resources) {
        super(resources);
        this.score = GameConstant.SMALLPLANE_SCORE;
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        super.initial(arg0, arg1, arg2);
        isAlive = true;
        bloodVolume = GameConstant.SMALLPLANE_BLOOD;
        blood = bloodVolume;
        Random ran = new Random();

        speed = 6 * ran.nextInt(3) + 19;

        object_x = ran.nextInt((int) (screen_width - object_width));
        object_y = -object_height * (currentCount * 2 + 1);
        currentCount++;
        if (currentCount >= sumCount) {
            currentCount = 0;
        }
    }

    @Override
    public void initBitmap() {
        smallPlane = BitmapFactory.decodeResource(resources, R.drawable.small);
        object_width = smallPlane.getWidth();
        object_height = smallPlane.getHeight() / 3;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            if (!isExplosion) {
                if (isVisible) {
                    canvas.save();
                    canvas.clipRect(object_x, object_y,
                            object_x + object_width, object_y + object_height);
                    canvas.drawBitmap(smallPlane, object_x, object_y, paint);
                    canvas.restore();
                }
                logic();
            } else {
                int y = (int) (currentFrame * object_height);
                canvas.save();
                canvas.clipRect(object_x, object_y, object_x + object_width,
                        object_y + object_height);
                canvas.drawBitmap(smallPlane, object_x, object_y - y, paint);
                canvas.restore();
                currentFrame++;
                if (currentFrame >= 3) {
                    currentFrame = 0;
                    isExplosion = false;
                    isAlive = false;
                }
            }
        }
    }

    @Override
    public void release() {
        if (!smallPlane.isRecycled()) {
            smallPlane.recycle();
        }
    }

    /**
     * 移动逻辑
     */
    public void logic() {

        Random random = new Random();

        if (object_y < screen_height) {

            object_y += speed;//纵坐标的计算是以屏幕顶部为0来计算的.纵坐标达到了屏幕高度的数值，则表示到达了底部.
            object_x += 20 * speedTime * Math.sin(object_y);

        } else {
            isAlive = false;
        }

        if (object_y + object_height > 0) {
            isVisible = true;
        } else {
            isVisible = false;
        }

    }

}
