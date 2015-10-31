package net.keyvalue.inviting.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import net.keyvalue.inviting.R;
import net.keyvalue.inviting.widget.CircleRevealView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sum on 15-8-15.
 */
public class TestRevealActivity extends BaseActivity implements CircleRevealView.OnRevealListener {

    private static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    @Bind(R.id.CircleReveal)
    CircleRevealView vCircleReveal;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ivTest)
    ImageView ivTest;

    public static void startFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, TestRevealActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_reveal);
        ButterKnife.bind(this);

        ivTest.setVisibility(View.INVISIBLE);
        setupRevealBackground(savedInstanceState);
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vCircleReveal.setFillPaintColor(0xFF009688);
        vCircleReveal.setOnRevealListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vCircleReveal.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vCircleReveal.getViewTreeObserver().removeOnPreDrawListener(this);
                    vCircleReveal.startAnimationFromLocation(startingLocation);
                    return true;
                }
            });
        }
    }

    @Override
    public void onRevealEnd() {
        vCircleReveal.setVisibility(View.GONE);
        ivTest.setVisibility(View.VISIBLE);
    }
}
