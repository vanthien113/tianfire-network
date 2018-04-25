package com.example.thienpro.mvp_firebase.ultils.widget;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.esafirm.imagepicker.features.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class SHBitmapHelper {
    /**
     *
     */
    protected SHBitmapHelper() {

    }

    public static void takePhoto(Fragment fragment, int requestCode) {
        ImagePicker.create(fragment)
                .returnAfterFirst(true)
                .imageTitle("Tap to select")
                .showCamera(true)
                .single()
                .imageDirectory("Camera")
                .start(requestCode);
    }

    public static void takePhoto(Activity activity, int requestCode) {
        ImagePicker.create(activity)
                .returnAfterFirst(true)
                .imageTitle("Tap to select")
                .showCamera(true)
                .single()
                .imageDirectory("Camera")
                .start(requestCode);
    }

    public static void bindCircularImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(view) {
                          @Override
                          protected void setResource(Bitmap resource) {
                              RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                              circularBitmapDrawable.setCircular(true);
                              view.setImageDrawable(circularBitmapDrawable);
                          }
                      }
                );
    }

    public static void bindImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    public static Bitmap roundBitmap(Bitmap bitmap, int radius) {
        if (bitmap == null) {
            return null;
        }

        Paint paintForRound = new Paint();
        paintForRound.setAntiAlias(true);
        paintForRound.setColor(0xff424242);
        paintForRound.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Rect RECT = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF RECTF = new RectF(RECT);

        canvas.drawARGB(0, 0, 0, 0);
        paintForRound.setXfermode(null);

        canvas.drawRoundRect(RECTF, radius, radius, paintForRound);

        paintForRound.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, RECT, RECT, paintForRound);

        return output;
    }

    public static Uri getUriAndCompressBitmap(Context context, String path){
        return getBitmapUri(context, SHBitmapHelper.decodeFile(new File(path), 500, 500));
    }

    public static Uri getBitmapUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /**
     * Resize bitmap
     *
     * @param context
     * @param uri
     * @param reqWidth
     * @param reqHeight
     * @return Bitmap
     */
    public static Bitmap decodeFile(Context context, Uri uri, int reqWidth,
                                    int reqHeight) {
        Bitmap bitmap = null;
        String path = null;
        try {
            if (uri != null) {
                path = getRealPathFromURI1(context, uri);
            }

            if (path == null || path.length() <= 0) {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//                return null;
            }

            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);

            // decode with inSampleSize
            o.inSampleSize = calculateInSampleSize(o, null, reqWidth, reqHeight);
            o.inJustDecodeBounds = false;
            if (path != null) {
                bitmap = BitmapFactory.decodeFile(path, o);
            } else {
                bitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, false);
            }
            if (bitmap != null) {
                ExifInterface exif = new ExifInterface(path);
                int tag = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        -1);
                int degree = 0;
                if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
                    degree = 90;
                } else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
                    degree = 180;
                } else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
                    degree = 270;
                }

                Bitmap temp = null;
                if (degree != 0) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(degree);

                    temp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
                }

                if (temp != null) {
                    bitmap.recycle();
                    bitmap = null;

                    bitmap = temp;
                }
                temp = null;
            }
        } catch (Exception ex) {
            bitmap = null;
        } catch (OutOfMemoryError oome) {
            bitmap = null;
        }

        return bitmap;
    }

    /**
     * Resize bitmap
     *
     * @param aBitmap
     * @param reqWidth
     * @param reqHeight
     * @return Bitmap
     */
    public static Bitmap decodeFile(Bitmap aBitmap, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;

        try {
            bitmap = aBitmap;
            if (bitmap == null) {
                return bitmap;
            }

            // decode with inSampleSize
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = calculateInSampleSize(null, bitmap,
                    reqWidth, reqHeight);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                    byteArray.length, options);

            options = null;
            byteArray = null;
        } catch (Exception ex) {
            bitmap = null;
        } catch (OutOfMemoryError oome) {
            bitmap = null;
        }

        return bitmap;
    }

    /**
     * Resize bitmap
     *
     * @param f         File
     * @param reqWidth  int
     * @param reqHeight int
     * @return Bitmap
     */
    public static Bitmap decodeFile(File f, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;

        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = calculateInSampleSize(o, null, reqWidth,
                    reqHeight);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null,
                    o2);

            o = null;
            o2 = null;
            String path = f.getPath();
            if (bitmap != null) {
                ExifInterface exif = new ExifInterface(path);
                int tag = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                int degree = 0;
                if ((tag == ExifInterface.ORIENTATION_ROTATE_180)) {
                    degree = 180;
                } else if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
                    degree = 90;
                } else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
                    degree = 270;
                }

                Bitmap temp = null;
                if (degree != 0) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(degree);

                    temp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
                }

                if (temp != null) {
                    bitmap.recycle();

                    bitmap = temp;
                }
                temp = null;
            }

        } catch (Exception ex) {
            bitmap = null;
        } catch (OutOfMemoryError oome) {
            bitmap = null;
        }

        return bitmap;
    }

    /**
     * Tính tỉ lệ scale Có thể truyền vào BitmapFactiry.Option hoặc bitmap để
     * lấy chiều cao của ảnh
     *
     * @param bitmap
     * @param reqWidth
     * @param reqHeight
     * @return int
     */
    public static int calculateInSampleSize(BitmapFactory.Options option,
                                            Bitmap bitmap, int reqWidth, int reqHeight) {
        int height = 0;
        int width = 0;

        if (option != null) {
            height = option.outWidth;
            width = option.outHeight;
        } else {
            height = bitmap.getHeight();
            width = bitmap.getWidth();
        }

        if (height == 0 || width == 0) {
            return 0;
        }

        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
    }

    /**
     * @param context
     * @param contentUri
     * @return String
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String result = null;

        try {
            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor cursor = ((ActivityObject) context).managedQuery(contentUri, proj,
//                    null, null, null);
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(contentUri, proj, null, null, null);
            if (cursor != null && !cursor.isClosed()) {
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(columnIndex);
                if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                    cursor.close();
                }
            }
            cursor = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private static String getRealPathFromURI1(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public static Bitmap getRightInterfaceBitmap(String filepath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        try {
            if (bitmap != null) {
                ExifInterface exif = new ExifInterface(filepath);
                int tag = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        -1);
                int degree = 0;
                if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
                    degree = 90;
                } else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
                    degree = 180;
                } else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
                    degree = 270;
                }

                Bitmap temp = null;
                if (degree != 0) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(degree);

                    temp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
                }

                if (temp != null) {
                    bitmap.recycle();
                    bitmap = null;

                    bitmap = temp;
                }
                temp = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static Bitmap fastblur(Bitmap sentBitmap, int radius) {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
//        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

//        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }
}