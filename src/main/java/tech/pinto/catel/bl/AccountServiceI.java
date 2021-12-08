package tech.pinto.catel.bl;

import tech.pinto.catel.model.Order;
import tech.pinto.catel.user.User;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.vo.order.CreditUpVO;
import tech.pinto.catel.vo.user.UserForm;
import tech.pinto.catel.vo.user.UserInfo;
import tech.pinto.catel.vo.user.VipForm;

public interface AccountServiceI {

    /**
     * 注册账号
     */
    void registerAccount(User user) throws OopsException;

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     */
    User login(UserForm userForm) throws OopsException;

    UserInfo getUserInfo(int id) throws OopsException;

    User getUserById(int id) throws OopsException;

    /**
     * 更新用户个人信息
     */
    void updateInfo(UserInfo userInfo) throws OopsException;

    void creditDown(Order order);

    void vip(VipForm vipForm);

    void creditUp(CreditUpVO creditUpVO) throws OopsException;

}
