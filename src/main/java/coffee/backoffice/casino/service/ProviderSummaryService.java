package coffee.backoffice.casino.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.casino.model.ProviderSummary;
import coffee.backoffice.casino.repository.jpa.ProviderSummaryRepository;

@Service
public class ProviderSummaryService {

    @Autowired
    private ProviderSummaryRepository providerSummaryRepository;

    public List<ProviderSummary> getProviderSummary(String username) {
        return providerSummaryRepository.findByUsername(username);
    }

    public ProviderSummary getProviderSummary(String providerCode, String username) {
        return providerSummaryRepository.findByProviderCodeAndUsername(providerCode, username);
    }

    public List<ProviderSummary> getProviderSummaryIn(List<String> providerCode, String username) {
        return providerSummaryRepository.findByProviderCodeInAndUsername(providerCode, username);
    }

    public BigDecimal getSumProviderSummaryIn(List<String> providerCode, String username) {
        BigDecimal res = providerSummaryRepository.findSumProviderSummary(providerCode, username);
        if (res != null) {
            return res;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public void createProviderSummary(String providerCode, String username) {
        ProviderSummary data = new ProviderSummary();
        data.setProviderCode(providerCode);
        data.setUsername(username);
        data.setTotalStake(BigDecimal.ZERO);
        data.setTotalValidsBet(BigDecimal.ZERO);
        data.setTotalLoss(BigDecimal.ZERO);
        data.setTotalWin(BigDecimal.ZERO);
        data.setUpdatedDate(new Date());
        data.setBetDaily(BigDecimal.ZERO);
        data.setLossDaily(BigDecimal.ZERO);

        providerSummaryRepository.save(data);
    }

    public void createProviderSummary(String providerCode, String username, BigDecimal stake, BigDecimal validsBet,
                                      BigDecimal winLoss) {
        ProviderSummary data = new ProviderSummary();
        data.setProviderCode(providerCode);
        data.setUsername(username);
        data.setTotalStake(stake);
        data.setTotalValidsBet(validsBet);
        if (winLoss.compareTo(BigDecimal.ZERO) < 0) {
            data.setTotalLoss(winLoss);
            data.setTotalWin(BigDecimal.ZERO);
        } else {
            data.setTotalLoss(BigDecimal.ZERO);
            data.setTotalWin(winLoss);
        }
        data.setLossDaily(winLoss);
        data.setWinLoss(winLoss);
        data.setUpdatedDate(new Date());
        data.setBetDaily(validsBet);

        providerSummaryRepository.save(data);
    }

    public void updateProviderSummary(String providerCode, String username, BigDecimal stake, BigDecimal validsBet,
                                      ProviderSummary data) {
        if (data != null) {
            data.setTotalStake(data.getTotalStake().add(stake));
            data.setTotalValidsBet(data.getTotalValidsBet().add(validsBet));
            data.setBetDaily(data.getBetDaily().add(validsBet));
            data.setUpdatedDate(new Date());
            providerSummaryRepository.save(data);
        }
    }

    public void updateProviderSummary(String providerCode, String username, BigDecimal stake, BigDecimal validsBet) {
        ProviderSummary data = providerSummaryRepository.findByProviderCodeAndUsername(providerCode, username);
        if (data != null) {
            data.setTotalStake(data.getTotalStake().add(stake));
            data.setTotalValidsBet(data.getTotalValidsBet().add(validsBet));
            data.setBetDaily(data.getBetDaily().add(stake));
            data.setUpdatedDate(new Date());
            providerSummaryRepository.save(data);
        }
    }

    public void updateBetDailyZero() {
        List<ProviderSummary> data = providerSummaryRepository.findAll();
        if (data == null) {
            return;
        }
        for (ProviderSummary providerSummary: data
             ) {
            providerSummary.setBetDaily(BigDecimal.ZERO);
            providerSummaryRepository.save(providerSummary);
        }
    }

}
