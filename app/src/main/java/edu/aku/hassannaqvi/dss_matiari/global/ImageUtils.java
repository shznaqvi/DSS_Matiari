package edu.aku.hassannaqvi.dss_matiari.global;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import edu.aku.hassannaqvi.dss_matiari.R;

public class ImageUtils {

    // Get Gallery Path
    public static File getGalleryDir(Context context) {
        return new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), AppConstants.PROJECT_NAME);
    }

    // Image name scheme
    public static String generateImageName(String sectionName, String formId) {
        return String.format(Locale.getDefault(), "%s_%s_%s_%s.jpg", AppConstants.PROJECT_NAME, sectionName,
                System.currentTimeMillis(), formId);
    }

    // Rename Image
    public static void renameTo(Activity activity, String fromName, String toName) {
        File dir = getGalleryDir(activity);
        if (dir.exists()) {
            File from = new File(dir, fromName);
            File to = new File(dir, toName);
            if (from.exists())
                from.renameTo(to);
        }
    }

    // Get all images from folder if exists
    public static List<File> getAllImagesFiles(Activity activity) {
        File photosDirectory = getGalleryDir(activity);
        if (photosDirectory.exists()) {
            File[] files = photosDirectory.listFiles(file -> (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg")));
            if (files != null && files.length > 0) {
                return Arrays.asList(files);
            } else {
                AppConstants.showSimpleSnackBar(activity, activity.getString(R.string.no_photos_to_upload),
                        AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
            }
        } else {
            AppConstants.showSimpleSnackBar(activity, activity.getString(R.string.no_photos_taken),
                    AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
        }
        return null;
    }

    // Get images name from folder if exists
    public static ArrayList<File> getImageFilesByNames(Activity activity, String[] imageNames) {
        ArrayList<File> resultFiles = new ArrayList<>();

        File photosDirectory = getGalleryDir(activity);
        if (photosDirectory.exists()) {
            File[] files = photosDirectory.listFiles(file -> (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg")));
            if (files != null && files.length > 0) {
                List<String> imageNamesList = Arrays.asList(imageNames);  // Convert array to list
                for (File file : files) {
                    String fileName = file.getName();
                    if (imageNamesList.contains(fileName))
                        resultFiles.add(file);
                }

                if (!resultFiles.isEmpty())
                    return resultFiles;
                else
                    AppConstants.showSimpleSnackBar(activity, activity.getString(R.string.no_image_found),
                            AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
            } else
                AppConstants.showSimpleSnackBar(activity, activity.getString(R.string.no_photos_to_upload),
                        AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
        } else
            AppConstants.showSimpleSnackBar(activity, activity.getString(R.string.no_photos_taken),
                    AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);

        return null;
    }

    // Get all images names if exist
    // This is currently used for sync list display
    public static List<String> getAllImagesNames(Activity activity) {
        List<File> allImages = getAllImagesFiles(activity);
        if (allImages != null && allImages.size() > 0) {
            List<String> imagesNames = new ArrayList<>();
            for (File file : allImages) {
                imagesNames.add(file.getName());
            }
            return imagesNames;
        }
        return null;
    }

    // Move image file from app folder to 'uploaded' folder to prevent permanently
    // delete image after successful upload
    public static void moveImage(Activity activity, String inputFile) {

        Log.d("Move_File", "moveFile: " + inputFile);
        InputStream in;
        OutputStream out;
        File inputPath = AppConstants.GALLERY_DIR;
        File outputPath = new File(AppConstants.GALLERY_DIR + File.separator + "uploaded");
        try {
            //create output directory if it doesn't exist (not needed, just a precaution)
            if (!outputPath.exists()) {
                boolean isDirCreated = outputPath.mkdirs();
                if (!isDirCreated) {
                    AppConstants.showSimpleSnackBar(activity, activity.getString(R.string.dir_not_created), AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
                    return;
                }
            }

            in = new FileInputStream(inputPath + File.separator + inputFile);
            out = new FileOutputStream(outputPath + File.separator + inputFile);
            Log.d("Image_Moved_From", "moveFile: (in)" + in);
            Log.d("Image_Moved_To", "moveFile: (out)" + out);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();

            // delete the original file
            new File(inputPath + File.separator + inputFile).delete();

        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
    }

    // Delete all images from pictures directory
    public static void deleteAllImages(Activity activity) {
        File picturesDir = getGalleryDir(activity);
        if (picturesDir.isDirectory()) {
            File[] files = picturesDir.listFiles(file -> (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg")));
            if (files != null && files.length > 0) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    // Convert Image Uri to Byte
    public static byte[] convertImageUriToByte(Activity activity, Uri uri) {
        byte[] data = null;
        try {
            ContentResolver cr = activity.getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Convert Image Bitmap to Byte
    public static byte[] convertImageBitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    // To take the screenshot of portion of the screen
    // i.e. layout/view given
    public static Bitmap takePortionScreenshot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}
