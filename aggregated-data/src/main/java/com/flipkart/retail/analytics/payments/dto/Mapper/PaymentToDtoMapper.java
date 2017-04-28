package com.flipkart.retail.analytics.payments.dto.Mapper;

import com.flipkart.retail.analytics.payments.dto.response.PaymentItemsResponse;
import com.flipkart.retail.analytics.payments.dto.response.PaymentResponse;
import com.flipkart.retail.analytics.persistence.entity.Payment;
import com.flipkart.retail.analytics.persistence.entity.PaymentItem;

import java.util.stream.Collectors;

public class PaymentToDtoMapper {
    public static PaymentItemsResponse getPaymentItemsResponse (PaymentItem paymentItems)
    {
        PaymentItemsResponse paymentItemsResponse = new PaymentItemsResponse();
        paymentItemsResponse.setInvoiceAmount(paymentItems.getInvoiceAmount());
        paymentItemsResponse.setInvoiceDate(paymentItems.getInvoiceDate());
        paymentItemsResponse.setInvoiceDescription(paymentItems.getInvoiceDescription());
        paymentItemsResponse.setInvoiceId(paymentItems.getItemRefNumber());
        paymentItemsResponse.setItemNetAmount(paymentItems.getItemNetAmount());
        paymentItemsResponse.setPaymentType(paymentItems.getPaymentType());
        paymentItemsResponse.setPrepaymentAmount(paymentItems.getPrepaymentAmount());
        paymentItemsResponse.setTxnNumber(paymentItems.getTxnNumber());
        return paymentItemsResponse;
    }

    public static PaymentResponse getPaymentResponse (Payment payments)
    {

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setAmount(payments.getAmount());
        paymentResponse.setBankAccountNumber(payments.getVendorBankAccountNumber());
        paymentResponse.setBankBranchName(payments.getVendorBranchName());
        paymentResponse.setBankName(payments.getVendorBankName());
        paymentResponse.setCurrency(payments.getCurrency());
        paymentResponse.setPaymentId(payments.getRefNumber());
        paymentResponse.setPaymentMethod(payments.getPaymentMethod());
        paymentResponse.setVendorName(payments.getVendorName());
        paymentResponse.setVendorSiteId(payments.getVendorSiteId());
        paymentResponse.setPaidDate(payments.getPaiddate());
        paymentResponse.setPaymentItems(payments.getPaymentItemsList().stream().map(PaymentToDtoMapper::getPaymentItemsResponse).
                collect(Collectors.toList()));
        return paymentResponse;

    }
}
