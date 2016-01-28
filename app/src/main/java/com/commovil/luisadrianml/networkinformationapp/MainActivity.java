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
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_READ_PHONE_STATE = 1;
    TextView sim_sn;
    TextView sim_op;
    TextView sim_imsi;
    TextView net_operator;
    TextView net_type;
    TextView msisdn;
    TelephonyManager teleManager;
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
                    net_type.setText(getPhoneType(teleManager.getPhoneType()) + " - " + getNetworkType(teleManager.getNetworkType()));
                    msisdn.setText(teleManager.getLine1Number());

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
    }



    private String getPhoneType(int phoneType) {
        String valuePhoneType  = null;
        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                valuePhoneType ="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                valuePhoneType ="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                valuePhoneType ="NONE";
                break;
        }
        return valuePhoneType;
    }

    private String getNetworkType(int networkType) {
        String valueNetworkType = null;
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT: {
                valueNetworkType = "1xRTT";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_CDMA: {
                valueNetworkType = "CDMA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EDGE: {
                valueNetworkType = "EDGE";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EHRPD: {
                valueNetworkType = "EHRPD";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EVDO_0: {
                valueNetworkType = "EVDO_0";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EVDO_A: {
                valueNetworkType = "EVDO_A";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_EVDO_B: {
                valueNetworkType = "EDVO_B";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_GPRS: {
                valueNetworkType = "GPRS";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_HSDPA: {
                valueNetworkType = "HSDPA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_HSPA: {
                valueNetworkType = "HSPA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_HSPAP: {
                valueNetworkType = "HSPAP";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_HSUPA: {
                valueNetworkType = "HSUPA";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_IDEN: {
                valueNetworkType = "IDEN";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_LTE: {
                valueNetworkType = "LTE";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_UMTS: {
                valueNetworkType = "UMTS";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_UNKNOWN: {
                valueNetworkType = "Unknown";
                break;
            }
        }

        return valueNetworkType;
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
