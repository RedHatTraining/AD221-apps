package com.redhat.training.payments.transaction;

import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PaymentsTransactionPolicy {
    // TODO: Create a transaction management policy
    @Bean(name = "PROPAGATION_REQUIRED")
    public SpringTransactionPolicy propagationRequired(PlatformTransactionManager transactionManager) {
        SpringTransactionPolicy propagationRequired = new SpringTransactionPolicy();

        propagationRequired.setTransactionManager(transactionManager);
        propagationRequired.setPropagationBehaviorName("PROPAGATION_REQUIRED");

        return propagationRequired;
    }
}
