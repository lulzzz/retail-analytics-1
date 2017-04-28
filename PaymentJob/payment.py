#!/usr/bin/env python

import requests
import simplejson as json
import  MySQLdb
from datetime import datetime, timedelta

conn = MySQLdb.connect(host="localhost",user="root",passwd="", db="aggregation_pipeline")
c = conn.cursor()


def getPaymentByPaymentId(paymentId):
    query = """SELECT id ,status FROM payments WHERE ref_number = %s """%(paymentId)
    c.execute(query)
    results = c.fetchall()
    if(len(results)==0):
        return results
    return results[0]


def getPaymentByPaymentIdAndInvoiceId(paymentId,invoiceId):
    query = """SELECT id FROM payment_items WHERE payment_id = %s AND invoice_id = %s """
    c.execute(query ,(paymentId,invoiceId))
    results = c.fetchall()
    if(len(results)==0):
        return results
    return results[0]

def hash(paymentItem):

    paymentItemHash = {'vendorSiteId':None,'vendorBankAccountNumber':None,'vendorBankName':None,'invoiceId':None,'paymentId':None,'paymentNumber':None,
                       'paymentDate':None,'paymentAmount':None,'vendorInvoiceId':None,'invoiceAmount':None,'invoiceDate':None,'invoiceDescription':None,
                       'paymentInvoiceAmount':None,'currency':None,'paymentMethod':None,'prepayAmount':None,'paymentType':None,
                       'itemRefNumber':None,'txnNumber':None,'status':None,'vendorBankBranchName':None,'vendorName':None}
    for key in paymentItemHash:
        if key in paymentItem:
            paymentItemHash[key] = paymentItem[key]
    return paymentItemHash



def insertIntoPaymentItems(paymentId,paymentItem):

    invoiceDate = str(paymentItem["invoiceDate"])
    itemRefNumber = None
    if paymentItem["itemRefNumber"] !=None:
        itemRefNumberlist = str(paymentItem["itemRefNumber"]).split("_")
        itemRefNumber = itemRefNumberlist[0]
    invoiceDate = datetime.strptime(invoiceDate,'%d-%b-%y')
    query = """ INSERT INTO payment_items (payment_id, item_net_amount, invoice_amount,invoice_date,item_ref_number,txn_number,
                                            invoice_description,invoice_id,prepayment_amount, payment_type, vendor_invoice_id,created_at)
                                            VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,NOW())"""
    c.execute(query,(paymentId,paymentItem["paymentInvoiceAmount"],paymentItem["invoiceAmount"],invoiceDate,itemRefNumber,paymentItem["txnNumber"],paymentItem["invoiceDescription"],paymentItem["invoiceId"],paymentItem["prepayAmount"],paymentItem["paymentType"],paymentItem["vendorInvoiceId"]))



def insertIntoPayments(paymentItem):
    paidDate = str(paymentItem["paymentDate"])
    paidDate = datetime.strptime(paidDate,'%d-%b-%y')
    #print paymentItem["status"],paymentItem["paymentId"], "---------into insertion"
    queryforPayment = """INSERT INTO payments (paid_date,ref_number,amount,payment_method,payment_number,vendor_bank_name,vendor_site_id,vendor_bank_account_number,currency, status, vendor_branch_name, vendor_name, created_at) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,NOW())"""
    c.execute(queryforPayment,(paidDate,paymentItem["paymentId"],paymentItem["paymentAmount"],paymentItem["paymentMethod"],paymentItem["paymentNumber"],paymentItem["vendorBankName"],paymentItem["vendorSiteId"],paymentItem["vendorBankAccountNumber"],paymentItem["currency"],paymentItem["status"],paymentItem["vendorBankBranchName"],paymentItem["vendorName"]))


def updatePayment(paymentItem,idForPayment):
    paidDate = str(paymentItem["paymentDate"])
    paidDate = datetime.strptime(paidDate,'%d-%b-%y')
    #print paymentItem["status"],paymentItem["paymentId"],"-----------into updation"
    query ="""UPDATE payments set paid_date = %s , ref_number = %s , amount = %s, payment_method = %s, payment_number = %s , vendor_bank_name = %s , vendor_site_id = %s, vendor_bank_account_number = %s, currency = %s, status = %s, vendor_branch_name = %s, vendor_name = %s, updated_at = NOW() WHERE id = %s """
    c.execute(query,(paidDate,paymentItem["paymentId"],paymentItem["paymentAmount"],paymentItem["paymentMethod"],paymentItem["paymentNumber"],paymentItem["vendorBankName"],paymentItem["vendorSiteId"],paymentItem["vendorBankAccountNumber"],paymentItem["currency"],paymentItem["status"],paymentItem["vendorBankBranchName"],paymentItem["vendorName"],idForPayment))


def insertIntoAggregatedPayments(vendorSiteId,currency,paidDate,paymentAmount):
    paymentAmount = float(paymentAmount)
    paymentAmount = round(paymentAmount,2)
    query = """ INSERT INTO aggregated_payments (vendor_site_id,month,amount,currency,created_at)VALUES(%s,%s,%s,%s,NOW()) """
    c.execute(query,(vendorSiteId,paidDate,paymentAmount,currency))

def updateAggregatedPipeline(aggregatedPaymentsId,paymentAmount):
    paymentAmount = float(paymentAmount)
    paymentAmount = round(paymentAmount,2)
    query =""" UPDATE aggregated_payments set amount = %s , updated_at = NOW() where  id = %s"""
    c.execute(query, (paymentAmount,aggregatedPaymentsId))

def insertIntoJobLogs(startDate,endDate ,processed):
    query = """INSERT INTO job_logs (job_id, start_date_param, end_date_param, processed, created_at)VALUES("fetch_payments", %s , %s , %s, NOW())"""
    c.execute(query, (startDate,endDate , processed))


def insertToPayments(paymentItem):
    payment = getPaymentByPaymentId(paymentItem["paymentId"])
    status = paymentItem["status"]
    if(len(payment)==0):
        try:
            insertIntoPayments(paymentItem)
            payment = getPaymentByPaymentId(paymentItem["paymentId"])
            if status =="ISSUED":
                isAmountIngested = aggregatePayments(paymentItem,True)
                if(not(isAmountIngested)):
                    return False
        except Exception as e:
            print("exception in creation into payment")
            print e.message, e.args
            print("------------------------------------------------------------------------------------------------")
            return False
    else:
        try:
            updatePayment(paymentItem,payment[0])
            if status =="VOID" and payment[1] == "ISSUED":
                aggregatePayments(paymentItem,False)
                if(not(isAmountIngested)):
                    return False
        except Exception as e:
            print("exception in updation into payments")
            print e.message, e.args
            print("----------------------------------------------------------------------------------------------")
            return False
    try:
        insertIntoPaymentItems(payment[0],paymentItem)
    except Exception as e:
        print("exception in creation into payment items")
        print e.message, e.args
        print("------------------------------------------------------------------------------------------------")
        return False
    return True

def getIdByVendorSiteCurrencyAndMonth(vendorSite,currency,month):

    query = """SELECT id , amount FROM aggregated_payments WHERE vendor_site_id = %s AND month = %s AND currency = %s """
    c.execute(query , (vendorSite,month,currency))
    results = c.fetchall()
    if(len(results)==0):
        return results
    return results[0]

def getYearAndMonth(date):
    paidDate = str(date)
    paidDate = datetime.strptime(paidDate,'%d-%b-%y')
    paidDate = paidDate.strftime('%Y-%m-%d')
    paidDate = paidDate.split("-")
    paymentDate = paidDate[0]+"-"+paidDate[1]
    return paymentDate

def aggregatePayments(paymentItem,isStatusIssued):
    paymentDate = getYearAndMonth(paymentItem["paymentDate"])
    vendorSiteId = paymentItem["vendorSiteId"]
    currency = paymentItem["currency"]
    paymentAmount = float(paymentItem["paymentAmount"])
    if not(isStatusIssued):
        paymentAmount = -paymentAmount
    vendorSiteCurrencyAndMonth = getIdByVendorSiteCurrencyAndMonth(vendorSiteId,currency,paymentDate)
    if (len(vendorSiteCurrencyAndMonth) == 0):
        try:
            insertIntoAggregatedPayments(vendorSiteId,currency,paymentDate,paymentAmount)
        except Exception as e:
            print("exception in creation into aggregated payments")
            print e.message, e.args
            print("------------------------------------------------------------------------------------------------")
            return False
    else:
        try:
            aggregatedPaymentsId = vendorSiteCurrencyAndMonth[0]
            amount = float(vendorSiteCurrencyAndMonth[1])
            amount = amount + paymentAmount
            updateAggregatedPipeline(aggregatedPaymentsId,amount)
        except Exception as e:
            print("exception in updation into aggregated payments")
            print e.message, e.args
            return False
    return True

def getPaymentItems(paymentItems):
    for paymentItem in paymentItems:
        try:
            if not(str(paymentItem["vendorSiteId"]).startswith("VS")):
                continue
            paymentItemHash = hash(paymentItem)
            isPaymentsInserted = insertToPayments(paymentItemHash)
            if(not(isPaymentsInserted)):
                return False
        except Exception as e:
            print ("error in insertion")
            print e.message, e.args
            return False
    return True;



def getFlatPayment(startDate,endDate):
    uri = "http://10.85.185.171:27660/ofiFeedback/PaymentService/getFlatPaymentItemsForDateRange?fromDate=2015-03-01&toDate=2015-05-01"
    try:
        response = requests.get(uri)
        if(response.status_code == 200):
            payment = json.loads(response.text)
            if payment["FlatPayments"] == "":
                return True
            return getPaymentItems(payment["FlatPayments"]['flatPaymentItemsList'])

        else:
            print("fail to fetch payments data")
            return False
    except Exception as e:
        print("excpetion in hitting getFlatPayment  for payments data")
        print e.message, e.args
        return False

def main():

    queryForStartDate = """SELECT end_date_param from job_logs where job_id = 'fetch_payments' and processed = 1 Order BY end_date_param DESC LIMIT 1 """
    c.execute(queryForStartDate)
    startDate = (c.fetchone())[0]
    endDate = datetime.strftime(datetime.now(), '%Y-%m-%d')
    isDataIngested = getFlatPayment(startDate,endDate)
    if isDataIngested:
        try :
            insertIntoJobLogs(startDate,endDate , 1)
            conn.commit()
        except Exception as e:
            print ("error in inserting into job logs")
            print e.message, e.args
            return False
    else:
        try :
            conn.rollback()
            insertIntoJobLogs(startDate,endDate , 0)
            conn.commit()
        except Exception as e:
            print ("error in inserting into job logs")
            print e.message, e.args
            return False

main()

c.close()
conn.close()
