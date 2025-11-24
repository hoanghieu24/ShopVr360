package com.shopvr360.backend.Controller;

import com.shopvr360.backend.Service.UserService;
import com.shopvr360.backend.dto.*;
import com.shopvr360.backend.entity.TableUser;
import com.shopvr360.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<TableUser> getAllUsers() {
        return userService.getAllUser();
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegisterRequest req) {
        userService.register(req);
        return new ApiResponse(true,"Đăng ký thành công. Kiểm tra email để nhận mã (giả lập).");
    }

    @PostMapping("/verify")
    public ApiResponse verify(@RequestBody VerifyRequest request) {
        userService.verifyRegistration(request.getEmail(), request.getCode());
        return new ApiResponse(true, "Xác thực thành công. Bạn có thể đăng nhập.");
    }

    @PostMapping("/resend-code")
    public ApiResponse resendCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        userService.sendVerificationCode(email);
        return new ApiResponse(true, "Mã xác thực mới đã được gửi đến email.");
    }

    @GetMapping("/me")
    public TableUser getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Authorization header thiếu hoặc sai định dạng");
            }

            String token = authHeader.replace("Bearer ", "");

            String username = userService.getUsernameFromToken(token);
            if (username == null) {
                throw new RuntimeException("Token không hợp lệ hoặc hết hạn");
            }

            // Lấy thông tin user từ database
            TableUser user = userService.getUserByUsername(username);
            if (user == null) {
                throw new RuntimeException("Không tìm thấy user");
            }

            return user;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy thông tin user: " + e.getMessage());
        }
    }


    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest req) {
        String token = userService.login(req);
        return new ApiResponse(true, "Đăng nhập thành công", token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        TableUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user có id = " + id));
        return ResponseEntity.ok(user);
    }



    @PostMapping("/forgot")
    public ApiResponse forgot(@RequestBody ForgotRequest req) {
        userService.forgotPassword(req);
        return new ApiResponse(true, "Gửi mã đặt lại mật khẩu (giả lập). Kiểm tra console hoặc log.");
    }

    @PostMapping("/reset")
    public ApiResponse reset(@RequestBody ResetPasswordRequest req) {
        userService.resetPassword(req);
        return new ApiResponse(true, "Đặt lại mật khẩu thành công.");
    }

    @PostMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        boolean available = userService.isUsernameAvailable(username);

        System.out.println("ng dung : " + username);
        Map<String, Object> res = new HashMap<>();
        res.put("available", available);
        res.put("message", available ? "Username is available" : "Username đã tồn tại , vui lòng thử Username khác !!!");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        boolean available = userService.isEmailAvailable(email);
        Map<String, Object> res = new HashMap<>();
        res.put("available", available);
        res.put("message", available ? "Email is available" : "Email đã tồn tại , vui lòng thử email khác !!! ");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/check-phone")
    public ResponseEntity<?> checkPhone(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        boolean available = userService.isPhoneAvailable(phone);
        Map<String, Object> res = new HashMap<>();
        res.put("available", available);
        res.put("message", available ? "Phone is available" : "Số điện thoại đã tồn tại , vui lòng thử số điện thoại khác !!!");

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNew(@PathVariable Long id) throws IOException {
        userService.deleteUser(id);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có ID = " + id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMultiple(@RequestBody List<Long> ids) throws IOException {
        userService.deleteMultiple(ids);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có các ID = " + ids);
    }

    @PutMapping("{id}/status")
    public ResponseEntity changeUser(@PathVariable Long id) throws IOException {
        userService.changeStatus(id);
        return ResponseEntity.ok("Đổi trạng thái người dùng thành công !!!");
    }

    @PutMapping("/{id}/resetPassword")
    public ResponseEntity resetPass(@PathVariable Long id) throws IOException {
        userService.resetPassword(id);
        return ResponseEntity.ok("reset mật khẩu thành công !!!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody TableUser updatedUser) {

        userService.updateUser(id, updatedUser);
        return ResponseEntity.ok("Cập nhật thông tin người dùng thành công!");
    }

}
