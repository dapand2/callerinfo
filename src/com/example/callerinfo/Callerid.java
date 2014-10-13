package com.example.callerinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.InputFilter.LengthFilter;
import android.widget.Toast;

public class Callerid extends BroadcastReceiver {
	Databasehandler database;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
				TelephonyManager.EXTRA_STATE_RINGING)) {
			sendmessage(context, intent);
			silent(context);

		} else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
				TelephonyManager.EXTRA_STATE_IDLE)
				|| (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
						.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
			Toast.makeText(context, "call hangup", Toast.LENGTH_LONG).show();
		}

	}

	private void sendmessage(Context context, Intent intent) {

		// TODO Auto-generated method stub
		String incomingnumber = intent
				.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		String Getdata;
		Getdata = "";
		database = new Databasehandler(context);
		database.open();
		Cursor C = database.returndata();
		if (C.moveToFirst()) {
			do {
				Getdata = C.getString(0);
			} while (C.moveToNext());
		}
		database.close();
		Toast.makeText(context, "data read", Toast.LENGTH_LONG).show();

		Toast.makeText(context, "call from " + incomingnumber,
				Toast.LENGTH_LONG).show();
		SmsManager manager = SmsManager.getDefault();
		manager.sendTextMessage(incomingnumber, null, Getdata, null, null);

	}

	public void silent(Context context) {
		// TODO Auto-generated method stub
		AudioManager audiomanage = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}

}
