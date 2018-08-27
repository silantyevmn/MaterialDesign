package silantyevmn.ru.materialdesign.view;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import silantyevmn.ru.materialdesign.R;

/**
 * Created by silan on 30.06.2018.
 */

public class DialogView {
    private DialogView.CompleteCallback completeCallback;

    public DialogView(Activity activity, String title, CompleteCallback completeCallback) {
        this.completeCallback = completeCallback;
        showDialogView(activity, title);
    }

    private void showDialogView(Activity activity, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setPositiveButton(activity.getString(R.string.dialog_button_ok), (dialogInterface, i) -> completeCallback.onComplete());
        builder.setNegativeButton(activity.getString(R.string.dialog_button_cancel), null);
        builder.create().show();
    }

    public interface CompleteCallback {
        void onComplete();
    }
}
