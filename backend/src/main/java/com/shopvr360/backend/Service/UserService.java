package com.shopvr360.backend.Service;

import com.shopvr360.backend.dto.ForgotRequest;
import com.shopvr360.backend.dto.LoginRequest;
import com.shopvr360.backend.dto.RegisterRequest;
import com.shopvr360.backend.dto.ResetPasswordRequest;
import com.shopvr360.backend.entity.RoleEntity;
import com.shopvr360.backend.entity.TableDonhang;
import com.shopvr360.backend.entity.TableUser;
import com.shopvr360.backend.repository.RoleRepository;
import com.shopvr360.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<TableUser> getAllUser(){
        return userRepository.findAll();
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    public boolean isPhoneAvailable(String phone) {
        return !userRepository.existsByDienthoai(phone);
    }


    // tạo mã 6 chữ số
    private String generateCode() {
        Random r = new Random();
        int code = 100000 + r.nextInt(900000);
        return String.valueOf(code);
    }

    public TableUser register(RegisterRequest req) {
        if(userRepository.findByUsername(req.username).isPresent()) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if(userRepository.findByEmail(req.email).isPresent()) {
            throw new RuntimeException("Email đã được dùng");
        }

        TableUser u = new TableUser();
        u.setUsername(req.username);
        u.setPassword(passwordEncoder.encode(req.password));
        u.setEmail(req.email);
        u.setTen(req.ten);
        u.setDienthoai(req.dienthoai);
        u.setCity(req.city);
        u.setSex(req.sex);
        u.setCom("user");
        u.setCongty("");
        u.setCountry("");
        u.setDiachi("");
        u.setHienthi(true);
        u.setLastlogin("");
        u.setLoginSession("");
        u.setNickYahoo("");
        u.setNickSkype("");
        u.setQuyen("user");
        u.setRole(Short.valueOf("4"));
        u.setUserToken(UUID.randomUUID().toString());
        RoleEntity defaultRole = roleRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Role mặc định không tồn tại"));
        u.setRoleEntity(defaultRole);




        // tạo mã xác thực và lưu tạm vào secretKey + expiry
        String code = generateCode();
        u.setSecretKey(code);
        u.setCodeExpiry(LocalDateTime.now().plusMinutes(15)); // hết hạn 15 phút

        TableUser saved = userRepository.save(u);

        // gửi mail giả lập
        sendMailSimulated(u.getEmail(), "Mã xác thực đăng ký", "Mã của bạn: " + code);

        return saved;
    }

    // xác thực mã đăng ký
    private final int CODE_EXPIRY_MINUTES = 5;

    // Gửi code mới (resend)
    public void sendVerificationCode(String email) {
        TableUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy email"));

        // Tạo secretKey mới
        String secretKey = UUID.randomUUID().toString().substring(0, 6); // ví dụ 6 ký tự
        user.setSecretKey(secretKey);
        user.setCodeExpiry(LocalDateTime.now().plusMinutes(CODE_EXPIRY_MINUTES));

        userRepository.save(user);

        // Gửi email
        System.out.println("Gửi mã xác thực tới email " + email + ": " + secretKey);
    }

    // Xác thực code
    public void verifyRegistration(String email, String code) {
        TableUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy email"));

        if(user.getSecretKey() == null || !user.getSecretKey().equals(code) ||
                user.getCodeExpiry() == null || user.getCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã không hợp lệ hoặc đã hết hạn");
        }

        // Xác thực thành công → xóa secretKey và codeExpiry
        user.setSecretKey(null);
        user.setCodeExpiry(null);
        userRepository.save(user);
    }
    public String login(LoginRequest req) {
        TableUser u = userRepository.findByUsername(req.username)
                .orElseThrow(() -> new RuntimeException("Tên đăng nhập hoặc mật khẩu sai"));

        // check tài khoản có bị vô hiệu hoá không
        if (u.getHienthi() != null && u.getHienthi() == false) {
            throw new RuntimeException("Tài khoản của bạn đã bị vô hiệu hoá, vui lòng liên hệ admin.");
        }

        // kiểm tra mật khẩu
        if (!passwordEncoder.matches(req.password, u.getPassword())) {
            throw new RuntimeException("Tên đăng nhập hoặc mật khẩu sai");
        }

        // tạo token phiên
        String token = UUID.randomUUID().toString();
        u.setUserToken(token);
        u.setLastlogin(String.valueOf(LocalDateTime.now()));
        userRepository.save(u);

        return token;
    }


    // quên mật khẩu: gửi mã
    public void forgotPassword(ForgotRequest req) {
        TableUser u = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        String code = generateCode();
        u.setSecretKey(code);
        u.setCodeExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(u);

        // gửi mail giả lập
        sendMailSimulated(u.getEmail(), "Mã đặt lại mật khẩu", "Mã đặt lại: " + code);
    }

    public TableUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
    }

    public String getUsernameFromToken(String token) {
        TableUser u = userRepository.findAll()
                .stream()
                .filter(user -> token.equals(user.getUserToken()))
                .findFirst()
                .orElse(null);

        if (u != null) {
            return u.getUsername();
        } else {
            System.out.println("❌ Token không hợp lệ hoặc hết hạn");
            return null;
        }
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> { throw new RuntimeException("Không tìm thấy user id = " + id); }
                );
    }


    public void changeStatus(Long id) {
        TableUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user có id " + id));

        user.setHienthi(!user.getHienthi()); // đảo ngược đúng nghĩa

        userRepository.save(user);
    }

    public void resetPassword(Long id) {
        TableUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user có id " + id));

        user.setPassword(passwordEncoder.encode("12345678"));

        userRepository.save(user);
    }



    public void deleteMultiple(List<Long> ids) {
        List<TableUser> orders = userRepository.findAllById(ids);

        if (orders.isEmpty()) {
            throw new RuntimeException("Không tìm thấy đơn hàng nào trong danh sách.");
        }

        int now = (int) (System.currentTimeMillis() / 1000);



        userRepository.saveAll(orders);
    }

    // reset password sử dụng mã
    public void resetPassword(ResetPasswordRequest req) {
        TableUser u = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        if(u.getSecretKey() == null || !u.getSecretKey().equals(req.code)
                || u.getCodeExpiry() == null || u.getCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã không hợp lệ hoặc đã hết hạn");
        }

        u.setPassword(passwordEncoder.encode(req.newPassword));
        u.setSecretKey(null);
        u.setCodeExpiry(null);
        userRepository.save(u);
    }

    public void updateUser(Long id, TableUser updatedUser) {
        TableUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user có id = " + id));

        user.setTen(updatedUser.getTen());
        user.setEmail(updatedUser.getEmail());
        user.setDienthoai(updatedUser.getDienthoai());
        user.setCity(updatedUser.getCity());
        user.setSex(updatedUser.getSex());
        user.setQuyen(updatedUser.getQuyen());
        user.setRoleEntity(updatedUser.getRoleEntity());

        userRepository.save(user);
    }




    private void sendMailSimulated(String to, String subject, String body) {
        System.out.println("---- GIẢ LẬP GỬI MAIL ----");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("--------------------------");
    }

}
