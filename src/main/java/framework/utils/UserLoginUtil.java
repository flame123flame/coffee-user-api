package framework.utils;

import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import framework.model.UserDetailModel;

public class UserLoginUtil {

	public static UserDetailModel getCurrentUserBean() {
		UserDetailModel userBean = null;

		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = "";
			String token = "";
			if (principal instanceof UserDetailModel) {
				token = ((UserDetailModel)principal).getToken() ;
				username = ((UserDetailModel)principal).getUsername() ;
			}
			userBean = new UserDetailModel(username);
			userBean.setToken(token);
		} else {
			String username = "NO LOGIN";
			userBean = new UserDetailModel(username);
		}
		return userBean;
	}

	public static String getToken() {
		return UserLoginUtil.getCurrentUserBean().getToken();
	}
	
	public static String getUsername() {
		return UserLoginUtil.getCurrentUserBean().getUsername();
	}
}
