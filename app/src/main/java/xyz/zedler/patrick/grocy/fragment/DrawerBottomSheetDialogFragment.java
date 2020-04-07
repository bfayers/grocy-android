package xyz.zedler.patrick.grocy.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import xyz.zedler.patrick.grocy.MainActivity;
import xyz.zedler.patrick.grocy.R;

public class DrawerBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private final static String TAG = "DrawerBottomSheet";

    private MainActivity activity;
    private View view;
    private String uiMode;
    private long lastClick = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(), R.style.Theme_Grocy_BottomSheetDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottomsheet_bottomdrawer, container, false);
        activity = (MainActivity) getActivity();
        /*uiMode = PreferenceManager.getDefaultSharedPreferences(activity).getString(
                MainActivity.PREF_UI_MODE, MainActivity.DEFAULT_UI_MODE
        );*/
        setOnClickListeners(
                R.id.linear_settings,
                R.id.linear_feedback,
                R.id.linear_help
        );
        /*if(uiMode.startsWith("saved")) {
            select(R.id.linear_memory, R.id.text_saved);
        } else if(uiMode.startsWith("channels")) {
            select(R.id.linear_channels, R.id.text_channels);
        }*/
        return view;
    }

    private void setOnClickListeners(@IdRes int... viewIds) {
        for (int viewId : viewIds) {
            view.findViewById(viewId).setOnClickListener(this);
        }
    }

    public void onClick(View v) {
        if(SystemClock.elapsedRealtime() - lastClick < 5000) return;
        lastClick = SystemClock.elapsedRealtime();

        Bundle bundle = new Bundle();
        /*switch(v.getId()) {
            case R.id.linear_memory:
                if(!uiMode.startsWith("saved")) {
                    bundle.putString("mode", MainActivity.UI_SAVED_DEFAULT);
                }
                break;
            case R.id.linear_channels:
                if(!uiMode.startsWith("channels")) {
                    bundle.putString("mode", MainActivity.UI_CHANNELS_DEFAULT);
                }
                break;
            case R.id.linear_settings:
                startAnimatedIcon(R.id.image_settings);
                startActivity(new Intent(activity, SettingsActivity.class));
                break;
            case R.id.linear_feedback:
                startAnimatedIcon(R.id.image_feedback);
                startActivity(new Intent(activity, FeedbackActivity.class));
                break;
            case R.id.linear_help:
                startAnimatedIcon(R.id.image_help);
                startActivity(new Intent(activity, HelpActivity.class));
                break;
        }*/
        /*if(bundle.getString("mode") == null) {
            new Handler().postDelayed(this::dismiss, 500);
        } else {
            activity.replaceFragment(MainActivity.FRAGMENT_PAGE, bundle, true);
            dismiss();
        }*/
    }

    private void select(@IdRes int linearLayoutId, @IdRes int textViewId) {
        view.findViewById( linearLayoutId).setBackgroundResource(R.drawable.bg_drawer_item_selected);
        ((TextView) view.findViewById(textViewId)).setTextColor(
                ContextCompat.getColor(activity, R.color.secondary)
        );
    }

    private void startAnimatedIcon(@IdRes int viewId) {
        try {
            ((Animatable) ((ImageView) view.findViewById(viewId)).getDrawable()).start();
        } catch (ClassCastException cla) {
            Log.e(TAG, "startAnimatedIcon(ImageView) requires AVD!");
        }
    }
}