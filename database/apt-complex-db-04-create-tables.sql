-- ----------------------------------------------------------------------------
-- TABLE - APARTMENT
-- ----------------------------------------------------------------------------

CREATE TABLE apartment (
  id      INT         NOT NULL AUTO_INCREMENT,
  number  VARCHAR(4)  NOT NULL,
  version BIGINT      NOT NULL DEFAULT 1,

  PRIMARY KEY (id),

  UNIQUE INDEX apt_nmbr_uq (number ASC)
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
-- TABLE - BILL (CAN BE AN APARTMENT BILL OR EVEN A CONDOMINIUM CLEANING STUFF BILL)
-- ----------------------------------------------------------------------------

CREATE TABLE bill (
  id              INT       NOT NULL AUTO_INCREMENT,
  reference_date  DATETIME  NOT NULL,
  version         BIGINT    NOT NULL DEFAULT 1,

  PRIMARY KEY (id)
);

-- ----------------------------------------------------------------------------
-- ASSOCIATION TABLE - BILL PAYMENT (ALL PAYMENTS TO PAY OFF THIS DEBT)
-- ----------------------------------------------------------------------------

CREATE TABLE apartment_fee (
  apartment_id  INT NOT NULL,
  bill_id       INT NOT NULL,

  PRIMARY KEY (apartment_id, bill_id),

  CONSTRAINT fk_aprtmntf_aprtmnt
    FOREIGN KEY (apartment_id)
      REFERENCES apartment (id),

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
-- BALANCE DIVERGENCE - ALL ACCOUNTS NOT REGISTERED FOR A REFERENCED DATE (FOR
-- ANY REASON WHATSOEVER), BUT ARE "EXPLAINABLE"
-- ----------------------------------------------------------------------------

CREATE TABLE balance_divergence (
  id              INT           NOT NULL AUTO_INCREMENT,
  type            ENUM          ('CUSTOM', 'CONDOMINIUM_DISCOUNT'),
  value           DECIMAL(15,2) NOT NULL,
  reference_date  DATETIME      NOT NULL,
  description     TEXT,
  version         BIGINT        NOT NULL DEFAULT 1,

  PRIMARY KEY (id)
);

-- ----------------------------------------------------------------------------
-- ----------------------------------------------------------------------------
-- END OF FILE, COMMITTING
-- ----------------------------------------------------------------------------
-- ----------------------------------------------------------------------------
