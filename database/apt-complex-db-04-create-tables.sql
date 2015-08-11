-- ----------------------------------------------------------------------------
-- MONTHLY BALANCE
-- ----------------------------------------------------------------------------

CREATE TABLE monthly_balance (
  id          INT       NOT NULL AUTO_INCREMENT,
  year        SMALLINT  NOT NULL,
  month	      SMALLINT  NOT NULL,
  version     BIGINT    NOT NULL DEFAULT 1,

  PRIMARY KEY (id),

  INDEX mnthlblnc_yr_mnth_idx (year, month)
);

-- ----------------------------------------------------------------------------

CREATE TABLE entity_balance_group (
  id                  INT           NOT NULL AUTO_INCREMENT,
  description         VARCHAR(255),
  type                ENUM          ('INCOMES', 'PERSONNEL', 'UTILITIES', 'CONTRACTS', 'GENERAL_SERVICES', 'BANKS', 'OTHERS') NOT NULL,
  monthly_balance_id  INT           NOT NULL,
  version             BIGINT        NOT NULL DEFAULT 1,

  PRIMARY KEY (id),

  CONSTRAINT fk_enttyblncgrp_mnthlyblnc
    FOREIGN KEY (monthly_balance_id)
      REFERENCES monthly_balance (id)
);

-- ----------------------------------------------------------------------------
-- BALANCE DIVERGENCE - ALL ACCOUNTS NOT REGISTERED FOR A REFERENCED DATE (FOR
-- ANY REASON WHATSOEVER), BUT ARE "EXPLAINABLE"
-- ----------------------------------------------------------------------------

CREATE TABLE balance_divergence (
  id          INT           NOT NULL AUTO_INCREMENT,
  type        ENUM          ('FEES_PAYMENTS', 'INVESTMENT_INTERESTS', -- Condominium fee
							'PAYROLL', 'SOCIAL_CHARGES', -- Personnel
							'WATER', 'ELECTRICITY', 'TELEPHONE', -- Utilities
							'CONDO_ADMINISTRATION', 'GATE_MAINTENANCE', 'CAM_MAINTENANCE', 'ELEVATOR_MAINTENANCE', 'SECURITY', 'ENGINEERING', -- Contracts
							'BANK_RATE', -- Banks
							'CUSTOM') NOT NULL, -- Others
  value       DECIMAL(15,2) NOT NULL,
  description TEXT,
  entity_balance_group_id INT NOT NULL,
  version     BIGINT        NOT NULL DEFAULT 1,

  PRIMARY KEY (id),

  CONSTRAINT fk_blncdvrgnc_enttyblncGRP
    FOREIGN KEY (entity_balance_group_id)
      REFERENCES entity_balance_group (id)

);

-- ----------------------------------------------------------------------------
-- TABLE - APARTMENT
-- ----------------------------------------------------------------------------

CREATE TABLE apartment (
  id      INT         NOT NULL AUTO_INCREMENT,
  number  VARCHAR(4)  NOT NULL,
  version BIGINT      NOT NULL DEFAULT 1,

  PRIMARY KEY (id),

  UNIQUE INDEX apt_nmbr_uq (number)
);

-- ----------------------------------------------------------------------------

CREATE TABLE apartment_balance (
  id                      INT        NOT NULL AUTO_INCREMENT,
  entity_balance_group_id INT        NOT NULL,
  apartment_id            INT, -- Nullable, so hibernate can remove dependency with apartment before deleting it
  apartment_number        VARCHAR(4) NOT NULL,

  PRIMARY KEY (id),

  UNIQUE KEY (entity_balance_group_id, apartment_id),

  CONSTRAINT fk_aprtmntblnc_enttyblncgrp
    FOREIGN KEY (entity_balance_group_id)
      REFERENCES entity_balance_group (id),

  CONSTRAINT fk_aprtmntblnc_aprtmnt
    FOREIGN KEY (apartment_id)
      REFERENCES apartment (id)
);

-- ----------------------------------------------------------------------------
-- TABLE - BILL (CAN BE AN APARTMENT BILL OR EVEN A CONDOMINIUM CLEANING STUFF BILL)
-- ----------------------------------------------------------------------------

CREATE TABLE bill (
  id      INT    NOT NULL AUTO_INCREMENT,
  version BIGINT NOT NULL DEFAULT 1,

  PRIMARY KEY (id)
);

-- ----------------------------------------------------------------------------

CREATE TABLE bill_with_due_date (
  id        INT       NOT NULL,
  due_date  DATETIME  NOT NULL,
  version   BIGINT    NOT NULL DEFAULT 1,

  PRIMARY KEY (id),

  CONSTRAINT fk_bllwthddt_bll
    FOREIGN KEY (id)
      REFERENCES bill (id)
);

-- ----------------------------------------------------------------------------
-- ASSOCIATION TABLE - BILL PAYMENT (ALL PAYMENTS TO PAY OFF THIS DEBT)
-- ----------------------------------------------------------------------------

CREATE TABLE apartment_fee (
  apartment_balance_id  INT NOT NULL,
  bill_id               INT NOT NULL,

  PRIMARY KEY (apartment_balance_id, bill_id),

  CONSTRAINT fk_aprtmntf_aprtmntblnc
  FOREIGN KEY (apartment_balance_id)
  REFERENCES apartment_balance (id),

  CONSTRAINT fk_aprtmntf_bll
  FOREIGN KEY (bill_id)
  REFERENCES bill (id)
);

-- ----------------------------------------------------------------------------
-- TABLE - BILL ITEMS
-- ----------------------------------------------------------------------------

CREATE TABLE bill_item (
  id          INT           NOT NULL AUTO_INCREMENT,
  type        ENUM          ('CUSTOM', 'CONDOMINIUM_FEE', 'CONDOMINIUM_SURCHARGE', 'CONDOMINIUM_DISCOUNT'),
  value       DECIMAL(15,2) NOT NULL,
  description VARCHAR(255),
  bill_id     INT           NOT NULL,
  version     BIGINT        NOT NULL DEFAULT 1,

  PRIMARY KEY (id),

  CONSTRAINT fk_bllitm_bll
  FOREIGN KEY (bill_id)
  REFERENCES bill (id)
);

-- ----------------------------------------------------------------------------
-- TABLE - PERSON
-- ----------------------------------------------------------------------------

CREATE TABLE person (
  id          INT           NOT NULL AUTO_INCREMENT,
  first_name  VARCHAR(50)   NULL,
  middle_name VARCHAR(255)  NULL,
  last_name   VARCHAR(50)   NULL,
  document    VARCHAR(11)   NULL,
  version     BIGINT        NOT NULL DEFAULT 1,

  PRIMARY KEY (id)
);

-- ----------------------------------------------------------------------------
-- ASSOCIATION TABLE - RESIDENCE, THE APARTMENT RESIDENT (PERSON)
-- ----------------------------------------------------------------------------

CREATE TABLE residence (
  apartment_id  INT NOT NULL,
  person_id     INT NOT NULL,

  PRIMARY KEY (apartment_id, person_id),

  CONSTRAINT fk_rsdnc_aprtmnt
	  FOREIGN KEY (apartment_id)
		  REFERENCES apartment (id),

  CONSTRAINT fk_rsdnc_prsn
	  FOREIGN KEY (person_id)
		  REFERENCES person (id)
);


-- ----------------------------------------------------------------------------
-- TABLE - PAYMENT
-- ----------------------------------------------------------------------------

CREATE TABLE payment (
  id            INT           NOT NULL AUTO_INCREMENT,
  value         DECIMAL(15,2) NOT NULL,
  type          ENUM          ('INCOME', 'EXPENSE'),
  payment_date  DATETIME      NOT NULL,
  note          VARCHAR(255),
  version       BIGINT        NOT NULL DEFAULT 1,

  PRIMARY KEY (id)
);

-- ----------------------------------------------------------------------------
-- ASSOCIATION TABLE - BILL PAYMENT (ALL PAYMENTS TO PAY OFF THIS DEBT)
-- ----------------------------------------------------------------------------

CREATE TABLE bill_payment (
  bill_id     INT NOT NULL,
  payment_id  INT NOT NULL,

  PRIMARY KEY (bill_id, payment_id),

  CONSTRAINT fk_bllpymnt_bll
    FOREIGN KEY (bill_id)
      REFERENCES bill (id),

  CONSTRAINT fk_bllpymnt_pymnt
    FOREIGN KEY (payment_id)
      REFERENCES payment (id)
);

-- ----------------------------------------------------------------------------
-- ----------------------------------------------------------------------------
-- END OF FILE, COMMITTING
-- ----------------------------------------------------------------------------
-- ----------------------------------------------------------------------------
