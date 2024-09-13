package edu.aku.hassannaqvi.dss_matiari.ui.sections;

import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.allMwraMigrated;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.fpMwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.households;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwra;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.mwraStatus;
import static edu.aku.hassannaqvi.dss_matiari.core.MainApp.sharedPref;
import static edu.aku.hassannaqvi.dss_matiari.global.AppConstants._EMPTY_;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import edu.aku.hassannaqvi.dss_matiari.R;
import edu.aku.hassannaqvi.dss_matiari.core.MainApp;
import edu.aku.hassannaqvi.dss_matiari.database.DssRoomDatabase;
import edu.aku.hassannaqvi.dss_matiari.databinding.ActivitySectionCBinding;
import edu.aku.hassannaqvi.dss_matiari.global.AppConstants;
import edu.aku.hassannaqvi.dss_matiari.global.DateUtils;
import edu.aku.hassannaqvi.dss_matiari.models.Mwra;

public class SectionCActivity extends AppCompatActivity {

    private static final String TAG = "SectionCxActivity";
    ActivitySectionCBinding bi;
    private DssRoomDatabase db;

    private Mwra.SC sC;
    private Mwra.SD sD;
    public static Mwra dbMWRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = sharedPref.getString("lang", "1");
        setTheme(lang.equals("1") ? R.style.AppThemeEnglish1 : R.style.AppThemeUrdu);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        db = MainApp.appInfo.dbHelper;

        try {
            mwra = db.mwraDao().getFollowupsBySno(MainApp.households.getUid(), MainApp.fpMwra.getRb01(), MainApp.fpMwra.getFRound());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(Followups): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Mwra.populateMetaFollowups();
        sC = Mwra.SC.getData();
        if (sC == null) {
            sC = new Mwra.SC();
            sC.populateMeta();
        }

        mwra.setSC(sC);
        bi.setFollowups(sC);
        bi.setFollowupsMain(mwra);

        initUI();
    }

    public void initUI() {

//        String date = DateUtils.addSubDays(DateUtils.getCurrentDateTime(AppConstants.APP_DATE_FORMAT), -7);
        String date = DateUtils.changeDateFormat("2024-01-01");
        bi.rb01a.setMinDate(date);

        // Set Round Number from followups data
        MainApp.ROUND = MainApp.fpMwra.getFRound();

        // Calculate age of woman according to registration date
//        long daysdiff = Mwra.CalculateAge(fpMwra.getReg_date());
        long daysdiff = Mwra.CalculateAge(DateUtils.getFormattedDateTime(
                fpMwra.getRa01().getDate(), AppConstants.CUSTOM_SERVER_DATE_TIME_FORMAT, AppConstants.APP_DATE_FORMAT));
        long months, years;

        int ageMonths = !AppConstants.isEmpty(fpMwra.getAgeM()) ? Integer.parseInt(fpMwra.getAgeM()) * 30 : Integer.parseInt(fpMwra.getRb05()) * 12 * 30;
        long cumulativeDays = ageMonths + daysdiff;
        months = cumulativeDays / 30;
        years = months / 12;

        long actualAge = 0;

        actualAge = years;
        bi.rb05.setText(String.valueOf(actualAge));
        mwra.setAgeM(Long.toString(months));

        // Enable Overage option in VISIT status according to woman age
        if (actualAge < 50) {
            if (Integer.parseInt(households.getVisitNo()) < 2) {
                bi.rb1001.setEnabled(true);
                bi.rb1002.setEnabled(true);
                bi.rb1003.setEnabled(true);
                bi.rb1004.setEnabled(true);
                bi.rb1005.setEnabled(true);
                bi.rb1006.setEnabled(true);
                bi.rb1007.setEnabled(true);
                bi.rb1008.setEnabled(false);
                bi.rb1009.setEnabled(false);
            } else if (Integer.parseInt(households.getVisitNo()) >= 2) {
                bi.rb1001.setEnabled(true);
                bi.rb1002.setEnabled(true);
                bi.rb1003.setEnabled(true);
                bi.rb1004.setEnabled(false);
                bi.rb1005.setEnabled(true);
                bi.rb1006.setEnabled(true);
                bi.rb1007.setEnabled(true);
                bi.rb1008.setEnabled(true);
                bi.rb1009.setEnabled(false);
            }
        } else {
            bi.rb1001.setEnabled(false);
            bi.rb1002.setEnabled(false);
            bi.rb1003.setEnabled(false);
            bi.rb1004.setEnabled(false);
            bi.rb1005.setEnabled(false);
            bi.rb1006.setEnabled(false);
            bi.rb1007.setEnabled(false);
            bi.rb1008.setEnabled(false);
            bi.rb1009.setEnabled(true);
        }

        setImmersive(true);
        bi.btnContinue.setText(MainApp.mwra.getUid().equals("") ? "Save" : "Update");

        /********************************On Click Listeners******************************/

        bi.rb01a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setDateRanges();
            }
        });

        bi.rb10.setOnCheckedChangeListener((radioGroup, i) -> {
            boolean isAvailable = false;
            boolean isMigratedOrRefused = false;
            if (bi.rb1004.isChecked()) {
                for (String[] arr : mwraStatus.keySet()) {
                    if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                        isAvailable = true;
                        break;
                    }
                }
                if (!isAvailable) {
                    mwraStatus.put(new String[]{fpMwra.getMuid(), fpMwra.getHdssid()}, false);
                }
                sC.setRb06(fpMwra.getRb06());
                sC.setRb04(fpMwra.getRb04());
            } else {
                sC.setRb06(sC.getRb06());
                sC.setRb04(fpMwra.getRb04());
                if (!mwraStatus.isEmpty()) {
                    for (String[] arr : mwraStatus.keySet()) {
                        if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                            mwraStatus.remove(arr);
                            break;
                        }
                    }
                }
            }

            // Put status of Migrated or Refused in its HashMap
            if (bi.rb1002.isChecked() || bi.rb1003.isChecked()) {
                for (String[] arr : allMwraMigrated.keySet()) {
                    if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                        isMigratedOrRefused = true;
                        break;
                    }
                }
                if (!isMigratedOrRefused) {
                    allMwraMigrated.put(new String[]{fpMwra.getMuid(), fpMwra.getHdssid()}, false);
                }
            } else {
                sC.setRb06(sC.getRb06());
                sC.setRb04(fpMwra.getRb04());
                if (!allMwraMigrated.isEmpty()) {
                    for (String[] arr : allMwraMigrated.keySet()) {
                        if (arr[0].equals(fpMwra.getMuid()) && arr[1].equals(fpMwra.getHdssid())) {
                            allMwraMigrated.remove(arr);
                            break;
                        }
                    }
                }
            }
        });


        bi.rb19.setOnCheckedChangeListener((radioGroup, i) -> {
            if (bi.rb1901.isChecked() || bi.rb2605.isChecked()) {
                MainApp.totalChildCount = 1;
            } else if (bi.rb1902.isChecked()) {
                MainApp.totalChildCount = 2;
            } else if (bi.rb1903.isChecked()) {
                MainApp.totalChildCount = 3;
            }
        });

        bi.rb17.setOnCheckedChangeListener((group, checkedId) -> {
            if (bi.rb1701.isChecked() || bi.rb1605.isChecked()) {
                MainApp.totalChildCount = 1;
            } else if (bi.rb1702.isChecked() && !bi.rb1605.isChecked()) {
                MainApp.totalChildCount = 2;
            } else if (bi.rb1703.isChecked()) {
                MainApp.totalChildCount = 3;
            }
        });

        bi.rb1605.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bi.rb1701.setEnabled(false);
                bi.rb1701.setChecked(false);
                bi.rb1703.setEnabled(false);
                bi.rb1703.setChecked(false);
                bi.rb1702.setEnabled(true);
            } else {
                bi.rb1701.setEnabled(true);
                bi.rb1703.setEnabled(true);
                bi.rb1702.setEnabled(true);
            }
        });

        bi.rb26.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            bi.rb19.clearCheck();
            bi.rb21a.setText(R.string.rb15);
            bi.rb1901.setEnabled(true);
            bi.rb1902.setEnabled(true);
            bi.rb1903.setEnabled(true);
            sC.setRb19("");
            bi.rb20.setMaxvalue(41);
            bi.rb20.setMinvalue(28);
            if (checkedId == bi.rb2605.getId()) {
                bi.rb19.clearCheck();
                bi.rb1901.setEnabled(false);
                bi.rb1901.setChecked(false);
                bi.rb1903.setEnabled(false);
                bi.rb1903.setChecked(false);
                bi.rb1902.setEnabled(true);
                sC.setRb19("2");
            } else if (checkedId == bi.rb2603.getId()) {
                bi.rb20.setMaxvalue(27);
                bi.rb20.setMinvalue(3);
                bi.rb21a.setText(R.string.rb15_mis);
            }
        });

        // If previously not pregnant save the last result
        if (!AppConstants.isEmpty(fpMwra.getRb07()) && fpMwra.getRb07().equals("2"))
            sC.setRb16(fpMwra.getRb07());
    }

    private void setDateRanges() {
        try {

            // Set time from RC01a
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(Objects.requireNonNull(sdf.parse(sC.getRb01a())));// all done

            //cal2.setTime(sdf.parse(fpMwra.getRa01().substring(9, 19)));
            cal2.setTime(Objects.requireNonNull(sdf.parse(fpMwra.getRa01().getDate())));
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            // Set MinEDD to 9 months from DOV
            cal.add(Calendar.MONTH, -9);
            String minEDD = sdf.format(cal.getTime());

            // Set to DOV
            cal.add(Calendar.MONTH, +9);
            String maxDD = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH, -3);
            String DD = sdf.format(cal2.getTime());

            bi.rb15.setMinDate(minEDD);
            bi.rb21.setMinDate(DD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void btnContinue(View view) throws JSONException {
        if (!formValidation()) return;
        Mwra.saveMainDataFup(households.getUid(), sC.getRb01(), fpMwra.getFRound(), sC);
        mwra.setSNo(sC.getRb01());
        mwra.setIstatus(sC.getRb10());
        if (!mwra.getUid().contains("_")) {
            if (sC.getRb18().equals("1")) {
                mwra.setPregnum(String.valueOf(Integer.parseInt(mwra.getPregnum()) + 1));
            }
            if (!AppConstants.isEmpty(fpMwra.getRb07()) && fpMwra.getRb07().equals("2") && sC.getRb18().equals("2")) {
                mwra.setPregnum(!AppConstants.isEmpty(fpMwra.getPregCount()) ? fpMwra.getPregCount() : "0");
            }
        } else {
            // For Edit Mode
            dbMWRA = db.mwraDao().getFollowupsBySno(MainApp.households.getUid(), MainApp.fpMwra.getRb01(), MainApp.fpMwra.getFRound());
            if (dbMWRA.getSC().getRb18().equals("1") && sC.getRb18().equals("2")) {
                mwra.setPregnum(String.valueOf(Integer.parseInt(mwra.getPregnum()) - 1));
            } else if (dbMWRA.getSC().getRb18().equals("2") && sC.getRb18().equals("1")) {
                mwra.setPregnum(String.valueOf(Integer.parseInt(mwra.getPregnum()) + 1));
            }
        }
        if (!bi.rb1001.isChecked() || bi.rb1401.isChecked()) {
            if (mwra.getSD() == null) {
                sD = new Mwra.SD();
                sD.setRb07(fpMwra.getRb07() != null ? fpMwra.getRb07() : _EMPTY_);
            }
            Mwra.SD.saveData(sD);
        }
        Mwra.SC.saveData(sC);

        if (bi.rb1001.isChecked()) {
            switch (fpMwra.getRb06()) {
                // Married in Previous Round
                case "1":
                    // Pregnant
                    if (fpMwra.getRb07().equals("1")) {
                        // If pregnancy continued
                        if (bi.rb1401.isChecked()) {
                            setResult(RESULT_OK);
                            //finish();
                        } else {  // If Pregnancy ended
                            if (bi.rb1601.isChecked() || bi.rb1605.isChecked()) // If Live Birth
                            {
                                if (fpMwra.getChild_count() != null) {
                                    MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                } else {
                                    MainApp.prevChildCount = 0;
                                }
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            } else { // If not live birth
                                Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            }
                        }
                    } else if (fpMwra.getRb07().equals("2") && bi.rb1801.isChecked()) {   // Not Pregnant
                        if (fpMwra.getChild_count() != null) {
                            MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                        } else {
                            MainApp.prevChildCount = 0;
                        }
                        if (bi.rb2601.isChecked() || bi.rb2605.isChecked()) {
                            Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            finish();
                            startActivity(forwardIntent);
                        } else if (bi.rb2603.isChecked()) {
                            AppConstants.gotoActivity(this, SectionDActivity.class, true);
                        } else {
                            setResult(RESULT_OK);
                            finish();
                        }
                    } else if (fpMwra.getRb07().equals("2") && bi.rb1802.isChecked()) {
                        Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                        forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                        setResult(RESULT_OK, forwardIntent);
                        startActivity(forwardIntent);
                    }
                    break;

                // Divorced
                case "2":
                    // Pregnant
                    if (fpMwra.getRb07().equals("1")) {
                        if (bi.rb1401.isChecked()) {  // If Pregnancy Continued
                            setResult(RESULT_OK);
                        } else {     // If Pregnancy ended
                            if (bi.rb1601.isChecked() || bi.rb1605.isChecked()) {    // Live Birth
                                if (fpMwra.getChild_count() != null) {
                                    MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                } else {
                                    MainApp.prevChildCount = 0;
                                }
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            } else {
                                setResult(RESULT_OK);
                            }
                        }
                    } else {      // Not Pregnant & Delivered baby in last 3 months / unreported pregnancy
                        if (bi.rb1801.isChecked()) {
                            if (fpMwra.getChild_count() != null) {
                                MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                            } else {
                                MainApp.prevChildCount = 0;
                            }
                            if (bi.rb2601.isChecked() || bi.rb2605.isChecked()) {
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                finish();
                                startActivity(forwardIntent);
                            } else if (bi.rb2603.isChecked()) {
                                AppConstants.gotoActivity(this, SectionDActivity.class, true);
                            } else {
                                setResult(RESULT_OK);
                                finish();
                            }
                            // Marital status changed
                        } else if (bi.rb0601.isChecked() || bi.rb1802.isChecked() && !mwra.getPrePreg().equals("2")) {
                            Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);
                        } else {
                            setResult(RESULT_OK);
                        }
                    }
                    break;

                // Widow
                case "3":
                    // Pregnant
                    if (fpMwra.getRb07().equals("1")) {
                        if (bi.rb1401.isChecked()) {  // If Pregnancy Continued
                            setResult(RESULT_OK);
                        } else {     // If Pregnancy ended
                            if (bi.rb1601.isChecked() || bi.rb1605.isChecked()) {    // Live Birth
                                if (fpMwra.getChild_count() != null) {
                                    MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                } else {
                                    MainApp.prevChildCount = 0;
                                }
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                startActivity(forwardIntent);
                            } else {
                                setResult(RESULT_OK);
                            }
                        }
                    } else {      // Not Pregnant
                        // Marital status changed
                        if (bi.rb0601.isChecked() || bi.rb1802.isChecked() && !fpMwra.getRb07().equals("2")) {
                            Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);
                            // Delivered baby in last 3 months / Unreported pregnancy
                        } else if (bi.rb1801.isChecked()) {
                            if (fpMwra.getChild_count() != null) {
                                MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                            } else {
                                MainApp.prevChildCount = 0;
                            }
                            if (bi.rb2601.isChecked() || bi.rb2605.isChecked()) {
                                Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                setResult(RESULT_OK, forwardIntent);
                                finish();
                                startActivity(forwardIntent);
                            } else if (bi.rb2603.isChecked()) {
                                AppConstants.gotoActivity(this, SectionDActivity.class, true);
                            } else {
                                setResult(RESULT_OK);
                                finish();
                            }
                        } else {
                            setResult(RESULT_OK);
                        }
                    }
                    break;

                // Separated
                case "5":
                    // Pregnant
                    if (fpMwra.getRb07().equals("1")) {

                        if (bi.rb1401.isChecked()) {  // If Pregnancy Continued
                            setResult(RESULT_OK);
                        } else {     // If Pregnancy ended
                            if (bi.rb1601.isChecked() || bi.rb1605.isChecked()) {    // Live Birth
                                if (fpMwra.getChild_count() != null) {
                                    MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                                } else {
                                    MainApp.prevChildCount = 0;
                                }
                                if (bi.rb2601.isChecked() || bi.rb2605.isChecked()) {
                                    Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                                    forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                    setResult(RESULT_OK, forwardIntent);
                                    finish();
                                    startActivity(forwardIntent);
                                } else if (bi.rb2603.isChecked()) {
                                    AppConstants.gotoActivity(this, SectionDActivity.class, true);
                                } else {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            } else {
                                setResult(RESULT_OK);
                            }
                        }
                    } else {      // Not Pregnant
                        // Marital status changed
                        if (bi.rb0601.isChecked() || bi.rb1802.isChecked() && !mwra.getPrePreg().equals("2")) {
                            Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);

                            // Delivered baby in last 3 months / Unreported pregnancy
                        } else if (bi.rb1801.isChecked()) {
                            if (fpMwra.getChild_count() != null) {
                                MainApp.prevChildCount = Integer.parseInt(fpMwra.getChild_count());
                            } else {
                                MainApp.prevChildCount = 0;
                            }
                            Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);
                        } else {
                            setResult(RESULT_OK);
                        }
                    }
                    break;

                case "4": // Unmarried in previous round
                    // if get married in current round
                    if (!bi.rb0604.isChecked() && bi.rb1802.isChecked()) {
                        Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                        forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                        setResult(RESULT_OK, forwardIntent);
                        startActivity(forwardIntent);
                        // Delivered baby within last 3 months / Unreported pregnancy
                    } else if (!bi.rb0604.isChecked() && bi.rb1801.isChecked()) {
                        if (bi.rb2601.isChecked() || bi.rb2605.isChecked()) {
                            MainApp.prevChildCount = 0;
                            Intent forwardIntent = new Intent(this, SectionEActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);
                        } else if (bi.rb2603.isChecked()) {
                            Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);
                        } else {
                            Intent forwardIntent = new Intent(this, SectionDActivity.class).putExtra("complete", true);
                            forwardIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            setResult(RESULT_OK, forwardIntent);
                            startActivity(forwardIntent);
                        }
                    }
                    // if still unmarried
                    else if (bi.rb0604.isChecked()) {
                        setResult(RESULT_OK);
                    }
                    break;
            }
            finish();
        } else {
            setResult(RESULT_OK);
            finish();
        }
    }

    public void btnEnd(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.GrpName))
            return false;
        setDateRanges();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.lockScreen(this);
    }
}