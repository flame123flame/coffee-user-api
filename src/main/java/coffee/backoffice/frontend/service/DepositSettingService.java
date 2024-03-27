package coffee.backoffice.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.frontend.model.DepositSetting;
import coffee.backoffice.frontend.repository.jpa.DepositSettingJpa;
import coffee.backoffice.frontend.vo.req.DepositSettingReq;
import framework.utils.UserLoginUtil;

@Service
public class DepositSettingService {

	@Autowired
	private DepositSettingJpa depositSettingJpa;

	public void saveDepositSetting(DepositSettingReq req) {
		DepositSetting data = depositSettingJpa.findById(req.getId()).get();
		if (data != null) {
			data.setMoneyTransfer(req.getMoneyTransfer());
			data.setUpdatedBy(UserLoginUtil.getUsername());
			depositSettingJpa.save(data);
		} else {
			data = new DepositSetting();
			data.setMoneyTransfer(req.getMoneyTransfer());
			data.setUpdatedBy(UserLoginUtil.getUsername());
			depositSettingJpa.save(data);
		}
	}

	public DepositSetting getDepositSettingById(Long id) {
		DepositSetting res = depositSettingJpa.findById(id).get();
		return res;
	}

	public DepositSetting getDepositSetting() {
		List<DepositSetting> res = depositSettingJpa.findAll();
		DepositSetting daraRes = new DepositSetting();
		if (res != null)
			daraRes = res.get(0);
		return daraRes;
	}
}
