package coffee.backoffice.player.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.vo.res.AllTransactionRes;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.TagManagement;
import coffee.backoffice.player.repository.dao.NewRegistrantDao;
import coffee.backoffice.player.repository.jpa.CustomerRepository;
import coffee.backoffice.player.repository.jpa.NewRegistrantRepository;
import coffee.backoffice.player.repository.jpa.TagManagementJpa;
import coffee.backoffice.player.vo.req.NewRegistrantReq;
import coffee.backoffice.player.vo.res.NewRegistrantRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;

@Service
public class NewRegistrantService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private NewRegistrantDao newRegistrantDao;
	
	@Autowired
	private TagManagementJpa tagManagementJpa;
	
	@Autowired
	private NewRegistrantRepository newRegistrantRepository;

	public List<NewRegistrantRes> getCountToDay() {
        List<NewRegistrantRes> dataSet = newRegistrantDao.getCountToDay();
        return dataSet;
    }
	
	
	public List<NewRegistrantRes> getCountToWeek() {
//		System.out.println("+++++++++++++++++++++++++++++++++++++" + req.getStartDate());
//		System.out.println("+++++++++++++++++++++++++++++++++++++" + req.getEndDate());
//		NewRegistrantRes dataSet = new  NewRegistrantRes();
//		Integer  countToweek = newRegistrantRepository.findByCountThisWeek(req.getStartDate(),req.getEndDate());
//		System.out.println("=====================================" + countToweek);
        List<NewRegistrantRes> dataSet = newRegistrantDao.getCountToWeek();
        return dataSet;
    }
	
	public List<NewRegistrantRes> getCountToLastweek() {
        List<NewRegistrantRes> dataSet = newRegistrantDao.getCountLastweek();
        return dataSet;
    }
	
	public DataTableResponse<NewRegistrantRes> getPaginate(DatatableRequest req) {
		DataTableResponse<NewRegistrantRes> dataTable = new DataTableResponse<>();
		DataTableResponse<NewRegistrantRes> tag = newRegistrantDao.getPaginate(req);
		List<NewRegistrantRes> data = tag.getData();
		for(NewRegistrantRes item:data) {
		if (item.getTagCode() != null) {
			List<String> listTagCode = Arrays.asList(item.getTagCode().split(","));
			List<String> tempList = new ArrayList<String>();
			for (String code : listTagCode) {
				Optional<TagManagement> tagdata = tagManagementJpa.findByTagCode(code);
				tempList.add(tagdata.get().getName());
			}
			item.setTagName(String.join(",", tempList));
		}
	}
		dataTable.setRecordsTotal(tag.getRecordsTotal());
		dataTable.setDraw(tag.getDraw());
		dataTable.setData(data);
		return dataTable;
    }
	
	public void editEnable(NewRegistrantReq req) {
		Customer data = customerRepository.findByUsername(req.getUsername());
		if (data != null) {
			data.setEnable(req.getEnable());
			data.setUpdatedBy(UserLoginUtil.getUsername());
			data.setUpdatedDate(new Date());
		customerRepository.save(data);
	}
}
}