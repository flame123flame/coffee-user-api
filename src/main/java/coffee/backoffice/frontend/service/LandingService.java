package coffee.backoffice.frontend.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.frontend.model.Landing;
import coffee.backoffice.frontend.repository.jpa.LandingJpa;
import coffee.backoffice.frontend.vo.req.LandingReq;
import coffee.backoffice.frontend.vo.res.LandingRes;
import coffee.website.frontend.vo.res.LandingWebPlayer;
import framework.utils.UserLoginUtil;

@Service
public class LandingService {

	@Autowired
	private LandingJpa landingJpa;

	public List<Landing> getLanding() {
		return landingJpa.findAllByOrderByCreatedDateDesc();
	}

	public void saveLanding(LandingReq req) {
		Landing data = new Landing();
		data.setHeader(req.getHeader());
		data.setDetail(req.getDetail());
		if (req.getImg() != null) {
			data.setImg(String.join(",", req.getImg()));
		} else {
			data.setImg(null);
		}
		data.setConfigPath(req.getConfigPath());
		data.setCreatedBy(UserLoginUtil.getUsername());
		landingJpa.save(data);

	}

	public void editLanding(LandingReq req) {
		Landing data = landingJpa.findById(req.getId()).get();
		if (data != null) {
			data.setHeader(req.getHeader());
			data.setDetail(req.getDetail());
			if (req.getImg() != null) {
				data.setImg(String.join(",", req.getImg()));
			} else {
				data.setImg(null);
			}
			data.setConfigPath(req.getConfigPath());
			data.setUpdatedBy(UserLoginUtil.getUsername());
			data.setUpdatedDate(new Date());
			landingJpa.save(data);
		}
	}

	public LandingRes getLandingById(Long id) {
		LandingRes res = new LandingRes();
		Landing data = landingJpa.findById(id).get();
		res.setId(data.getId());
		res.setHeader(data.getHeader());
		res.setDetail(data.getDetail());
		res.setConfigPath(data.getConfigPath());
		if (data.getImg() != null) {
			List<String> imgList = Arrays.asList(data.getImg().split(","));
			res.setImg(imgList);
		}
		return res;
	}

	public LandingWebPlayer getLandingByPath(String configPath) {
		LandingWebPlayer res = new LandingWebPlayer();
		Landing data = landingJpa.findByConfigPath(configPath);
		res.setHeader(data.getHeader());
		res.setDetail(data.getDetail());
		res.setConfigPath(data.getConfigPath());
		if (data.getImg() != null) {
			List<String> imgList = Arrays.asList(data.getImg().split(","));
			res.setImg(imgList);
		}
		return res;
	}

	@Transactional
	public void deleteLanding(Long id) {
		landingJpa.deleteById(id);
	}
}
