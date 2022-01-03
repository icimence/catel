package tech.pinto.catel.admin;

import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.user.UserService;
import tech.pinto.catel.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService  {

    final AdminMapper adminMapper;
    final UserService userService;
    final AccountMapper accountMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper, UserService userService, AccountMapper accountMapper) {
        this.adminMapper = adminMapper;
        this.userService = userService;
        this.accountMapper = accountMapper;
    }

    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }

    public void bindToManager(int hotelId, int managerId) {
        adminMapper.bind(hotelId, managerId);
    }

    public void deleteHotel(int id) {
        adminMapper.deleteHotel(id);
    }

    public void deleteUser(int id) {
        adminMapper.beforeDelete(id);
        accountMapper.beforeDelete(id);
        adminMapper.deleteUser(id);
    }

    public List<User> getAllMarketers() {
        return adminMapper.getAllMarketers();
    }

}
