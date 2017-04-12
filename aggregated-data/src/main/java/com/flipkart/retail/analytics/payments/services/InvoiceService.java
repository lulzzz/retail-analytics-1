package com.flipkart.retail.analytics.payments.services;

import com.flipkart.retail.analytics.payments.dto.Mapper.PaymentToDtoMapper;
import com.flipkart.retail.analytics.payments.dto.response.PaymentIdListResponse;
import com.flipkart.retail.analytics.payments.dto.response.PaymentListResponse;
import com.flipkart.retail.analytics.persistence.PaymentItemsPersistenceManager;
import com.flipkart.retail.analytics.persistence.entity.Payment;
import com.flipkart.retail.analytics.persistence.entity.PaymentItem;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kushagra.gupta on 11/04/17.
 */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class InvoiceService {

    private final PaymentItemsPersistenceManager paymentItemsPersistenceManager;

    @Transactional
    public PaymentIdListResponse getPayments(String invoiceId) //throws AuthServiceException
    {
        List<PaymentItem> paymentItemsList = paymentItemsPersistenceManager.getAllPaymentIds(invoiceId);
        if(paymentItemsList.size() == 0)
        {
           // throw new AuthServiceException(ErrorCodes.INVALID_INVOICE_ID);
        }
        PaymentIdListResponse paymentIdListResponse = new PaymentIdListResponse();
        paymentIdListResponse.setPaymentIds(paymentItemsList.stream().map(i -> i.getPayments().getRefNumber()).collect(Collectors.toList()));
        return paymentIdListResponse;
    }

    @Transactional
    public PaymentListResponse getPaymentsDetails(String invoiceId) //throws AuthServiceException
    {
        List<PaymentItem> paymentItemsList = paymentItemsPersistenceManager.getAllPaymentIds(invoiceId);
        if(paymentItemsList.size() == 0)
        {
            //throw new AuthServiceException(ErrorCodes.INVALID_INVOICE_ID);
        }
        List<Payment> paymentsList = paymentItemsList.stream().map(i->i.getPayments()).collect(Collectors.toList());
        PaymentListResponse paymentListResponse = new PaymentListResponse();
        paymentListResponse.setPayments(paymentsList.stream().map(PaymentToDtoMapper::getPaymentResponse).collect(Collectors.toList()));
        return paymentListResponse;

    }

}

