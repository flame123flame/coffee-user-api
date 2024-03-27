package coffee.backoffice.masterdata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.masterdata.model.FwLovDtl;
import coffee.backoffice.masterdata.model.FwLovHdr;
import coffee.backoffice.masterdata.model.redis.LovDetail;
import coffee.backoffice.masterdata.repository.jpa.FwLovDtlRepository;
import coffee.backoffice.masterdata.repository.jpa.FwLovHdrRepository;
import coffee.backoffice.masterdata.repository.redis.LovRedisRepo;
import coffee.backoffice.masterdata.vo.req.LovDtlReq;
import coffee.backoffice.masterdata.vo.req.LovHdrReq;
import coffee.backoffice.masterdata.vo.res.LovDtlRes;
import coffee.backoffice.masterdata.vo.res.LovHdrRes;
import framework.utils.ConvertDateUtils;
import framework.utils.UserLoginUtil;

@Service
public class LovService {

	@Autowired
	private FwLovHdrRepository fwLovHdrRepo;

	@Autowired
	private FwLovDtlRepository fwLovDtlRepo;

	@Autowired
	private LovRedisRepo lovRedisRepo;
	
	public List<LovDetail> getLov(String lovKey){
		return lovRedisRepo.findByLovKey(lovKey).getValue();
	}
	
	public List<LovHdrRes> getListLov() {
		List<LovHdrRes> dataRes = new ArrayList<LovHdrRes>();
		LovHdrRes lovHdrSet;
		List<LovDtlRes> listLovDtlSet;
		LovDtlRes lovDtlSet;

		// find lov hdr
		List<FwLovHdr> dataHdrFind = fwLovHdrRepo.findAllByOrderByCreateDateDesc();
		// loop lov hdr
		for (FwLovHdr lovHdrGet : dataHdrFind) {
			lovHdrSet = new LovHdrRes();
			// set lov hdr to lovHdrSet
			lovHdrSet.setFwLovHdrId(lovHdrGet.getFwLovHdrId());
			lovHdrSet.setLovKey(lovHdrGet.getLovKey());
			lovHdrSet.setLovDescription(lovHdrGet.getLovDescription());
			lovHdrSet.setCreateBy(lovHdrGet.getCreateBy());
			lovHdrSet.setCreateDate(ConvertDateUtils.formatDateToString(lovHdrGet.getCreateDate(), ConvertDateUtils.DD_MM_YYYY_HHMM));
			lovHdrSet.setUpdateBy(lovHdrGet.getUpdateBy());
			lovHdrSet.setCreateDate(ConvertDateUtils.formatDateToString(lovHdrGet.getUpdateDate(), ConvertDateUtils.DD_MM_YYYY_HHMM));

			listLovDtlSet = new ArrayList<LovDtlRes>();
			// find lov dtl
			List<FwLovDtl> dataDtlFind = fwLovDtlRepo.findByLovKey(lovHdrGet.getLovKey());
			// loop lov dtl
			for (FwLovDtl lovDtlGet : dataDtlFind) {
				lovDtlSet = new LovDtlRes();
				// set lov dtl to lovDtlSet
				lovDtlSet.setFwLovDtlId(lovDtlGet.getFwLovDtlId());
				lovDtlSet.setLovKey(lovDtlGet.getLovKey());
				lovDtlSet.setCodeDetail(lovDtlGet.getCodeDetail());
				lovDtlSet.setValueTh1(lovDtlGet.getValueTh1());
				lovDtlSet.setValueEn1(lovDtlGet.getValueEn1());
				lovDtlSet.setValueTh2(lovDtlGet.getValueTh2());
				lovDtlSet.setValueEn2(lovDtlGet.getValueEn2());
				lovDtlSet.setSeq(lovDtlGet.getSeq());
				lovDtlSet.setCreateBy(lovDtlGet.getCreateBy());
				lovDtlSet.setCreateDate(ConvertDateUtils.formatDateToString(lovDtlGet.getCreateDate(), ConvertDateUtils.DD_MM_YYYY_HHMM));
				lovDtlSet.setUpdateBy(lovDtlGet.getUpdateBy());
				lovDtlSet.setCreateDate(ConvertDateUtils.formatDateToString(lovDtlGet.getUpdateDate(), ConvertDateUtils.DD_MM_YYYY_HHMM));
				// add lov dtl to listLovDtlSet
				listLovDtlSet.add(lovDtlSet);
			}

			lovHdrSet.setListDetail(listLovDtlSet);
			// add lov hdr to dataRes
			dataRes.add(lovHdrSet);

		}
		return dataRes;
	}

	public List<FwLovDtl> getLovByKey(LovHdrReq req) {
		return fwLovDtlRepo.findByLovKey(req.getLovKey());

	}

	public LovHdrRes getLovById(Long id) {
		// find lov hdr by id
		FwLovHdr lovHdrfind = fwLovHdrRepo.findById(id).get();
		LovHdrRes dataRes = new LovHdrRes();
		// set lov hdr to dataRes
		dataRes.setFwLovHdrId(lovHdrfind.getFwLovHdrId());
		dataRes.setLovKey(lovHdrfind.getLovKey());
		dataRes.setLovDescription(lovHdrfind.getLovDescription());
		dataRes.setCreateBy(lovHdrfind.getCreateBy());
		dataRes.setCreateDate(ConvertDateUtils.formatDateToString(lovHdrfind.getCreateDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
		dataRes.setUpdateBy(lovHdrfind.getUpdateBy());
		dataRes.setUpdateDate(ConvertDateUtils.formatDateToString(lovHdrfind.getUpdateDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));

		// find lov dtl by lovKey
		List<FwLovDtl> lovDtlfind = fwLovDtlRepo.findByLovKey(lovHdrfind.getLovKey());
		List<LovDtlRes> listLovDtlSet = new ArrayList<LovDtlRes>();
		;
		LovDtlRes lovDtlSet;
		// loop lov dtl
		for (FwLovDtl lovDtlGet : lovDtlfind) {
			lovDtlSet = new LovDtlRes();
			// set lov dtl to lovDtlSet
			lovDtlSet.setFwLovDtlId(lovDtlGet.getFwLovDtlId());
			lovDtlSet.setLovKey(lovDtlGet.getLovKey());
			lovDtlSet.setValueTh1(lovDtlGet.getValueTh1());
			lovDtlSet.setCodeDetail(lovDtlGet.getCodeDetail());
			lovDtlSet.setValueEn1(lovDtlGet.getValueEn1());
			lovDtlSet.setValueTh2(lovDtlGet.getValueTh2());
			lovDtlSet.setValueEn2(lovDtlGet.getValueEn2());
			lovDtlSet.setSeq(lovDtlGet.getSeq());
			lovDtlSet.setCreateBy(lovDtlGet.getCreateBy());
			lovDtlSet.setCreateDate(ConvertDateUtils.formatDateToString(lovDtlGet.getCreateDate(), ConvertDateUtils.DD_MM_YYYY_HHMM));
			lovDtlSet.setUpdateBy(lovDtlGet.getUpdateBy());
			lovDtlSet.setUpdateDate(ConvertDateUtils.formatDateToString(lovDtlGet.getUpdateDate(), ConvertDateUtils.DD_MM_YYYY_HHMM));
			// add lov dtl to listLovDtlSet
			listLovDtlSet.add(lovDtlSet);
		}

		dataRes.setListDetail(listLovDtlSet);
		return dataRes;
	}

	@Transactional
	public void saveLov(LovHdrReq req) {
		// entity set save
		FwLovHdr lovHdrEntity;
		FwLovDtl lovDtlEntity;

		// save lov hdr
		lovHdrEntity = new FwLovHdr();
		// set lov hdr to dataSet
		lovHdrEntity.setLovKey(req.getLovKey());
		lovHdrEntity.setLovDescription(req.getLovDescription());
		lovHdrEntity.setCreateBy(UserLoginUtil.getUsername());
		fwLovHdrRepo.save(lovHdrEntity);

		// index for seq
		int indexLovDtl = 0;
		// loop save lov dtl
		for (LovDtlReq lovDtlGet : req.getListDetail()) {
			lovDtlEntity = new FwLovDtl();
			// set lov dtl
			lovDtlEntity.setLovKey(req.getLovKey());
			lovDtlEntity.setCodeDetail(lovDtlGet.getCodeDetail());
			lovDtlEntity.setValueTh1(lovDtlGet.getValueTh1());
			lovDtlEntity.setValueEn1(lovDtlGet.getValueEn1());
			lovDtlEntity.setValueTh2(lovDtlGet.getValueTh2());
			lovDtlEntity.setValueEn2(lovDtlGet.getValueEn2());
			lovDtlEntity.setSeq(indexLovDtl);
			lovDtlEntity.setCreateBy(UserLoginUtil.getUsername());
			// save lov dtl
			fwLovDtlRepo.save(lovDtlEntity);

			indexLovDtl++;
		}
	}

	@Transactional
	public void editLov(LovHdrReq req) {
		// find lov hdr by id
		FwLovHdr lovHdrEntity = fwLovHdrRepo.findById(req.getFwLovHdrId()).get();
		// set lov hdr by id
		lovHdrEntity.setFwLovHdrId(req.getFwLovHdrId());
		lovHdrEntity.setLovKey(req.getLovKey());
		lovHdrEntity.setLovDescription(req.getLovDescription());
		lovHdrEntity.setUpdateBy(UserLoginUtil.getUsername());
		lovHdrEntity.setUpdateDate(new Date());
		fwLovHdrRepo.save(lovHdrEntity);

		// index for seq
		int indexLovDtl = 0;

		// loop save lov dtl
		for (LovDtlReq lovDtlGet : req.getListDetail()) {
			// find lov dtl by id
			FwLovDtl lovDtlEntity = null;
			if (lovDtlGet.getFwLovDtlId() != null) {
				lovDtlEntity = fwLovDtlRepo.findById(lovDtlGet.getFwLovDtlId()).get();
				// set lov dtl
				lovDtlEntity.setFwLovDtlId(lovDtlGet.getFwLovDtlId());
				lovDtlEntity.setLovKey(req.getLovKey());
				lovDtlEntity.setCodeDetail(lovDtlGet.getCodeDetail());
				lovDtlEntity.setValueTh1(lovDtlGet.getValueTh1());
				lovDtlEntity.setValueEn1(lovDtlGet.getValueEn1());
				lovDtlEntity.setValueTh2(lovDtlGet.getValueTh2());
				lovDtlEntity.setCreateBy(UserLoginUtil.getUsername());
				lovDtlEntity.setValueEn2(lovDtlGet.getValueEn2());
				lovDtlEntity.setSeq(indexLovDtl);
				lovDtlEntity.setUpdateBy(UserLoginUtil.getUsername());
				lovDtlEntity.setUpdateDate(new Date());
			} else {
				lovDtlEntity = new FwLovDtl();
				lovDtlEntity.setFwLovDtlId(lovDtlGet.getFwLovDtlId());
				lovDtlEntity.setLovKey(req.getLovKey());
				lovDtlEntity.setCodeDetail(lovDtlGet.getCodeDetail());
				lovDtlEntity.setValueTh1(lovDtlGet.getValueTh1());
				lovDtlEntity.setValueEn1(lovDtlGet.getValueEn1());
				lovDtlEntity.setValueTh2(lovDtlGet.getValueTh2());
				lovDtlEntity.setCreateBy(UserLoginUtil.getUsername());
				lovDtlEntity.setValueEn2(lovDtlGet.getValueEn2());
				lovDtlEntity.setSeq(indexLovDtl);
				lovDtlEntity.setUpdateBy(UserLoginUtil.getUsername());
				lovDtlEntity.setUpdateDate(new Date());
			}

			// save lov dtl
			fwLovDtlRepo.save(lovDtlEntity);

			indexLovDtl++;
		}

	}

	@Transactional
	public void deleteLovHdr(String lovKey) {
		fwLovHdrRepo.deleteByLovKey(lovKey);
		fwLovDtlRepo.deleteByLovKey(lovKey);
	}

	@Transactional
	public void deleteLovDtl(Long id) {
		fwLovDtlRepo.deleteById(id);
		;
	}
}
