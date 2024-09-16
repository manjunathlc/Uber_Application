package com.jsp.uber.strategies;

import com.jsp.uber.entites.enums.PaymentMethod;
import com.jsp.uber.strategies.impl.CashPaymentStrategy;
import com.jsp.uber.strategies.impl.WalletPaymentStrategyImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private final WalletPaymentStrategyImpl walletPaymentStrategyImpl;
    private final CashPaymentStrategy cashPaymentStrategyImpl;

    public PaymentStrategy getPaymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategyImpl;
            case CASH -> cashPaymentStrategyImpl;
//            default -> throw new RuntimeException("Invalid payment Method " + paymentMethod);
        };

    }
}
