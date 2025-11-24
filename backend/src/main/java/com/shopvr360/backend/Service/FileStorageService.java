package com.shopvr360.backend.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    // Thư mục gốc để lưu file (tự động tạo nếu chưa có)
    private static final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File trống!");
        }

        // Tạo thư mục nếu chưa tồn tại
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Tạo tên file an toàn
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        // Ghi file vào ổ đĩa
        Files.write(filePath, file.getBytes());

        // Trả về tên file (để lưu vào DB)
        return fileName;
    }

    // ✅ Hàm lấy đường dẫn tuyệt đối (nếu cần hiển thị file)
    public Path getFilePath(String fileName) {
        return Paths.get(UPLOAD_DIR).resolve(fileName);
    }
}
