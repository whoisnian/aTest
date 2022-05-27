package com.whoisnian.atest;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.widget.Toast;

import hello.Hello;

public class HCEService extends HostApduService {
    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        Toast.makeText(this, "processCommandApdu", Toast.LENGTH_LONG).show();
        return Hello.processCommandApdu(apdu);
    }

    @Override
    public void onDeactivated(int reason) {}
}
