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

//    public Response serve(String uri, Method method,
//                          Map<String, String> header, Map<String, String> parameters,
//                          Map<String, String> files) {
//
//
//        File rootDir = new File("/mnt/sdcard/Download/");//Environment.getExternalStorageDirectory();
//        File[] filesList = null;
//        String filepath = "";
//        if (uri.trim().isEmpty()) {
//            filesList = rootDir.listFiles();
//        } else {
//            filepath = uri.trim();
//        }
//        filesList = new File(filepath).listFiles();
//        String answer = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>DVBK</title>";
//        if (new File(filepath).isDirectory()) {
//            for (File detailsOfFiles : filesList) {
//                answer += "<a href=\"" + detailsOfFiles.getAbsolutePath()
//                        + "\" alt = \"\">"
//                        + detailsOfFiles.getAbsolutePath() + "</a><br>";
//            }
//        } else {
//        }
//        answer += "</head></html>" + "uri: " + uri + " \nfiles " + files
//                + " \nparameters " + parameters + " \nheader ";
//        return newFixedLengthResponse(answer);
////        String response = "Định Vị Bách Khoa";
////        return newFixedLengthResponse(response);
//    }
//
    @Override
    public Response serve(IHTTPSession session) {
        Log.d("TAG", "response server");
        // Lấy đường dẫn được yêu cầu
        String uri = session.getUri();
        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }

        Log.d("TAG", "uri :" + uri);
        //thu muc
        String pathDir = Environment.getExternalStorageDirectory().toString()  + File.separator + uri ;
        Log.d("TAG", "pathDir :" + pathDir);
        // Kiểm tra xem có phải là yêu cầu truy cập vào thư mục gốc không
//        if (uri.contains("/")) {

            // Trả về danh sách các tệp trong thư mục gốc
            File rootDir = new File(pathDir  );//Environment.getExternalStorageDirectory(); // Thư mục gốc trên Android
//            return getDirectoryListingResponse(rootDir);
            return getFileListResponse(rootDir);
//        }


            // Nếu yêu cầu khác, trả về phản hồi rỗng
//            return newFixedLengthResponse("Nothing");



    }

    // Phương thức này trả về danh sách các tệp trong một thư mục
    private Response getFileListResponse(File dir) {
        Log.d("TAG", "getFileListResponse");
        // Kiểm tra nếu đường dẫn là thư mục
        if (!dir.isDirectory()) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Directory Not Found");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><h1>Files in: ").append(dir.getAbsolutePath()).append("</h1><ul>");

        // Lặp qua danh sách tệp và thư mục trong thư mục gốc
        for (File file : dir.listFiles()) {
                String name = file.getName();
                String link = "<a href=\"" + name + "\">" + name + "</a>";
                sb.append("<li>").append(link).append("</li>");


        }

        sb.append("</ul></body></html>");
        return newFixedLengthResponse(sb.toString());
    }

    private Response serveFile(File file) {
        Log.d("TAG", "serveFile : " + file );
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // Trả về lỗi nếu không tìm thấy tệp tin
            return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "File not found");
        }
        // Lấy loại MIME của tệp tin để phục vụ cho client
        String mimeType = NanoHTTPD.getMimeTypeForFile(file.getName());
        // Trả về Response chứa dữ liệu của tệp tin và loại MIME tương ứng
        return newFixedLengthResponse(Response.Status.OK, mimeType, inputStream, file.length());
    }

    private Response showFile(File  file){
        // đặt kiểu MIME cho phản hồi
        String mimeType = NanoHTTPD.getMimeTypeForFile(file.getName());

        try {
            // tạo một FileInputStream để đọc nội dung của file
            FileInputStream fis = new FileInputStream(file);

            // trả về một phản hồi với nội dung của file
            return newFixedLengthResponse(Response.Status.OK, mimeType, fis, file.length());
        } catch (IOException e) {
            e.printStackTrace();
            return newFixedLengthResponse("Error while getting file");
        }
    }



}
