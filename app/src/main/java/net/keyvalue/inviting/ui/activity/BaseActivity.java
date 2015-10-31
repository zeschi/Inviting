package net.keyvalue.inviting.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by sum on 15-9-27.
 */
public class BaseActivity extends AppCompatActivity {

    private Toast toast;
    private ProgressDialog progressDialog;

    protected void showToast(@StringRes int stringRes) {
        showToast(stringRes, Gravity.BOTTOM);
    }

    protected void showToast(@StringRes int stringRes, int gravity) {
        if (null == toast) {
            toast = Toast.makeText(getApplicationContext(), getString(stringRes), Toast.LENGTH_SHORT);
        }
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    protected void showProgressDialog(@StringRes int stringRes) {
        showProgressDialog(stringRes, true);
    }

    protected void showProgressDialog(@StringRes int stringRes, boolean isCancelable) {
        if (isFinishing()) {
            return;
        }

        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    onProgressDialogCancel();
                }
            });
        }

        progressDialog.setMessage(getString(stringRes));
        progressDialog.setCancelable(isCancelable);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (null == progressDialog) {
            throw new NullPointerException("Make sure you invoked showProgressDialog() method first.");
        }
        progressDialog.dismiss();
    }

    protected void onProgressDialogCancel() {
    }

}
