package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.ResetPassHistory;
import com.example.swp493_g1_camms.entities.User;
import com.example.swp493_g1_camms.payload.request.ChangePasswordRequest;
import com.example.swp493_g1_camms.payload.request.UserProfileRequest;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.payload.response.UserProfileResponse;
import com.example.swp493_g1_camms.repository.IResetPassHistoryRepository;
import com.example.swp493_g1_camms.repository.IUserRepository;
import com.example.swp493_g1_camms.security.services.AuthenticationFacade;
import com.example.swp493_g1_camms.services.interfaceService.IUserProfileService;
import com.example.swp493_g1_camms.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserProfileServiceImpl implements IUserProfileService {
    @Autowired
    AuthenticationFacade authenticationFacade;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IResetPassHistoryRepository resetPassHistoryRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    //can sua lai toan bo
    @Override
    public ResponseEntity<?> getUserProfile() {
        MessageResponse messageResponse = new MessageResponse();
        ResponseVo responseVo = new ResponseVo();
        try{
            String usernameOfCurrentUser = authenticationFacade.currentUserNameSimple();
            System.out.println("user hien tai la "+usernameOfCurrentUser);
            Optional<User> user = userRepository.findByUsername(usernameOfCurrentUser);

            if (!user.isPresent()){
                messageResponse.setStatus(500);
                messageResponse.setMessage("user ko ton tai");
                responseVo.setData(messageResponse);
                return new ResponseEntity<>(responseVo, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            UserProfileResponse userProfileResponse = new UserProfileResponse();
            userProfileResponse.setUser_id(user.get().getId());
            userProfileResponse.setUsername(user.get().getUsername());
            userProfileResponse.setDob(user.get().getDob());
            if(user.get().getImage()!= null){
                userProfileResponse.setImage(user.get().getImage());
            }else{
                userProfileResponse.setImage(null);
            }
            userProfileResponse.setEmail(user.get().getEmail());
            userProfileResponse.setFullName(user.get().getFullName());
            userProfileResponse.setPhone(user.get().getPhone());
            responseVo.setData(userProfileResponse);

            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch(Exception e){
            System.out.println("loi khong lay dc");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest) {
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> output = new HashMap<>();
        MessageResponse messageResponse = new MessageResponse();
        try {
            String current_password = changePasswordRequest.getCurrent_pass();
            String new_password = changePasswordRequest.getNew_pass();
            String confirm_password = changePasswordRequest.getConfirm_pass();

            if (!new_password.equals(confirm_password)) {
                output.put("status", Constant.FAIL);
                output.put("message", "Nhap mk ko chinh xac.");
                return new ResponseEntity<>(output, HttpStatus.OK);
            } else {
                Long user_id = changePasswordRequest.getUser_id();
                Optional<User> userByUserId = userRepository.getUserById(user_id);
                String user_pass =  userByUserId.get().getPassword();
                boolean isCurrentPassword = passwordEncoder.matches(current_password,
                       user_pass);
                if(!isCurrentPassword){
                    output.put("status", Constant.FAIL);
                    output.put("message", "Sai mat khau. Vui long kiem tra lai mat khau cua ban");
                    return new ResponseEntity<>(output, HttpStatus.OK);
                }

                boolean checkPasswordDuplicated = passwordEncoder.matches(confirm_password,
                        user_pass);
                if (checkPasswordDuplicated) {
                    output.put("status", Constant.FAIL);
                    output.put("message", "Bạn đã sử dụng mật khẩu này gần đây. Hay chọn một mật khẩu khác.");
                    return new ResponseEntity<>(output, HttpStatus.OK);
                }
                //get current user active
                String usernameOfCurrentUser = authenticationFacade.currentUserNameSimple();
                System.out.println("user hien tai la " + usernameOfCurrentUser);
                Optional<User> user = userRepository.findByUsername(usernameOfCurrentUser);

                //lay ra danh sach lich su nhung lan ma nguoi dung thay doi mk cua minh
                List<ResetPassHistory> resetPassHistoryList =
                        resetPassHistoryRepository.getUserByUserId(user.get().getId());
                if (resetPassHistoryList == null) {
                    //lay ra date hien tai
                    Date in = new Date();
                    LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                    //ma hoa password lay tu phia user
                    String bcrypt_password = passwordEncoder.encode(new_password);
                    //lay ra password ma nguoi dung dang su dung truoc khi maf thuc hien forgot password
                    String old_password = user.get().getPassword();
                    //cap nhat lai old_password vao bang resetpasswordhistory
                    ResetPassHistory resetPassHistory = new ResetPassHistory();
                    resetPassHistory.setOld_password(old_password);
                    resetPassHistory.setStatus(false);
                    resetPassHistory.setUser(user.get());

                    //set lai time cho mk moi
                    Calendar c = Calendar.getInstance();
                    c.setTime(in);//set current time
                    //add 1 month to valid password when want to use old password
                    c.add(Calendar.MONTH, 1);
                    // convert calendar to date
                    Date currentDatePlusOne = c.getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String date_format = dateFormat.format(currentDatePlusOne);
                    Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date_format);
                    //set future date
                    LocalDateTime localDateTime_futrue =
                            LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
                    resetPassHistory.setTime_active_pass(localDateTime_futrue);

                    // chu y co nen them truong status cua otp khi ma dung xong otp
                    resetPassHistoryRepository.save(resetPassHistory);
                    //thay doi mk o bang user
                    user.get().setPassword(bcrypt_password);
                    userRepository.save(user.get());

                    output.put("status", Constant.SUCCESS);
                    output.put("message", "Cập nhật mật khẩu thành công.");
                    return new ResponseEntity<>(output, HttpStatus.OK);
                }else {
                    boolean mark = false;
                    for (ResetPassHistory rph : resetPassHistoryList
                    ) {
                        //lay ra date hien tai
                        Date in = new Date();
                        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                        //lay ra valid date for using old password
                        LocalDateTime date_for_old_pass = rph.getTime_active_pass();
                        boolean checkPasswordExist = passwordEncoder.matches(confirm_password,
                                rph.getOld_password());
                        if (checkPasswordExist) {
                            if(ldt.isBefore(date_for_old_pass)) {
                                output.put("status", Constant.FAIL);
                                output.put("message", "Bạn đã sử dụng mật khẩu này gần đây. " +
                                        "Vui lòng chọn một mật khẩu khác.");
                                return new ResponseEntity<>(output, HttpStatus.OK);
                            } else {
                                mark = true;
                                //ma hoa password lay tu phia user
                                String bcrypt_password = passwordEncoder.encode(new_password);
                                //lay ra password ma nguoi dung dang su dung truoc khi maf thuc hien forgot password
                                String old_password = user.get().getPassword();
                                //cap nhat lai old_password vao bang resetpasswordhistory
                                // cap nhat vao object chua mk cu do
                                ResetPassHistory resetPassHistory = new ResetPassHistory();
                                resetPassHistory.setUser(user.get());
                                resetPassHistory.setOld_password(old_password);
                                //set lai time cho mk moi
                                Calendar c = Calendar.getInstance();
                                c.setTime(in);//set current time
                                //add 1 month to valid password when want to use old password
                                c.add(Calendar.MONTH, 1);
                                // convert calendar to date
                                Date currentDatePlusOne = c.getTime();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                String date_format = dateFormat.format(currentDatePlusOne);
                                Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date_format);
                                //set future date
                                LocalDateTime localDateTime_futrue =
                                        LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
                                resetPassHistory.setTime_active_pass(localDateTime_futrue);
                                resetPassHistory.setStatus(false);
                                resetPassHistoryRepository.save(resetPassHistory);

                                user.get().setPassword(bcrypt_password);
                                userRepository.save(user.get());
                                output.put("status", Constant.SUCCESS);
                                output.put("message", "Cập nhật mật khẩu thành công.");
                                return new ResponseEntity<>(output, HttpStatus.OK);
                            }
                        }else{
                            mark = false;
                        }

                    }
                    if(mark==false){
                        //lay ra date hien tai
                        Date in = new Date();
                        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
                        //ma hoa password lay tu phia user
                        String bcrypt_password = passwordEncoder.encode(new_password);
                        //lay ra password ma nguoi dung dang su dung truoc khi maf thuc hien forgot password
                        String old_password = user.get().getPassword();
                        //cap nhat lai old_password vao bang resetpasswordhistory
                        // cap nhat vao object chua mk cu do
                        ResetPassHistory resetPassHistory = new ResetPassHistory();
                        resetPassHistory.setUser(user.get());
                        resetPassHistory.setOld_password(old_password);
                        //set lai time cho mk moi
                        Calendar c = Calendar.getInstance();
                        c.setTime(in);//set current time
                        //add 1 month to valid password when want to use old password
                        c.add(Calendar.MONTH, 1);
                        // convert calendar to date
                        Date currentDatePlusOne = c.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String date_format = dateFormat.format(currentDatePlusOne);
                        Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date_format);
                        //set future date
                        LocalDateTime localDateTime_futrue =
                                LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
                        resetPassHistory.setTime_active_pass(localDateTime_futrue);
                        resetPassHistory.setStatus(false);
                        resetPassHistoryRepository.save(resetPassHistory);

                        user.get().setPassword(bcrypt_password);
                        userRepository.save(user.get());
                        output.put("status", Constant.SUCCESS);
                        output.put("message", "Cập nhật mật khẩu thành công.");
                        return new ResponseEntity<>(output, HttpStatus.OK);
                    }
                }
            }
        }catch(Exception e){
            System.out.println("loi khong gui dc");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> updateProfile(UserProfileRequest userProfileRequest) {
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> output = new HashMap<>();
        MessageResponse messageResponse = new MessageResponse();
        try{
            Optional<User> user = userRepository.getUserById(userProfileRequest.getUser_id());
            user.get().setEmail(userProfileRequest.getEmail());
            user.get().setFullName(userProfileRequest.getFull_name());
            user.get().setPhone(userProfileRequest.getPhone());
            user.get().setImage(userProfileRequest.getImage());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate dateTime = LocalDate.parse(userProfileRequest.getDob(), formatter);
            user.get().setDob(dateTime);
            userRepository.save(user.get());
            messageResponse.setMessage("update thanh cong");
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }catch(Exception e){
            System.out.println("loi khong gui dc");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }


}
