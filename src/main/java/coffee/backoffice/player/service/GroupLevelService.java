package coffee.backoffice.player.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.repository.dao.GroupLevelDao;
import coffee.backoffice.player.repository.jpa.GroupLevelRepository;
import coffee.backoffice.player.vo.req.GroupLevelReq;
import coffee.backoffice.player.vo.res.GroupLevelRes;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class GroupLevelService {

	@Autowired
	private GroupLevelRepository groupLevelRepository;

	@Autowired
	private GroupLevelDao groupLevelDao;

	public List<GroupLevelRes> getAll() {
		List<GroupLevelRes> dataRes = groupLevelDao.findCustomerByGroupName();
		return dataRes;

	}
	
	public GroupLevel getDefaultGroup() {
		return groupLevelRepository.findByDefaultGroup(true);

	}

	public GroupLevel getByGroupCode(String groupCode) {
		return groupLevelRepository.findByGroupCode(groupCode);
	}

	public void saveNewGroupLevel(GroupLevelReq form) {
		GroupLevel groupLevel = new GroupLevel();
		groupLevel.setGroupCode(GenerateRandomString.generate());
		groupLevel.setGroupName(form.getGroupName());
		groupLevel.setCountPlayer(form.getCountPlayer());
		groupLevel.setStatus(form.getStatus());
		groupLevel.setDescription(form.getDescription());
		groupLevel.setDefaultGroup(form.getDefaultGroup());
		groupLevel.setMinDepositAmt(form.getMinDepositAmt());
		groupLevel.setMaxDepositAmt(form.getMaxDepositAmt());
		groupLevel.setMinWithdrawalAmt(form.getMinWithdrawalAmt());
		groupLevel.setMaxWithdrawalAmt(form.getMaxWithdrawalAmt());
		groupLevel.setDailyMaxWDAmount(form.getDailyMaxWDAmount());
		groupLevel.setDailyMaxWDCount(form.getDailyMaxWDCount());
		groupLevel.setUpdateOn(new Date());
		groupLevel.setUpdateBy(UserLoginUtil.getUsername());
		groupLevel.setGroupMobilePhone(form.getGroupMobilePhone());
		groupLevel.setGroupLinkLine(form.getGroupLinkLine());
		groupLevel.setGroupIconImg(form.getGroupIconImg());
		groupLevel.setGeneralCondition(form.getGeneralCondition());
		groupLevel.setLevel(form.getLevel());
		groupLevelRepository.save(groupLevel);
		
		if(form.getDefaultGroup()) {
			List<GroupLevel>  listGroup = groupLevelRepository.findAllByGroupCodeNotLike(groupLevel.getGroupCode());
			for(GroupLevel group:listGroup) {
				group.setDefaultGroup(false);
			}
			groupLevelRepository.saveAll(listGroup);
		}
	}

	public void editGroupLevel(GroupLevelReq form) {
		GroupLevel dataSave = groupLevelRepository.findByGroupCode(form.getGroupCode());
		dataSave.setGroupCode(form.getGroupCode());
		dataSave.setGroupName(form.getGroupName());
		dataSave.setCountPlayer(form.getCountPlayer());
		dataSave.setStatus(form.getStatus());
		dataSave.setDescription(form.getDescription());
		dataSave.setDefaultGroup(form.getDefaultGroup());
		dataSave.setMinDepositAmt(form.getMinDepositAmt());
		dataSave.setMaxDepositAmt(form.getMaxDepositAmt());
		dataSave.setMinWithdrawalAmt(form.getMinWithdrawalAmt());
		dataSave.setMaxWithdrawalAmt(form.getMaxWithdrawalAmt());
		dataSave.setDailyMaxWDAmount(form.getDailyMaxWDAmount());
		dataSave.setDailyMaxWDCount(form.getDailyMaxWDCount());
		dataSave.setUpdateOn(new Date());
		dataSave.setUpdateBy(UserLoginUtil.getUsername());
		dataSave.setGroupMobilePhone(form.getGroupMobilePhone());
		dataSave.setGroupLinkLine(form.getGroupLinkLine());
		dataSave.setGroupIconImg(form.getGroupIconImg());
		dataSave.setGeneralCondition(form.getGeneralCondition());
		dataSave.setLevel(form.getLevel());
		groupLevelRepository.save(dataSave);
		
		if(form.getDefaultGroup()) {
			List<GroupLevel>  listGroup = groupLevelRepository.findAllByGroupCodeNotLike(dataSave.getGroupCode());
			for(GroupLevel group:listGroup) {
				group.setDefaultGroup(false);
			}
			groupLevelRepository.saveAll(listGroup);
		}
	}

	@Transactional
	public void deleteGroupLevel(String groupCode) {
		groupLevelRepository.deleteByGroupCode(groupCode);
	}

	public GroupLevelRes getGroupLevelByGroupCode(String groupCode) {
		GroupLevel data = groupLevelRepository.findByGroupCode(groupCode);
		GroupLevelRes dataRes = new GroupLevelRes();
		dataRes.setId(data.getId());
		dataRes.setGroupCode(data.getGroupCode());
		dataRes.setGroupName(data.getGroupName());
		dataRes.setCountPlayer(data.getCountPlayer());
		dataRes.setStatus(data.getStatus());
		dataRes.setDescription(data.getDescription());
		dataRes.setDefaultGroup(data.getDefaultGroup());
		dataRes.setMinDepositAmt(data.getMinDepositAmt());
		dataRes.setMaxDepositAmt(data.getMaxDepositAmt());
		dataRes.setMinWithdrawalAmt(data.getMinWithdrawalAmt());
		dataRes.setMaxWithdrawalAmt(data.getMaxWithdrawalAmt());
		dataRes.setDailyMaxWDAmount(data.getDailyMaxWDAmount());
		dataRes.setDailyMaxWDCount(data.getDailyMaxWDCount());
		dataRes.setUpdateOn(data.getUpdateOn());
		dataRes.setUpdateBy(data.getUpdateBy());
		dataRes.setGroupMobilePhone(data.getGroupMobilePhone());
		dataRes.setGroupLinkLine(data.getGroupLinkLine());
		dataRes.setGroupIconImg(data.getGroupIconImg());
		dataRes.setGeneralCondition(data.getGeneralCondition());
		dataRes.setLevel(data.getLevel());
		return dataRes;
	}

	public List<GroupLevel> getDropdownGroup() {
		List<GroupLevel> dataRes = groupLevelRepository.findAll();
		return dataRes;
	}
}
