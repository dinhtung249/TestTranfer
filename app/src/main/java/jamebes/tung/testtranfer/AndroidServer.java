package jamebes.tung.testtranfer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.FileHandler;

import fi.iki.elonen.NanoHTTPD;

public class AndroidServer extends NanoHTTPD {
    public AndroidServer(int port) {
        super(port);
    }

    public AndroidServer(String hostname, int port) {
        super(hostname, port);
    }




//    public Response serve(IHTTPSession session) {
//        String response = "Định Vị Bách Khoa";
//        return newFixedLengthResponse(response);
////        String msg = "<html><body><h1>Hello server</h1>\n";
////        Map<String, String> parms = session.getParms();
////        if (parms.get("username") == null) {
////            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
////        } else {
////            msg += "<p>Hello, " + parms.get("username") + "!</p>";
////        }
////        return newFixedLengthResponse(msg + "</body></html>\n");
//
//
//
//    }


    public Response serve(String uri, Method method,
                          Map<String, String> header, Map<String, String> parameters,
                          Map<String, String> files) {


        File rootDir = new File("/mnt/sdcard/Download/");//Environment.getExternalStorageDirectory();
        File[] filesList = Environment.getExternalStorageDirectory().listFiles();
        String filepath = "";
        if (uri.trim().isEmpty()) {
            filesList = rootDir.listFiles();
        } else {
            filepath = uri.trim();
        }
        filesList = new File(filepath).listFiles();
        String answer = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>DVBK</title>";
        if (new File(filepath).isDirectory()) {
            for (File detailsOfFiles : filesList) {
                answer += "<a href=\"" + detailsOfFiles.getAbsolutePath()
                        + "\" alt = \"\">"
                        + detailsOfFiles.getAbsolutePath() + "</a><br>";
            }
        } else {
        }
        answer += "</head></html>" + "uri: " + uri + " \nfiles " + files
                + " \nparameters " + parameters + " \nheader ";
        return newFixedLengthResponse(answer);
//        String response = "Định Vị Bách Khoa";
//        return newFixedLengthResponse(response);
    }



}
