package base;

import java.math.BigDecimal;

/**
 * Here be dragons Created by @author Ezio on 2019-01-28 15:35
 */
public interface BankService {

    void transfer(int fromId, int toId, BigDecimal amount);

}
