package org.numisoft.drawertest.tasks;

import android.os.AsyncTask;

import org.numisoft.drawertest.MainActivity;

import java.lang.ref.WeakReference;

/**
 * Created by mac-200 on 12/5/17.
 */

public class InitializeDatabaseTask extends AsyncTask<String, Integer, Long> {

    private WeakReference<MainActivity> activity;

    public InitializeDatabaseTask(MainActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }

}
