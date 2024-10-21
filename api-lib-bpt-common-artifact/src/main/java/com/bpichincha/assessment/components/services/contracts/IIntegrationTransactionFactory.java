package com.bpichincha.assessment.components.services.contracts;

import java.io.Serializable;

public interface IIntegrationTransactionFactory {

    <T extends Serializable, R extends Serializable> IIntegrationTransactionService<T, R> factory(String transactionCode);
}