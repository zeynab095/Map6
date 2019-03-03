package com.example.user.map6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SetAlarmActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    TextView dest;
    Spinner spinner;
    float desLat;
    float desLong;
    private static final String TAG = SetAlarmActivity.class.getSimpleName();
    static Location loc;
    static int dist;
    static boolean remove;

    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private SetAlarmActivity.MyReceiver myReceiver;

    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;

    // UI elements.
    private Button mRequestLocationUpdatesButton;
    private Button mRemoveLocationUpdatesButton;

    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myReceiver = new SetAlarmActivity.MyReceiver();
        setContentView(R.layout.activity_set_alarm);


        loc=new Location("a");
        ///////////////////////////
        Bundle extras = getIntent().getExtras();
        String value;
        if (extras != null) {
            value = String.valueOf(extras.getFloat("desLat"));
            desLat=extras.getFloat("desLat");
            desLong=extras.getFloat("desLong");
        }

        loc.setLatitude(desLat);
        loc.setLongitude(desLong);

        ///////////////////////////

        mRequestLocationUpdatesButton = (Button) findViewById(R.id.request_location_updates_button); //ok
        mRemoveLocationUpdatesButton = (Button) findViewById(R.id.remove_location_updates_button);
        mRequestLocationUpdatesButton.setEnabled(true);
        mRemoveLocationUpdatesButton.setEnabled(false);


        dest=(TextView) findViewById(R.id.dest); //destination name to save to shared preferneces
        spinner = (Spinner) findViewById(R.id.list);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meters, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        Bundle b1= getIntent().getExtras();
        if(b1!=null&&b1.getString("name")!=null) {
            String name = b1.getString("name");

            @SuppressLint("WrongConstant") SharedPreferences sh1 = getSharedPreferences("MyOwnShared", MODE_APPEND);

            String s1 = sh1.getString(name, "");
            Log.d("String", s1);
            String[] arr;
            arr = s1.split(",");
            Log.d("check0", arr[0]);
            Log.d("check1", arr[1]);
            Log.d("check2", arr[2]);
            Log.d("check3", arr[3]);
            dest.setText(arr[0]);
            spinner.setSelection(Integer.parseInt(arr[1]));
            desLat=Float.parseFloat(arr[2]);
            desLong=Float.parseFloat(arr[3]);

        }

        // Check that the user hasn't revoked permissions by going to Settings.
        if (Utils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }
    }
    public void saveData(View view) {
        String s[]={dest.getText().toString(),String.valueOf(spinner.getSelectedItemPosition()),Float.toString(desLat),Float.toString(desLong)};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            sb.append(s[i]).append(",");
        }
        SharedPreferences sh = getSharedPreferences("MyOwnShared",MODE_PRIVATE);
        SharedPreferences.Editor myEdit =sh.edit();
        myEdit.putString(dest.getText().toString(),sb.toString());
        myEdit.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);



        mRequestLocationUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove=false;
                mRequestLocationUpdatesButton.setEnabled(false);
                mRemoveLocationUpdatesButton.setEnabled(true);
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                   /* Intent intent = new Intent(LocationUpdatesService.class.getName());
                    Bundle bundle = new Bundle();
                    bundle.putFloat("desLat", desLat);
                    bundle.putFloat("desLong", desLong);

                    intent.putExtras(bundle);
                    sendBroadcast(intent);*/

                    dist=Integer.parseInt((String) spinner.getSelectedItem());
                    mService.requestLocationUpdates();
                }
            }
        });

        mRemoveLocationUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRequestLocationUpdatesButton.setEnabled(true);
                mRemoveLocationUpdatesButton.setEnabled(false);
                remove=true;
                mService.removeLocationUpdates();
            }
        });

        // Restore the state of the buttons when the activity (re)launches.


        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
       // setButtonsState(Utils.requestingLocationUpdates(this));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    /**
     * Returns the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.

    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                mService.requestLocationUpdates();
            } else {
                // Permission denied.

            }
        }
    }

    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            if (location != null) {
              //  Toast.makeText(SetAlarmActivity.this, Utils.getLocationText(location)Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Update the buttons state depending on whether location updates are being requested.
        if (s.equals(Utils.KEY_REQUESTING_LOCATION_UPDATES)) {
            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES,
                    false));
        }
    }

    private void setButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            mRequestLocationUpdatesButton.setEnabled(false);
            mRemoveLocationUpdatesButton.setEnabled(true);
        } else {
            mRequestLocationUpdatesButton.setEnabled(true);
            mRemoveLocationUpdatesButton.setEnabled(false);
        }
    }
}
