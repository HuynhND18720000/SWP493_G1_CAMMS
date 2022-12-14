package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.payload.request.ResetPasswordRequest;
import com.example.swp493_g1_camms.entities.ResetPassHistory;
import com.example.swp493_g1_camms.entities.User;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.IResetPassHistoryRepository;
import com.example.swp493_g1_camms.repository.IUserRepository;
import com.example.swp493_g1_camms.security.services.AuthenticationFacade;
import com.example.swp493_g1_camms.services.interfaceService.IUserService;
import com.example.swp493_g1_camms.utils.Constant;
import com.example.swp493_g1_camms.utils.EmailSender;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository IUserRepository;
    @Autowired
    EmailSender emailSender;

    @Autowired
    IResetPassHistoryRepository resetPassHistoryRepository;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    PasswordEncoder passwordEncoder;

    //random ma otp
    public static char[] generateOTP(int length) {
        String numbers = "1234567890";
        Random random = new Random();
        char[] otp = new char[length];

        for(int i = 0; i< length ; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

    //lấy email từ client rồi gưit otp về gmail
    @Override
    public ResponseEntity<?> getEmailInForgotPassword(String email) {
        ResponseVo responseVo = new ResponseVo();
        MessageResponse messageResponse = new MessageResponse();
        try{
            System.out.println("email lay dc ve la:"+email);
            User user = IUserRepository.getUserByEmail(email);

            if(user == null){
                responseVo.setMessage("Không tìm thấy nguoi dung!");
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }else{
                //set create date when create otp
                Date in = new Date();
                LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                //create otp when random otp
                String ma_OTP = String.valueOf(generateOTP(6));
                boolean result_sendOtp = emailSender.sendOtpIntoEmail(email, ma_OTP, user.getFullName());
                if(result_sendOtp){
                    responseVo.setMessage("Gui ma otp ve email thanh cong");
                    //add vao bang reset_pass_history
                    ResetPassHistory resetPassHistory = new ResetPassHistory();
                    resetPassHistory.setUser(user);
                    resetPassHistory.setOld_password(null);
                    resetPassHistory.setOtp_code(ma_OTP);
                    resetPassHistory.setCreate_date(ldt);
                    resetPassHistory.setStatus(false);
                    //set otp valid during 5 minutes
                    LocalDateTime resetTime_OTP
                            = LocalDateTime.now().plusMinutes(5);
                    resetPassHistory.setExpiration_date(resetTime_OTP);

                    Calendar c = Calendar.getInstance();
                    c.setTime(in);//set current time
                    //add 1 month to valid password when want to use old password
                    c.add(Calendar.MONTH, 1);
                    // convert calendar to date
                    Date currentDatePlusOne = c.getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String date_format = dateFormat.format(currentDatePlusOne);
                    Date date1=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date_format);
                    //set future date
                    LocalDateTime localDateTime_futrue =
                            LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
                    resetPassHistory.setTime_active_pass(localDateTime_futrue);
                    //create current token
                    User u = new User();
                    u.setUsername(user.getUsername());
                    u.setPassword(user.getPassword());
                    System.out.println("username: "+u.getUsername());

                    String current_token = Jwts.builder().setSubject(u.getUsername()).setIssuedAt(new Date())
                            .setExpiration(new Date((new Date()).getTime() + 24*60*60*1000)).
                            signWith(SignatureAlgorithm.HS512, "demoLogin")
                            .compact();

                    resetPassHistory.setCurrent_token(current_token);
                    //save into table reset_pass_history
                    resetPassHistoryRepository.save(resetPassHistory);
                    responseVo.setData(current_token);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }else{
                    responseVo.setMessage("Gui ma otp ve email that bai");
                    return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            System.out.println("loi khong gui dc");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }
    @Override
    public ResponseEntity<?> checkOTPFromClient(String otp) {
        MessageResponse messageResponse = new MessageResponse();
        Map<String, Object> output = new HashMap<>();
        try{
            //loi dung otp roi nhung van update dc mk
            String otp_exist = resetPassHistoryRepository.getOTPExist(otp);
            System.out.println("otp lay dc ve la:"+otp);

            if(otp.equalsIgnoreCase(otp_exist)){
                //neu dung otp cho nguoi khac ma khong phai cua minh
                ResetPassHistory resetPassHistory = resetPassHistoryRepository.getUserByOtpCode(otp);
                //get current user to check whether user have correct this otp or other user have otp
                //co van de
                String usernameOfCurrentUser = authenticationFacade.currentUserNameSimple();
                System.out.println("user hien tai la "+usernameOfCurrentUser);
                Optional<User> user = IUserRepository.findByUsername(usernameOfCurrentUser);
                //thieu lay ra token hien tai
                long user_id_of_otp = resetPassHistory.getUser().getId();
                long current_user_id = user.get().getId();

                if(current_user_id == user_id_of_otp){
                    if (LocalDateTime.now().isAfter(resetPassHistory.getExpiration_date())) {
                        output.put("status",Constant.FAIL);
                        output.put("message","Thời gian nhập OTP đã hết");
                    }else {
                        output.put("status", Constant.SUCCESS);
                        output.put("otp", otp_exist);
                        output.put("message", "OTP chính xác");
                    }
                }else{
                    output.put("status", Constant.FAIL);
                    output.put("message", "OTP sai");
                }
            }else{
                output.put("status", Constant.FAIL);
                output.put("message","OTP nhập sai");
            }
            return new ResponseEntity<>(output, HttpStatus.OK);
        }catch(Exception e){
            System.out.println("loi khong gui dc");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }
    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        ResponseVo responseVo = new ResponseVo();
        MessageResponse messageResponse = new MessageResponse();
        Map<String, Object> output = new HashMap<>();
        try{
            // Password entered by user
            String new_password = resetPasswordRequest.getConfirm_password();
            System.out.println("mk lay tu phia user: "+new_password);
            //get current user active
            String usernameOfCurrentUser = authenticationFacade.currentUserNameSimple();
            System.out.println("user hien tai la "+usernameOfCurrentUser);
            Optional<User> user = IUserRepository.findByUsername(usernameOfCurrentUser);
            //lay ra user theo user id cua current user hien tai
            Optional<User> current_user_using_pass = IUserRepository.getUserById(user.get().getId());
            //so sanh mk lay tu client vs mk cua user dang dung
            boolean checkPasswordDuplicated = passwordEncoder.matches(new_password,
                    current_user_using_pass.get().getPassword());
            if(checkPasswordDuplicated){
                output.put("status",Constant.FAIL);
                output.put("message","Bạn đã sử dụng mật khẩu này gần đây. Hay chọn một mật khẩu khác.");
                return new ResponseEntity<>(output, HttpStatus.OK);
            }else{
                //lấy ra list lich su khi ma user thay doi mk
                List<ResetPassHistory> resetPassHistoryList =
                        resetPassHistoryRepository.getUserByUserId(user.get().getId());
                boolean checkPasswordIsOldPassword = false;
                boolean usedOTP = false;
                //neu nhu nguoi dung thuc hien day du thao tac doi mk
                //list danh sach se ko rong
                //kiem tra list danh sach co rong hay khoong

                if(resetPassHistoryList.size() != 0){
                    for (ResetPassHistory rph: resetPassHistoryList
                    ) {
                        System.out.println("mk user nhap la:" + new_password);
                        System.out.println("mk lay tu db la:" + rph.getOld_password());
                        //kiem tra otp chi dc dung 1 lan sau khi ma cap nhat xong mat khau
                        if (rph.isStatus() == false) {
                            usedOTP = false;
                            if (passwordEncoder.matches(new_password, rph.getOld_password())) {
                                System.out.println("da vao day 1");
                                checkPasswordIsOldPassword = true;
                                //lay ra date hien tai
                                Date in = new Date();
                                LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                                //lay ra valid date for using old password
                                LocalDateTime date_for_old_pass = rph.getTime_active_pass();
                                System.out.println("ngay bay gio:" + ldt);
                                System.out.println("ngay han dc sua mk la:" + date_for_old_pass);

                                if (ldt.isBefore(date_for_old_pass)) {
                                    output.put("status", Constant.FAIL);
                                    output.put("message", "Bạn đã sử dụng mật khẩu này gần đây. " +
                                            "Vui lòng chọn một mật khẩu khác.");
                                    return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
                                } else {
                                    //ma hoa password lay tu phia user
                                    String bcrypt_password = passwordEncoder.encode(new_password);
                                    //lay ra password ma nguoi dung dang su dung truoc khi maf thuc hien forgot password
                                    String old_password = user.get().getPassword();
                                    //cap nhat lai old_password vao bang resetpasswordhistory
                                    ResetPassHistory resetPassHistory = resetPassHistoryRepository.getUserByOtpCode(
                                            resetPasswordRequest.getOtp()
                                    );

                                    resetPassHistory.setOld_password(old_password);
                                    resetPassHistory.setStatus(true);
                                    // chu y co nen them truong status cua otp khi ma dung xong otp
                                    resetPassHistoryRepository.save(resetPassHistory);
                                    //thay doi mk o bang user
                                    user.get().setPassword(bcrypt_password);
                                    IUserRepository.save(user.get());

                                    output.put("status", Constant.SUCCESS);
                                    output.put("message", "Cập nhật mật khẩu thành công.");
                                    return new ResponseEntity<>(output, HttpStatus.OK);

                                }

                            } else {
                                checkPasswordIsOldPassword = false;
                            }
                        }else{
                            usedOTP = false;
                        }
                    }
                    //thieu truong hop
                    if(checkPasswordIsOldPassword == false){
                        System.out.println("da vao day 2");
                        //ma hoa password lay tu phia user
                        String bcrypt_password = passwordEncoder.encode(new_password);
                        //lay ra password ma nguoi dung dang su dung truoc khi maf thuc hien forgot password
                        String old_password = user.get().getPassword();
                        //cap nhat lai old_password vao bang resetpasswordhistory
                        ResetPassHistory resetPassHistory = resetPassHistoryRepository.getUserByOtpCode(
                                resetPasswordRequest.getOtp()
                        );
                        if(resetPassHistory.isStatus() == false){
                            resetPassHistory.setOld_password(old_password);
                            resetPassHistory.setStatus(true);

                            resetPassHistoryRepository.save(resetPassHistory);
                            //thay doi mk o bang user
                            user.get().setPassword(bcrypt_password);
                            IUserRepository.save(user.get());

                            output.put("status",Constant.SUCCESS);
                            output.put("message","Cập nhật mật khẩu thành công.");
                            return new ResponseEntity<>(output, HttpStatus.OK);
                        }else{
                            output.put("status",Constant.FAIL);
                            output.put("message","Cap nhat that bai. Ma OTP da dc su dung");
                            return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                    if(usedOTP==true){
                        output.put("status",Constant.FAIL);
                        output.put("message","Cap nhat that bai. Ma OTP da dc su dung");
                        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }else{
                    //ma hoa password lay tu phia user
                    String bcrypt_password = passwordEncoder.encode(new_password);
                    //lay ra password ma nguoi dung dang su dung truoc khi maf thuc hien forgot password
                    String old_password = user.get().getPassword();
                    //cap nhat lai old_password vao bang resetpasswordhistory
                    ResetPassHistory resetPassHistory = resetPassHistoryRepository.getUserByOtpCode(
                            resetPasswordRequest.getOtp()
                    );
                    if(resetPassHistory.isStatus() == false){
                        resetPassHistory.setOld_password(old_password);
                        resetPassHistory.setStatus(true);

                        resetPassHistoryRepository.save(resetPassHistory);
                        //thay doi mk o bang user
                        user.get().setPassword(bcrypt_password);
                        IUserRepository.save(user.get());

                        output.put("status",Constant.SUCCESS);
                        output.put("message","Cập nhật mật khẩu thành công.");
                        return new ResponseEntity<>(output, HttpStatus.OK);
                    }else{
                        output.put("status",Constant.FAIL);
                        output.put("message","Cap nhat that bai. Ma OTP da dc su dung");
                        return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
                    }


                }

            }

        }catch(Exception e){
            System.out.println("loi khong tao dc");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
        return null;
    }


}
