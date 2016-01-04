package eheya.kentaku.net.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by LinhNguyen on 1/4/2016.
 */
public class FileUltil {

    public static Camera.PictureCallback getPictureCallback(final CameraPreview mPreview, final Camera mCamera,final  int rotation) {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File myDir = new File("/sdcard/Android/data/Linh");
                myDir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File file = new File(myDir, timeStamp+".jpg");

                // check img exists
                if (file.exists()) file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(data);
                    out.flush();
                    out.close();

                    // rotation img output
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotation);
                    Bitmap bmp = BitmapFactory.decodeFile(file.getPath());
                    bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

                    FileOutputStream output = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 85, output);
                    output.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //refresh camera to continue preview
                mPreview.refreshCamera(mCamera);
            }
        };
        return picture;
    }
}
