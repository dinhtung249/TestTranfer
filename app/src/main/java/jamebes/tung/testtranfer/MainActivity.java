package jamebes.tung.testtranfer;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        methodRequiresTwoPermission();

        AndroidServer server = new AndroidServer(8080);
        try {
            server.start();
            System.out.println("Server started");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        File[] file = this.getExternalMediaDirs();
//        String path = file[1] + "/2023-03-10-1220.jpg";
//        Log.d("TAG", "path: "  + path);
//        File wwwroot = new File(path);
//        int port = 8080;
//        try {
//            new SimpleServer(port, wwwroot);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(111)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "no perms",
                    111, perms);
        }
    }
}