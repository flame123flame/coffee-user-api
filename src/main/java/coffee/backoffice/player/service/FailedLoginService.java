package coffee.backoffice.player.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.FailedLogin;
import coffee.backoffice.player.repository.dao.FailedLoginDao;
import coffee.backoffice.player.repository.jpa.FailedLoginJpa;
import coffee.backoffice.player.vo.req.FailedLoginReq;
import coffee.backoffice.player.vo.res.FailedLoginRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;

@Service
public class FailedLoginService {

	@Autowired
	FailedLoginJpa failedLoginJpa;

	@Autowired
	FailedLoginDao failedLoginDao;

	@Autowired
	CustomerService customerService;

	public void failedLogin(FailedLoginReq req) {
		Customer customer = customerService.getByUsername(req.getUsername());
		FailedLogin find = failedLoginJpa.findTop1ByUsernameAndRemarkOrderByCreatedDateDesc(req.getUsername(),
				req.getRemark());
		if (customer != null) {
			List<String> realname = Arrays.asList(customer.getRealName().split(" "));
			req.setFirstname(realname.get(0));
			req.setLastname(realname.get(1));
		}

//		หาใน failed login ไม่มี  และ ใน customer มี ให้   save
		if (find == null && customer != null) {
			saveFailedLogin(req);
		} else {
			if (isAtleastTenMinutesAgo(find.getCreatedDate())) {
				saveFailedLogin(req);
			} else {
				updateFailedLogin(req, find);
			}
		}

	}

	public void saveFailedLogin(FailedLoginReq req) {
		FailedLogin item = new FailedLogin();
		item.setFailedLoginCode(GenerateRandomString.generate());
		item.setUsername(req.getUsername());
		item.setRealName(req.getFirstname() + " " + req.getLastname());
		item.setLastFailedLogin(new Date());
		item.setCountFailedLogin(1);
		item.setRemark(req.getRemark());
		item.setCountry(req.getCountry());
		item.setIpAddress(req.getIpAddress());
		item.setPlatform(req.getPlatform());
		item.setBrowserName(req.getBrowserName());
		item.setBrowserVersion(req.getBrowserVersion());
		item.setCreatedDate(new Date());
		failedLoginJpa.save(item);
	}

	public void updateFailedLogin(FailedLoginReq req, FailedLogin item) {
		item.setCountFailedLogin(item.getCountFailedLogin() + 1);
		item.setLastFailedLogin(new Date());
		failedLoginJpa.save(item);
	}

	public Boolean isAtleastTenMinutesAgo(Date date) {
		Date now = new Date();
		Date previous = date;
		Long duration = now.getTime() - previous.getTime();
		if (duration > 10 * 60 * 1000) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public void deleteByFailedLogincode(String faliedLoginCode) {
		failedLoginJpa.deleteByFailedLoginCode(faliedLoginCode);
	}

	public DataTableResponse<FailedLoginRes> paginate(DatatableRequest req) throws Exception {
		DataTableResponse<FailedLoginRes> paginateData = failedLoginDao.paginate(req);
		DataTableResponse<FailedLoginRes> dataTable = new DataTableResponse<>();
		List<FailedLoginRes> data = paginateData.getData();
		dataTable.setRecordsTotal(paginateData.getRecordsTotal());
		dataTable.setDraw(paginateData.getDraw());
		dataTable.setData(data);
		dataTable.setPage(req.getPage());
		return paginateData;
	}
}
