package coffee.website.register.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coffee.website.register.model.RegisterOtp;

import java.util.Optional;

public interface RegisterOtpJpa extends JpaRepository<RegisterOtp , Long> {

    RegisterOtp save(RegisterOtp entity);

    Optional<RegisterOtp> findByMobilePhone(String phone);
    boolean existsByMobilePhone(String phone);

    boolean existsByMobilePhoneAndOtp(String phone , String otp);

    Optional<RegisterOtp> findByMobilePhoneAndOtp(String phone , String otp);
}
