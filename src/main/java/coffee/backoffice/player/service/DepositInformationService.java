package coffee.backoffice.player.service;

import coffee.backoffice.player.model.DepositInformation;
import coffee.backoffice.player.model.WithdrawalInformation;
import coffee.backoffice.player.repository.jpa.DepositInformationJpa;
import framework.utils.UserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class DepositInformationService {
    @Autowired
    DepositInformationJpa depositInformationJpa;



    public DepositInformation getByUsername(String username) {
        DepositInformation depositInformation = depositInformationJpa.findByUsername(username);
        if (depositInformation != null) {
            return depositInformation;
        } else {
            depositInformation = new DepositInformation();
            depositInformation.setTotalCount(0L);
            depositInformation.setTotalAmount(BigDecimal.valueOf(0));
            depositInformation.setUsername(username);
            depositInformationJpa.save(depositInformation);
            return depositInformation;
        }
    }

    public void addOrUpdateDepositInformation(String username, BigDecimal amount){
        DepositInformation depositInformation = depositInformationJpa.findByUsername(username);
        if (depositInformation!=null){
            if (depositInformation.getTotalCount()==0){
                depositInformation.setCreatedBy(UserLoginUtil.getUsername());
                depositInformation.setCreatedDate(new Date());
            }
            depositInformation.setUpdatedBy(UserLoginUtil.getUsername());
            depositInformation.setUpdatedDate(new Date());
            depositInformation.setTotalCount(depositInformation.getTotalCount()+1);
            depositInformation.setTotalAmount(depositInformation.getTotalAmount().add(amount));
        }else{
            depositInformation = new DepositInformation();
            depositInformation.setTotalCount(1L);
            depositInformation.setTotalAmount(amount);
            depositInformation.setUsername(username);
            depositInformation.setCreatedBy(UserLoginUtil.getUsername());
            depositInformation.setCreatedDate(new Date());
        }
        depositInformationJpa.save(depositInformation);
    }

}
