package com.example.keval.e_moss.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.keval.e_moss.R;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    private static ProgressDialog pDialog;
    public static int selectedStatus;
    public static JSONObject curruntWorkObj;

    public static void showProgressDialog(Context activity, String msg) {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.show();
    }


    public static JSONObject getCurruntWorkObj() {
        return curruntWorkObj;
    }

    public static void setCurruntWorkObj(JSONObject curruntWorkObj) {
        curruntWorkObj = curruntWorkObj;
    }

    public static boolean isGPS(Context context) {

        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;

    }

    public static void cancelProgressDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.cancel();
    }

    public static void showLog(Activity activity, JSONArray jsonArray) {
        Log.i("############     " + activity.getLocalClassName(), jsonArray.toString());
    }

    public static boolean isValidEmail(CharSequence strEmail) {
        return !TextUtils.isEmpty(strEmail) && android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    public static File getAppPath(Activity activity) {
        return new File(Environment.getExternalStorageDirectory() + "/" + activity.getResources().getString(R.string.app_name) + "/");
//        return new File(activity.getFilesDir(), "My Account");
    }

    public static String getPathFromURI(final Context context, final Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if (Constants.FILE_TYPE_IMAGE_STR.equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if (Constants.FILE_TYPE_VIDEO_STR.equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if (Constants.FILE_TYPE_Audio_STR.equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String dateFormat(long date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public static String timeFormat(long time) {
        return new SimpleDateFormat("hh:mm aa").format(time);
    }

    public static Date convertStringToDate(String date) {

        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date convertStringToDateTime(String date) {

        try {
            return new SimpleDateFormat("dd-MM-yyyy hh:mm aa").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    private static boolean dateEqualCompare(String fromCompare, String toCompare) {

        long selectDate = convertStringToDate(fromCompare).getTime();
        long systemDate = convertStringToDate(toCompare).getTime();

        return selectDate == systemDate;
    }

    private static boolean timeCompare(String fromCompare, String toCompare) {

        long selectTime = 0;
        long systemTime = 0;

        try {
            selectTime = new SimpleDateFormat("hh:mm aa").parse(fromCompare).getTime();
            systemTime = new SimpleDateFormat("hh:mm aa").parse(toCompare).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return selectTime >= systemTime;
    }

    public static void closeKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager.isAcceptingText())
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri, Boolean bBoolean) {
        String[] projectData = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, projectData, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index;
            if (bBoolean)
                column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            else
                column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri, Boolean bBoolean) {
        String[] proj;

        int column_index;
        Cursor cursor;
        if (bBoolean) {
            proj = new String[]{MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        } else {
            proj = new String[]{MediaStore.Video.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String indexId(String constantsString) {

        if (!TextUtils.isEmpty(SharedPreferenceUtil.getString(constantsString, "")))
            SharedPreferenceUtil.putValue(constantsString, (Integer.valueOf(SharedPreferenceUtil.getString(constantsString, "")) + 1) + "");
        else
            SharedPreferenceUtil.putValue(constantsString, "1");

        SharedPreferenceUtil.save();
        return SharedPreferenceUtil.getString(constantsString, "");

    }

    public static String encryptData(String dataToEncrypt) {

        byte[] byte_arr = dataToEncrypt.getBytes();
        String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

        return image_str;

    }

    public static String decodeData(String dataToDecrypt) {

        byte[] image_str1 = Base64.decode(dataToDecrypt, 0);
        String convertToString = new String(image_str1);

        return convertToString;
    }

    public static void showKeyboard(Context activityContext, final EditText editText) {

        final InputMethodManager imm = (InputMethodManager)
                activityContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (!editText.hasFocus()) {
            editText.requestFocus();
        }

        editText.post(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            }
        });
    }

    public static boolean dateGreaterCompare(String greaterDate, String lessDate) {

        long greaterDateLong = convertStringToDate(greaterDate).getTime();
        long lessDateLong = convertStringToDate(lessDate).getTime();

        return greaterDateLong > lessDateLong;
    }

    public static boolean dateLessCompare(String greaterDate, String lessDate) {

        long greaterDateLong = convertStringToDate(greaterDate).getTime();
        long lessDateLong = convertStringToDate(lessDate).getTime();

        return greaterDateLong < lessDateLong;
    }

    public static boolean isLegalPassword(String pass) {

        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", Pattern.DOTALL);
        Matcher m = p.matcher(pass);

        return m.find();
    }

    public static boolean isSpecialChar(String pass) {

        Pattern p = Pattern.compile("[&@!#+]", Pattern.DOTALL);
        Matcher m = p.matcher(pass);

        return m.find();
    }
}