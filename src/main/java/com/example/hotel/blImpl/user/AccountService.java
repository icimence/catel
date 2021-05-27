package com.example.hotel.blImpl.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.user.AccountServiceI;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.PersonMapper;
import com.example.hotel.enums.VipType;
import com.example.hotel.po.CreditUp;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.OopsException;
import com.example.hotel.util.OssService;
import com.example.hotel.vo.order.CreditUpVO;
import com.example.hotel.vo.user.UserForm;
import com.example.hotel.vo.user.UserInfo;
import com.example.hotel.vo.user.VipForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AccountService implements AccountServiceI {

    private final AccountMapper accountMapper;
    private final PersonMapper personMapper;
    private final OssService ossService;

    @Autowired
    public AccountService(AccountMapper accountMapper, PersonMapper personMapper, OssService ossService) {
        this.accountMapper = accountMapper;
        this.personMapper = personMapper;
        this.ossService = ossService;
    }

    @Override
    public void registerAccount(User user) throws OopsException {
        if (exists(user)) throw new OopsException(1);
        accountMapper.createNewAccount(user);
    }

    @Override
    public User login(UserForm userForm) throws OopsException {
        User user = accountMapper.getAccountByEmail(userForm.getEmail());
        if (user == null) user = accountMapper.getAccountByUsername(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            throw new OopsException(3);
        }
        return user;
    }

    public User getUserById(int id) throws OopsException {
        User user = accountMapper.select(id);
        if (null == user) throw new OopsException(4);
        user.setCredit(creditAnew(id));
        return user;
    }

    private double creditAnew(int id) {
        return accountMapper.getCreditFromOrder(id) + 100 + accountMapper.getCreditFromDirect(id);
    }

    @Override
    public UserInfo getUserInfo(int id) throws OopsException {
        User user = getUserById(id);
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(user, userInfo, CopyOptions.create().ignoreNullValue());
        return userInfo;
    }

    @Override
    public void updateInfo(UserInfo userInfo) throws OopsException {
        User user = accountMapper.select(userInfo.getId());
        boolean newAvatar = null != userInfo.getAvatar() && !userInfo.getAvatar().equals(user.getAvatar());
        BeanUtil.copyProperties(userInfo, user, CopyOptions.create().ignoreNullValue());
        if (exists(user)) throw new OopsException(1);
        if (newAvatar) {
            user.setAvatar(ossService.savePublic("hotel-res-img-public",
                    "user/" + user.getId().toString() + "/avatar.png", user.getAvatar()));
        }
        accountMapper.updateAccount(user);
    }

    @Override
    public void creditDown(Order order) {
        order.setRoomNum(-order.getRoomNum());
        LocalDate nowPWeek = LocalDate.now();
        User user = accountMapper.select(order.getUserId());
        int aheadLimit = user.getVipType() == VipType.Big ? 2 :
                user.getVipType() == VipType.Small ? 4 : 8;
        if (nowPWeek.plusDays(aheadLimit).isAfter(order.getCheckInDate())) {
            order.setCreditDelta(-order.getPrice() / 2);
        }
    }

    private boolean exists(User user) {
        User u = accountMapper.getAccountByEmail(user.getEmail());
        if (u != null && !u.getId().equals(user.getId())) return true;
        u = accountMapper.getAccountByUsername(user.getUsername());
        return u != null && !u.getId().equals(user.getId());
    }

    @Override
    public void vip(VipForm vipForm) {
        User user = accountMapper.select(vipForm.getUserId());
        if (user.getVipEnd().isAfter(LocalDateTime.now())) {
            user.setVipEnd(user.getVipEnd().plusDays(vipForm.getDay()));
        } else {
            user.setVipType(vipForm.getVipType());
            user.setVipEnd(LocalDateTime.now().plusDays(vipForm.getDay()));
        }
        accountMapper.updateAccount(user);
    }

    @Override
    public void creditUp(CreditUpVO creditUpVO) throws OopsException {
        CreditUp creditUp = new CreditUp();
        User user = accountMapper.getAccountByUsername(creditUpVO.getUsername());
        if (!user.getEmail().equals(creditUpVO.getEmail())) throw new OopsException(6);
        creditUp.setUserId(user.getId());
        creditUp.setCreditDelta(creditUpVO.getCreditDelta());
        accountMapper.creditUp(creditUp);
    }

}
