package tech.pinto.catel.user;

import tech.pinto.catel.domain.CreditEntry;
import tech.pinto.catel.domain.CreditUp;
import tech.pinto.catel.domain.User;
import tech.pinto.catel.domain.Order;
import tech.pinto.catel.user.dto.DtoGetCreditHistory;
import tech.pinto.catel.user.dto.*;
import tech.pinto.catel.util.*;
import tech.pinto.catel.util.error.CustomException;
import tech.pinto.catel.util.error.EntityNotExists;
import tech.pinto.catel.util.error.OopsException;
import tech.pinto.catel.vo.order.CreditUpVO;
import tech.pinto.catel.user.dto.DtoUserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void registerAccount(DtoRegister dtoRegister) throws OopsException {
        var user = mapX.toUser(dtoRegister);

        Optional<User> u;
        u = repoUser.findByEmail(dtoRegister.getEmail());
        if (u.isPresent()) throw new OopsException(1);
        u = repoUser.findByUsername(dtoRegister.getUsername());
        if (u.isPresent()) throw new OopsException(1);

        repoUser.save(user);
    }

    public DtoUserInfo login(DtoLogin dtoLogin) throws OopsException {
        var user = repoUser.findByEmail(dtoLogin.getEmail());
        if (user.isEmpty()) user = repoUser.findByUsername(dtoLogin.getEmail());
        if (user.isEmpty()) {
            throw new OopsException(3);
        }
        if (
            !dtoLogin.getPassword().equals(user.get().getPassword())
        ) {
            throw new OopsException(3);
        }
        return mapX.toInfo(user.get());
    }

    public DtoUserInfo info(long id) throws CustomException {
        var user = repoUser.findById(id);
        if (user.isEmpty()) throw new EntityNotExists("用户");
        return mapX.toInfo(user.get());
    }

    public void updateInfo(DtoUserUpdate dtoUserUpdate) throws OopsException {
        var user = repoUser.getById(dtoUserUpdate.getId());
        if (!user.getUsername().equals(dtoUserUpdate.getName())) {
            var other = repoUser.findByUsername(dtoUserUpdate.getName());
            if (other.isPresent()) throw new OopsException(1);
            user.setUsername(dtoUserUpdate.getName());
        }

        if (dtoUserUpdate.getEmail() != null) {
            user.setEmail(dtoUserUpdate.getEmail());
        }

        if (dtoUserUpdate.getAvatar() != null) {
            user.setAvatar(dtoUserUpdate.getAvatar());
        }

        repoUser.save(user);
    }

    public void creditPunish(Order order) {
        var user = order.getUser();
        LocalDate today = LocalDate.now();
        var aheadLimit = user.getVipLevel().getAnnulLimit();
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

    public List<DtoCreditEntry> creditHistory(DtoGetCreditHistory dtoGetCreditHistory) {
        var r = repoCreditEntry.findAllByUserId(dtoGetCreditHistory.getUserId());
        return r
            .stream()
            .map(mapX::toDtoEntry)
            .collect(Collectors.toList());
    }

    public void vip(DtoUserVip dtoUserVip) {
        var user = repoUser.getById(dtoUserVip.getId());
        if (user.getVipEnd().isAfter(LocalDateTime.now())) {
            user.setVipEnd(user.getVipEnd().plusDays(dtoUserVip.getDays()));
        } else {
            user.setVipLevel(dtoUserVip.getLevel());
            user.setVipEnd(LocalDateTime.now().plusDays(dtoUserVip.getDays()));
        }
        repoUser.save(user);
    }

    public void creditUp(CreditUpVO creditUpVO) throws OopsException {
        CreditUp creditUp = new CreditUp();
        User user = accountMapper.getAccountByUsername(creditUpVO.getUsername());
        if (!user.getEmail().equals(creditUpVO.getEmail())) throw new OopsException(6);
        creditUp.setUserId(user.getId());
        creditUp.setCreditDelta(creditUpVO.getCreditDelta());
        accountMapper.creditUp(creditUp);
    }

    public void changePassword(DtoChangePwd dtoChangePwd) throws OopsException {
        var user = repoUser.getById(dtoChangePwd.getId());
        System.out.println(dtoChangePwd);
        System.out.println(user);
        if (!user.getPassword().equals(dtoChangePwd.getOldPass())) {
            throw new OopsException(14);
        }
        user.setPassword(dtoChangePwd.getNewPass());
        repoUser.save(user);
    }

}
