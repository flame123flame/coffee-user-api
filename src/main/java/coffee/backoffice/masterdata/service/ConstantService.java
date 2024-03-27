package coffee.backoffice.masterdata.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.masterdata.model.FwConstant;
import coffee.backoffice.masterdata.model.redis.ConstantRedisModel;
import coffee.backoffice.masterdata.repository.dao.FwConstantDao;
import coffee.backoffice.masterdata.repository.jpa.FwConstantRepository;
import coffee.backoffice.masterdata.repository.redis.ConstantRedisRepo;
import coffee.backoffice.masterdata.vo.req.ConstantReq;
import coffee.backoffice.masterdata.vo.res.ConstantRes;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.utils.ConvertDateUtils;
import framework.utils.UserLoginUtil;

@Service
public class ConstantService {

	@Autowired
	private FwConstantRepository fwConstantRepo;
	
	@Autowired private FwConstantDao constantDao;
	
	@Autowired 
	private ConstantRedisRepo constantRedisRepo;
	
	public ConstantRedisModel getConstant(String constantCode) {
		// find constant by constant code
		return constantRedisRepo.findByKey(constantCode);
	}

	public List<ConstantRes> getConstantList(ConstantReq form) {
		List<ConstantRes> dataRes = constantDao.findAllByConstantKey(form.getConstantKey());
		return dataRes;
	}

	public ConstantRes getConstantById(Long id) {
		// find constant by id
		FwConstant find = fwConstantRepo.findById(id).get();
		ConstantRes dataRes = new ConstantRes();
		//set constan to dataRes
		dataRes.setFwConstantId(find.getFwConstantId());
		dataRes.setConstantKey(find.getConstantKey());
		dataRes.setConstantValue(find.getConstantValue());
		dataRes.setCreateBy(find.getCreateBy());
		dataRes.setCreateDate(ConvertDateUtils.formatDateToString(find.getCreateDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
		dataRes.setUpdateBy(find.getUpdateBy());
		dataRes.setUpdateDate(ConvertDateUtils.formatDateToString(find.getUpdateDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
		
		//return constan to dataRes
		return dataRes;
	}

	public String saveConstant(ConstantReq form) {
		if(fwConstantRepo.findByConstantKey(form.getConstantKey()) != null) {
			return RESPONSE_STATUS.DUPLICATE_DATA.toString();
		}
		FwConstant dataSet = new FwConstant();
		//set constant to dataSet
		dataSet.setConstantKey(form.getConstantKey());
		dataSet.setConstantValue(form.getConstantValue());
		dataSet.setCreateBy(UserLoginUtil.getUsername());
		//save constant
		fwConstantRepo.save(dataSet);
		return RESPONSE_STATUS.SUCCESS.toString();
	}

	public void editConstant(ConstantReq form) {
		//find constant by id
		FwConstant dataSet = fwConstantRepo.findById(form.getFwConstantId()).get();
		//set constant to dataSet
		dataSet.setConstantKey(form.getConstantKey());
		dataSet.setConstantValue(form.getConstantValue());
		dataSet.setUpdateBy(UserLoginUtil.getUsername());
		dataSet.setUpdateDate(new Date());
		//save constant
		fwConstantRepo.save(dataSet);
	}

	public void deleteConstant(Long id) {
		//delete constant
		fwConstantRepo.deleteById(id);
	}
}
