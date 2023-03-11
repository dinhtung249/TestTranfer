package jamebes.tung.testtranfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD;

public class SimpleServer extends NanoHTTPD {
    private final File wwwroot;

    public SimpleServer(int port, File wwwroot) throws IOException {
        super(port);
        this.wwwroot = wwwroot;
        start();
        System.out.println("Server started on port " + port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        File file = new File(wwwroot, uri);
        if (!file.exists()) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found");
        }
        if (file.isDirectory()) {
            file = new File(file, "index.html");
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            return newFixedLengthResponse(Response.Status.OK, getMimeTypeForFile(uri), inputStream, inputStream.available());
        } catch (IOException e) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "500 Internal Server Error");
        }
    }

//    private String getMimeTypeForFile(String uri) {
//        if (uri.endsWith(".html")) {
//            return "text/html";
//        } else if (uri.endsWith(".js")) {
//            return "application/javascript";
//        } else if (uri.endsWith(".css")) {
//            return "text/css";
//        } else {
//            return "application/octet-stream";
//        }
//    }

}
