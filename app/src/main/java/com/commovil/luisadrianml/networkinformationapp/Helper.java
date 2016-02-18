package com.commovil.luisadrianml.networkinformationapp;

import android.content.Context;
import android.os.RemoteException;
import android.telephony.CellLocation;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

/**
 * Created by LuisMartinez on 02/18/16.
 */
public class Helper {

    protected static String getPhoneType(int phoneType) {
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

    protected static String getNetworkType(int networkType) {
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


    protected static int getGSMCid(TelephonyManager telephonyManager) {
        GsmCellLocation cellGsmLocation = (GsmCellLocation) telephonyManager.getCellLocation();
        return cellGsmLocation.getCid();
        //String gsm_cell_location = cellGsmLocation.toString();

        //int gsm_ss = signalStrength.getGsmSignalStrength();
        //int gsm_ber = signalStrength.getGsmBitErrorRate();
    }

    protected static int getGSMLac(TelephonyManager telephonyManager) {
        GsmCellLocation cellGsmLocation = (GsmCellLocation) telephonyManager.getCellLocation();
        return cellGsmLocation.getLac();
    }

    protected static String getGsmCellLocation(TelephonyManager telephonyManager) {
        GsmCellLocation cellGsmLocation = (GsmCellLocation) telephonyManager.getCellLocation();
        return cellGsmLocation.toString();
    }

}

