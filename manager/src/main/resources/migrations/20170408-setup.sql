--liquibase formatted sql

--changeset rajnishkumar.s:1

-- Create syntax for TABLE 'aggregated_payments'
-- Create syntax for TABLE 'aggregated_payments'
CREATE TABLE `aggregated_payments` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vendor_site_id` varchar(255) DEFAULT NULL,
  `month` varchar(255) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'current_fact_table'
CREATE TABLE `current_fact_table` (
  `report_name` varchar(100) NOT NULL,
  `active_table` varchar(100) NOT NULL,
  `inactive_table` varchar(100) NOT NULL,
  PRIMARY KEY (`report_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'DATABASECHANGELOGLOCK'
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'irn_aggr'
CREATE TABLE `irn_aggr` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vs_id` varchar(50) DEFAULT NULL,
  `fk_warehouse` varchar(50) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `total_invoice_amount` double DEFAULT NULL,
  `total_qty` int(11) DEFAULT NULL,
  `received_qty` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'irn_aggr_old'
CREATE TABLE `irn_aggr_old` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vs_id` varchar(50) DEFAULT NULL,
  `fk_warehouse` varchar(50) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `total_invoice_amount` double DEFAULT NULL,
  `total_qty` int(11) DEFAULT NULL,
  `received_qty` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'payment_items'
CREATE TABLE `payment_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_id` bigint(20) DEFAULT NULL,
  `item_net_amount` float DEFAULT NULL,
  `item_ref_number` varchar(255) DEFAULT NULL,
  `txn_number` varchar(255) DEFAULT NULL,
  `invoice_amount` float DEFAULT NULL,
  `invoice_date` datetime DEFAULT NULL,
  `invoice_description` varchar(255) DEFAULT NULL,
  `invoice_id` varchar(255) DEFAULT NULL,
  `prepayment_amount` float DEFAULT NULL,
  `credit_amount` float DEFAULT NULL,
  `tds_amount` float DEFAULT NULL,
  `vendor_invoice_id` varchar(255) DEFAULT NULL,
  `payment_type` varchar(255) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1080797 DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'payments'
CREATE TABLE `payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paid_date` datetime DEFAULT NULL,
  `ref_number` varchar(255) DEFAULT NULL,
  `payment_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `vendor_bank_name` varchar(255) DEFAULT NULL,
  `vendor_branch_name` varchar(255) DEFAULT NULL,
  `vendor_name` varchar(255) DEFAULT NULL,
  `vendor_site_id` varchar(255) DEFAULT NULL,
  `vendor_bank_account_number` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_payments_on_ref_number` (`ref_number`),
  KEY `index_payments_on_status` (`status`),
  KEY `index_payments_on_vendor_site_id` (`vendor_site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=227259 DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'purchase_order_aggr'
CREATE TABLE `purchase_order_aggr` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vs_id` varchar(50) DEFAULT NULL,
  `fsn` varchar(50) DEFAULT NULL,
  `fk_warehouse` varchar(150) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `received_quantity` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'purchase_order_aggr_old'
CREATE TABLE `purchase_order_aggr_old` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vs_id` varchar(50) DEFAULT NULL,
  `fsn` varchar(50) DEFAULT NULL,
  `fk_warehouse` varchar(150) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `received_quantity` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'return_order_aggr'
CREATE TABLE `return_order_aggr` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vs_id` varchar(50) DEFAULT NULL,
  `fsn` varchar(50) DEFAULT NULL,
  `fk_warehouse` varchar(150) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `roi_status` varchar(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `lost_quantity` int(11) DEFAULT NULL,
  `processed_quantity` double DEFAULT NULL,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'return_order_aggr_old'
CREATE TABLE `return_order_aggr_old` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `vs_id` varchar(50) DEFAULT NULL,
  `fsn` varchar(50) DEFAULT NULL,
  `fk_warehouse` varchar(150) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `roi_status` varchar(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `lost_quantity` int(11) DEFAULT NULL,
  `processed_quantity` double DEFAULT NULL,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114288 DEFAULT CHARSET=utf8;

INSERT INTO `current_fact_table` (`report_name`, `active_table`, `inactive_table`)
VALUES
	('irn_aggr_report', 'irn_aggr', 'irn_aggr_old'),
	('purchase_order_report', 'purchase_order_aggr', 'purchase_order_aggr_old'),
	('return_order_report', 'return_order_aggr', 'return_order_aggr_old');


--rollback drop table aggregated_payments,DATABASECHANGELOGLOCK,irn_aggr,payment_items,payments,purchase_order_aggr,return_order_aggr,return_order_aggr,return_order_aggr_old,;