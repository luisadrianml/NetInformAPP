package com.commovil.luisadrianml.networkinformationapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_READ_PHONE_STATE = 1;
    TextView sim_sn;
    TextView sim_op;
    TextView sim_imsi;
    TextView net_operator;
    TextView net_type;
    TextView msisdn;
    TextView cid;
    TextView lac;
    TextView gsmCellLocation;
    TelephonyManager teleManager;
    CdmaCellLocation cellLocation;
    String cdma_cell_location;
    int gsm_signal;
    GsmCellLocation cellGsmLocation;
    boolean permission_granted = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sim_sn = (TextView) findViewById(R.id.sim_sn);
        sim_op = (TextView) findViewById(R.id.sim_op);
        sim_imsi = (TextView) findViewById(R.id.sim_imsi);
        net_operator = (TextView) findViewById(R.id.net_ope);
        net_type = (TextView) findViewById(R.id.net_type);
        msisdn = (TextView) findViewById(R.id.msisdn);
        cid = (TextView) findViewById(R.id.cid);
        lac = (TextView) findViewById(R.id.lac);
        gsmCellLocation = (TextView) findViewById(R.id.gsmcelllocation);


        teleManager =  (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        askForPermission();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permission_granted) {
                    sim_sn.setText(teleManager.getSimSerialNumber());
                    sim_op.setText(teleManager.getSimOperatorName());
                    sim_imsi.setText(teleManager.getSubscriberId());
                    net_operator.setText(teleManager.getNetworkOperatorName());
                    net_type.setText(Helper.getPhoneType(teleManager.getPhoneType()) + " - " + Helper.getNetworkType(teleManager.getNetworkType()));
                    msisdn.setText(teleManager.getLine1Number());
                    cid.setText(Integer.toString(Helper.getGSMCid(teleManager)));
                    lac.setText(Integer.toString(Helper.getGSMLac(teleManager)));
                    gsmCellLocation.setText(Integer.toString(gsm_signal));

                    Snackbar.make(view, R.string.data_updated, Snackbar.LENGTH_LONG)
                            .setAction(R.string.clear, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cleanTextViews();
                                    Snackbar.make(v, R.string.data_emptied, Snackbar.LENGTH_LONG).show();
                                }

                                private void cleanTextViews() {
                                    sim_sn.setText(R.string.empty);
                                    sim_op.setText(R.string.empty);
                                    sim_imsi.setText(R.string.empty);
                                    net_operator.setText(R.string.empty);
                                    net_type.setText(R.string.empty);
                                    msisdn.setText(R.string.empty);
                                }
                            }).show();

                } else {
                    Snackbar.make(view, R.string.data_permission, Snackbar.LENGTH_LONG)
                            .setAction(R.string.okay, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cleanTextViews();
                                    Snackbar.make(v, R.string.data_emptied, Snackbar.LENGTH_LONG).show();
                                }

                                private void cleanTextViews() {
                                    sim_sn.setText(R.string.empty);
                                    sim_op.setText(R.string.empty);
                                    sim_imsi.setText(R.string.empty);
                                    net_operator.setText(R.string.empty);
                                    net_type.setText(R.string.empty);
                                    msisdn.setText(R.string.empty);
                                }
                            }).show();

                }
            }
        });

        PhoneStateListener listener = new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                String ssignal = signalStrength.toString();
                String[] parts = ssignal.split(" ");
                if (teleManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE){

                    int ltesignal = Integer.parseInt(parts[9]);

                    // check to see if it get's the right signal in dB, a signal below -2
                    if(ltesignal < -2) {
                        gsm_signal = ltesignal;
                    }
                }
                // Else 3G
                else {
                    if (signalStrength.getGsmSignalStrength() != 99) {

                        gsm_signal = -113 + 2 * signalStrength.getGsmSignalStrength();
                    }
                }

            }
        };

        teleManager.listen(listener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

    }


    private void askForPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_READ_PHONE_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    permission_granted = true;


                } else {

                    permission_granted = false;

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(R.string.action_about);
            alertDialog.setMessage("Deployment by Luis Martinez");
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
