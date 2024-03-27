package coffee.backoffice.player.service;

import coffee.backoffice.player.model.DepositInformation;
import coffee.backoffice.player.model.WithdrawalInformation;
import coffee.backoffice.player.repository.jpa.DepositInformationJpa;
import coffee.backoffice.player.repository.jpa.WithdrawalInformationJpa;
import framework.utils.UserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class WithdrawalInformationService {

    @Autowired
    WithdrawalInformationJpa withdrawalInformationJpa;


    public WithdrawalInformation getByUsername(String username) {
        WithdrawalInformation withdrawalInformation = withdrawalInformationJpa.findByUsername(username);
        if (withdrawalInformation != null) {
            return withdrawalInformation;
        } else {
            withdrawalInformation = new WithdrawalInformation();
            withdrawalInformation.setTotalCount(0L);
            withdrawalInformation.setTotalAmount(BigDecimal.valueOf(0));
            withdrawalInformation.setUsername(username);
            withdrawalInformationJpa.save(withdrawalInformation);
            return withdrawalInformation;
        }
    }

    public void addOrUpdateWithdrawalInformation(String username, BigDecimal amount) {
        WithdrawalInformation withdrawalInformation = withdrawalInformationJpa.findByUsername(username);
        if (withdrawalInformation != null) {
            if (withdrawalInformation.getTotalCount()==0){
                withdrawalInformation.setCreatedBy(UserLoginUtil.getUsername());
                withdrawalInformation.setCreatedDate(new Date());
            }
            withdrawalInformation.setUpdatedBy(UserLoginUtil.getUsername());
            withdrawalInformation.setUpdatedDate(new Date());
            withdrawalInformation.setTotalCount(withdrawalInformation.getTotalCount() + 1);
            withdrawalInformation.setTotalAmount(withdrawalInformation.getTotalAmount().add(amount));
        } else {
            withdrawalInformation = new WithdrawalInformation();
            withdrawalInformation.setTotalCount(1L);
            withdrawalInformation.setTotalAmount(amount);
            withdrawalInformation.setCreatedBy(UserLoginUtil.getUsername());
            withdrawalInformation.setUsername(username);
            withdrawalInformation.setCreatedDate(new Date());
        }
        withdrawalInformationJpa.save(withdrawalInformation);
    }
}
