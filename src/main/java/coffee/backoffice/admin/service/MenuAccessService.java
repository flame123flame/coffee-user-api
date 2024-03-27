package coffee.backoffice.admin.service;

import coffee.backoffice.admin.repository.jpa.FwRoleMenuAccessRepository;
import coffee.backoffice.admin.repository.jpa.FwRoleRepository;
import coffee.backoffice.admin.vo.res.FwRoleRes;
import framework.security.model.FwUser;
import framework.security.repository.FwUserRepo;
import framework.utils.UserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuAccessService {

    @Autowired
    FwRoleMenuAccessRepository fwRoleMenuAccessRepository;
    @Autowired
    FwRoleRepository fwRoleRepository;
    @Autowired
    FwUserRepo fwUserRepository;
    @Autowired
    FwRoleService fwRoleService;

    public FwRoleRes getMenuAccess() throws Exception{
        FwUser fwUser = fwUserRepository.findByUsername(UserLoginUtil.getUsername());
        FwRoleRes fwRoleRes = new FwRoleRes();
        return fwRoleRes;
//        return fwRoleService.getMenuAccess(fwUser.getFwUserRoleCode());
    }
}
