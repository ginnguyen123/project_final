package cg.service.role;

import cg.model.user.Role;
import cg.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role, Long> {
    Role findByName(String name);
}
