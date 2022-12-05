package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reset_password_history")
public class ResetPassHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "old_password")
    private String old_password;

    @Column(name = "otp_code")
    private String otp_code;

    @Column(name = "expiration_date")
    private LocalDateTime expiration_date;

    @Column(name = "create_date")
    private LocalDateTime create_date;

    @Column(name = "current_token")
    private String current_token;

    @Column(name = "time_active_pass")
    private LocalDateTime time_active_pass;

    @Column(name = "status")
    private boolean status;
}
