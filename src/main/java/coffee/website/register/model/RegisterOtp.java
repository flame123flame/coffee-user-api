package coffee.website.register.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "register_otp")
@Data
public class RegisterOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column(name = "otp")
    private String otp;
    @Column(name = "count_otp")
    private Long countOtp;
    @Column(name = "created_date")
    private Date createdDate;
}
