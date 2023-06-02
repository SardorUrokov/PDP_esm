package com.example.pdp_esm.service.Implements;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath = "C:\\Users\\user\\Desktop\\PDP_Certificates"; // Replace with the actual file path on your PC
        File file = new File(filePath);

        // Set response headers
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        // Read the file and write it to the response output stream
        try (FileInputStream fis = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}