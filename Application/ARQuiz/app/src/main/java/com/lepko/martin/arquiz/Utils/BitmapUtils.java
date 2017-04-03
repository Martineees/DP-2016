package com.lepko.martin.arquiz.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.lepko.martin.arquiz.Entities.Answer;
import com.lepko.martin.arquiz.Entities.Question;
import com.lepko.martin.arquiz.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Martin on 3.3.2017.
 */

public class BitmapUtils {

    public static Bitmap drawTextToBitmap(Context context, Question q) {

        Bitmap bitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);
        // get a canvas to paint over the bitmap
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(android.graphics.Color.TRANSPARENT);

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextSize(100);
        paint.setShadowLayer(6,0,0,Color.BLACK);

        //draw quesiton
        String question = q.getName();

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(question, 0, question.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width())/2;
        int y = (bitmap.getHeight() + bounds.height())/2;

        int offsetTop = bounds.height() + 100;
        canvas.drawText(question, x,  offsetTop, paint);

        if(q.getType() == 0) {

            List<Answer> answers = q.getAnswers();

            for(int i = 0; i < answers.size(); i++) {

                Answer answer = answers.get(i);

                bounds = new Rect();
                paint.getTextBounds(answer.getName(), 0, answer.getName().length(), bounds);
                paint.setTextSize(50);

                offsetTop += bounds.height() + 10;
                canvas.drawText( i+1 +".) " + answer.getName(), 20, offsetTop, paint);
            }

        } else if(q.getType() == 1) {

            String writableQuestion = context.getString(R.string.STR_QUESTION_WRITABLE);

            bounds = new Rect();
            paint.getTextBounds(writableQuestion, 0, writableQuestion.length(), bounds);
            paint.setTextSize(50);

            offsetTop += bounds.height() + 30;
            x = (bitmap.getWidth() - bounds.width())/2;
            canvas.drawText( writableQuestion, x, offsetTop, paint);
        }


        return bitmap;
    }

    public static Bitmap drawStaticTextLayoutToBitmap(Context context, Question q) {

        Bitmap bitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);
        float scale = context.getResources().getDisplayMetrics().density;
        // get a canvas to paint over the bitmap
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(android.graphics.Color.TRANSPARENT);


        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        //draw question
        int height = drawQuestion(bitmap, canvas, scale, q);

        //draw answers
        drawAnswer(bitmap, canvas, scale, q, height, context);


        return bitmap;
    }

    private static int drawQuestion(Bitmap bitmap ,Canvas canvas, float scale, Question q) {

        // new antialised Paint
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextSize(80);
        paint.setShadowLayer(6,0,0,Color.BLACK);

        //draw quesiton
        String question = q.getName();

        int textWidth = canvas.getWidth() - (int) (16 * scale);

        StaticLayout textLayout = new StaticLayout(question, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        int textHeight = textLayout.getHeight();

        float x = (bitmap.getWidth() - textWidth)/2;
        float y = 20;

        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return textHeight;
    }

    private static void drawAnswer(Bitmap bitmap ,Canvas canvas, float scale, Question q, int questionHeight, Context context) {
        // new antialised Paint
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        paint.setShadowLayer(6,0,0,Color.BLACK);

        int textWidth = canvas.getWidth() - (int) (18 * scale);
        int offset = questionHeight + 40;

        if(q.getType() == 0) {
            List<Answer> answers = q.getAnswers();

            for(int i = 0; i < answers.size(); i++) {

                Answer answer = answers.get(i);

                StaticLayout textLayout = new StaticLayout( i + 1 + ") " + answer.getName(), paint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

                canvas.save();
                canvas.translate(40, offset);
                textLayout.draw(canvas);
                canvas.restore();

                offset += textLayout.getHeight() + 20;
            }
        } else if(q.getType() == 1) {
            StaticLayout textLayout = new StaticLayout(context.getString(R.string.STR_QUESTION_WRITABLE), paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

            canvas.save();
            canvas.translate(40, offset);
            textLayout.draw(canvas);
            canvas.restore();
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
