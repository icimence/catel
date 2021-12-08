package tech.pinto.catel.admin;

import tech.pinto.catel.bl.AdminServiceI;
import tech.pinto.catel.bl.AccountServiceI;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements AdminServiceI {

    final AdminMapper adminMapper;
    final AccountServiceI accountService;
    final AccountMapper accountMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper, AccountServiceI accountService, AccountMapper accountMapper) {
        this.adminMapper = adminMapper;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }

    @Override
    public void bindToManager(int hotelId, int managerId) {
        adminMapper.bind(hotelId, managerId);
    }

    @Override
    public void deleteHotel(int id) {
        adminMapper.deleteHotel(id);
    }

    @Override
    public void deleteUser(int id) {
        adminMapper.beforeDelete(id);
        accountMapper.beforeDelete(id);
        adminMapper.deleteUser(id);
    }

    @Override
    public List<User> getAllMarketers() {
        return adminMapper.getAllMarketers();
    }

}
