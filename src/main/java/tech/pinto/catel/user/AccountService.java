package tech.pinto.catel.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import tech.pinto.catel.domain.CreditEntry;
import tech.pinto.catel.domain.CreditUp;
import tech.pinto.catel.domain.User;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.user.dto.DtoUserInfo;
import tech.pinto.catel.util.MapX;
import tech.pinto.catel.util.OopsException;
import tech.pinto.catel.util.OssService;
import tech.pinto.catel.vo.order.CreditUpVO;
import tech.pinto.catel.vo.user.UserForm;
import tech.pinto.catel.vo.user.UserInfo;
import tech.pinto.catel.vo.user.VipForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountMapper accountMapper;
    private final OssService ossService;
    private final RepoUser repoUser;
    private final RepoCreditEntry repoCreditEntry;
    private final MapX mapX;

    @Autowired
    public AccountService(AccountMapper accountMapper, OssService ossService, RepoUser repoUser, RepoCreditEntry repoCreditEntry, MapX mapX) {
        this.accountMapper = accountMapper;
        this.ossService = ossService;
        this.repoUser = repoUser;
        this.repoCreditEntry = repoCreditEntry;
        this.mapX = mapX;
    }

    public void registerAccount(User user) throws OopsException {
        if (exists(user)) throw new OopsException(1);
        accountMapper.createNewAccount(user);
    }

    public DtoUserInfo login(UserForm userForm) throws OopsException {
        var user = repoUser.findByEmail(userForm.getEmail());
        if (user == null) user = repoUser.findByUsername(userForm.getEmail());
        
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            throw new OopsException(3);
        }
        return mapX.toInfo(user);
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

    public UserInfo getUserInfo(int id) throws OopsException {
        User user = getUserById(id);
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(user, userInfo, CopyOptions.create().ignoreNullValue());
        return userInfo;
    }

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

    public void creditPunish(Order order) {
        var user = order.getUser();
        LocalDate today = LocalDate.now();
        var aheadLimit = user.getVipType().getAnnulLimit();
        var tooLate = today.plusDays(aheadLimit).isAfter(order.getCheckInDate());

        if (tooLate) {
            var entry = new CreditEntry();
            entry.setUser(user);
            entry.setOrder(order);
            var delta = -1.5 * order.getPrice().doubleValue();
            entry.setDelta(delta);
            repoCreditEntry.save(entry);
        }
    }

    private boolean exists(User user) {
        User u = accountMapper.getAccountByEmail(user.getEmail());
        if (u != null && !u.getId().equals(user.getId())) return true;
        u = accountMapper.getAccountByUsername(user.getUsername());
        return u != null && !u.getId().equals(user.getId());
    }

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

    public void creditUp(CreditUpVO creditUpVO) throws OopsException {
        CreditUp creditUp = new CreditUp();
        User user = accountMapper.getAccountByUsername(creditUpVO.getUsername());
        if (!user.getEmail().equals(creditUpVO.getEmail())) throw new OopsException(6);
        creditUp.setUserId(user.getId());
        creditUp.setCreditDelta(creditUpVO.getCreditDelta());
        accountMapper.creditUp(creditUp);
    }

}
