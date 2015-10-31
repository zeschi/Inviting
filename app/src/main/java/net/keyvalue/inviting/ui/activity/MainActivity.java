package net.keyvalue.inviting.ui.activity;

import android.os.Bundle;

import net.keyvalue.inviting.R;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        setupHeader();
    }

    public void setupHeader() {

    }

}
