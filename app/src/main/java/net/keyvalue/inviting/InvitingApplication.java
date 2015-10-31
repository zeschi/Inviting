package net.keyvalue.inviting;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by sum on 15-8-14.
 */
public class InvitingApplication extends Application {

    private static InvitingApplication instance;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        refWatcher = LeakCanary.install(this);
    }

    public static InvitingApplication getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher() {
        return instance.refWatcher;
    }
}
