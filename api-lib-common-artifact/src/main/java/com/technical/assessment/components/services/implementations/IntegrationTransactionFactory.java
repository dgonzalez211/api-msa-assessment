package com.technical.assessment.components.services.implementations;

import com.technical.assessment.components.exceptions.TransactionFactoryException;
import com.technical.assessment.components.services.contracts.IIntegrationTransactionFactory;
import com.technical.assessment.components.services.contracts.IIntegrationTransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.technical.assessment.constants.ValidationConstants.BYPASS_SONAR_FIELD_UNCHECKED;

@Log4j2
@Component
@SuppressWarnings(BYPASS_SONAR_FIELD_UNCHECKED)
public class IntegrationTransactionFactory implements IIntegrationTransactionFactory {

    private final BeanFactory beanFactory;

    public IntegrationTransactionFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <T extends Serializable, R extends Serializable> IIntegrationTransactionService<T, R> factory(String transactionCode) {

        try{
            return  (IIntegrationTransactionService<T, R>) beanFactory.getBean(transactionCode, IIntegrationTransactionService.class);
        }catch (Exception e){
            log.error("error in process IntegrationTransactionFactory, error: {}", e.getMessage());
            throw new TransactionFactoryException(
                    "error in process IntegrationTransactionFactory, the ITransactionService not exist by transactionCode " + transactionCode
            );
        }
    }
}