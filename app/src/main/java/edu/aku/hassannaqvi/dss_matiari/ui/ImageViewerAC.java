package edu.aku.hassannaqvi.dss_matiari.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivityImageViewerBinding;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;

public class ImageViewerAC extends AppCompatActivity {

    ActivityImageViewerBinding bi;
    private int currentIndex = 0;

    // List of image files and their names
    private ArrayList<File> filePaths;
    private ArrayList<String> imageNames;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(ImageViewerAC.this, R.layout.activity_image_viewer);

        // Retrieve the list of file paths and names from the intent
        filePaths = (ArrayList<File>) getIntent().getSerializableExtra("image_files");
        imageNames = new ArrayList<>(Arrays.asList(getIntent().getStringArrayExtra("image_names")));

        if (filePaths == null || filePaths.isEmpty()) {
            // Handle the case where no image files are found
            AppConstants.showSimpleSnackBar(ImageViewerAC.this, this.getString(R.string.no_image_found),
                    AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
            return;
        }

        if (filePaths.size() > 1) bi.btnNext.setVisibility(View.VISIBLE);

        // Set initial image
        displayCurrentImage();

        // Button click listeners
        bi.btnPrevious.setOnClickListener(view -> showPreviousImage());
        bi.btnNext.setOnClickListener(view -> showNextImage());
        bi.btnOk.setOnClickListener(view -> finishActivity());
        bi.btnDelete.setOnClickListener(view -> deleteCurrentImage());
    }

    private void showPreviousImage() {
        bi.btnNext.setVisibility(View.VISIBLE);
        currentIndex--;
        displayCurrentImage();
        if (currentIndex == 0) {
            bi.btnPrevious.setVisibility(View.INVISIBLE);
        }
    }

    private void showNextImage() {
        bi.btnPrevious.setVisibility(View.VISIBLE);
        currentIndex++;
        displayCurrentImage();
        if (currentIndex == filePaths.size() - 1) {
            bi.btnNext.setVisibility(View.INVISIBLE);
        }
    }

    private void displayCurrentImage() {
        bi.imageView.setImageURI(Uri.fromFile(filePaths.get(currentIndex)));
    }

    private void deleteCurrentImage() {
        if (filePaths.isEmpty()) return;

        File currentFile = filePaths.get(currentIndex);
        String currentImageName = currentFile.getName();
        if (currentFile.exists()) {
            if (currentFile.delete()) {
                // Remove the file from the list and image names
                filePaths.remove(currentIndex);
                imageNames.remove(currentImageName);

                // Notify the user
                AppConstants.showSimpleSnackBar(ImageViewerAC.this, "Image deleted successfully.", AppConstants.MSG_DURATION, AppConstants.TYPE_SUCCESS);

                // Update the UI
                if (filePaths.isEmpty()) {
                    // Update MainApp.imageNames with the remaining image names
                    MainApp.imageNames = String.join("\n", imageNames);
                    finish();
                } else {
                    if (currentIndex >= filePaths.size()) {
                        currentIndex = filePaths.size() - 1;
                    }
                    displayCurrentImage();
                    updateNavigationButtons();
                }
            } else {
                AppConstants.showSimpleSnackBar(ImageViewerAC.this, "Failed to delete image.", AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
            }
        } else {
            AppConstants.showSimpleSnackBar(ImageViewerAC.this, "Image file does not exist.", AppConstants.MSG_DURATION, AppConstants.TYPE_ERROR);
        }
    }

    private void finishActivity() {
        // Update MainApp.imageNames with the remaining image names
        MainApp.imageNames = String.join("\n", imageNames);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void updateNavigationButtons() {
        if (currentIndex == 0) {
            bi.btnPrevious.setVisibility(View.INVISIBLE);
        } else {
            bi.btnPrevious.setVisibility(View.VISIBLE);
        }
        if (currentIndex == filePaths.size() - 1) {
            bi.btnNext.setVisibility(View.INVISIBLE);
        } else {
            bi.btnNext.setVisibility(View.VISIBLE);
        }
    }
}
