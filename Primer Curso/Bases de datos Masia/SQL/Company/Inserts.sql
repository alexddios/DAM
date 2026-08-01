INSERT into CO_PROVINCE(CODE,NAME,TOTAL_AMOUNT)
VALUES ('IB','Islas Baleares',1250000);

INSERT into CO_VENDOR(CODE,NAME)
VALUES (001,'Alfonso');

INSERT INTO CO_CUSTOMER (
    DNI, 
    NAME, 
    TYPE, 
    REGISTER_DATE, 
    TOTAL_PURCHASES, 
    PROVINCE_CODE, 
    VENDOR_CODE
)
VALUES (
    '10101010',          
    'Valls Mas Àngel',    
    'AA',                  
    TO_DATE('02/08/03','DD/MM/YY'), 
    100000,               
    'IB',                 
    001                    
);
COMMIT;