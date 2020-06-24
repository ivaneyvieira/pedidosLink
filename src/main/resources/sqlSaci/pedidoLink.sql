DROP TABLE IF EXISTS sqldados.T_EMP;
CREATE TEMPORARY TABLE sqldados.T_EMP (
  PRIMARY KEY (empno)
)
SELECT no                                                            AS empno,
       name                                                          AS empName,
       sname                                                         AS empSname,
       trim(cast(CONCAT(CHAR(ascii(mid(pswd, 1, 1)) - 5), CHAR(ascii(mid(pswd, 2, 1)) - 7),
			CHAR(ascii(mid(pswd, 3, 1)) - 8), CHAR(ascii(mid(pswd, 4, 1)) - 0),
			CHAR(ascii(mid(pswd, 5, 1)) - 34), CHAR(ascii(mid(pswd, 6, 1)) - 9),
			CHAR(ascii(mid(pswd, 7, 1)) - 9),
			CHAR(ascii(mid(pswd, 8, 1)) - 13)) AS CHAR)) AS senha
FROM sqldados.emp
WHERE pswd <> '';

DROP TEMPORARY TABLE IF EXISTS sqldados.TPED;
CREATE TEMPORARY TABLE sqldados.TPED (
  PRIMARY KEY (storeno, ordno)
)
SELECT 'Link de pagamento loja ENGECOPI Ped MF'                                         AS note,
       eord.storeno,
       eord.ordno,
       CONCAT(eord.nfno, '/', eord.nfse)                                                AS NFiscal,
       CONCAT(eord.nfno_futura, '/', eord.nfse_futura)                                  AS NF_Fat,
       eord.amount                                                                      AS valor,
       IF(eord.other = 0, MID(eordrk.remarks__480, 7, 10), eord.other) / 100            AS frete,
       MID(eordrk.remarks__480, 160, 10)                                                AS CARTAO,
       (eord.amount + IF(eord.other = 0, (MID(eordrk.remarks__480, 7, 2) * 100), eord.other)) /
       100                                                                              AS total,
       CONCAT(paym.no, '-', paym.sname)                                                 AS MET,
       LPAD(emp.empSname, 10, ' ')                                                      AS VENDEDOR,
       IF(custp.auxString2 = 'J', LPAD(custp.tel, 9, ' '), LPAD(custp.celular, 9, ' ')) AS WHATSAPP,
       ifnull(custp.name, '*')                                                          AS CLIENTE,
       custp.no                                                                         AS CODCLI,
       eord.amount / 100                                                                AS amount,
       emp.senha                                                                        AS senha
FROM sqldados.eord
  LEFT JOIN sqldados.eoprd
	      ON (eoprd.storeno = eord.storeno AND eoprd.ordno = eord.ordno)
  LEFT JOIN sqldados.prd
	      ON (prd.no = eoprd.prdno)
  LEFT JOIN sqldados.custp
	      ON (custp.no = eord.custno)
  LEFT JOIN sqldados.eordrk
	      ON (eordrk.storeno = eord.storeno AND eordrk.ordno = eord.ordno)
  LEFT JOIN sqldados.paym
	      ON (paym.no = eord.paymno)
  LEFT JOIN sqldados.T_EMP AS emp
	      ON (emp.empno = eord.empno)
WHERE (eord.storeno = :storeno OR :storeno = 0)
  AND paym.sname LIKE '%LINK%'
  AND (eord.date >= :data)
GROUP BY eord.storeno, eord.ordno;

DROP TABLE IF EXISTS sqldados.TTEF;
CREATE TEMPORARY TABLE sqldados.TTEF (
  PRIMARY KEY (storeno, ordno)
)
SELECT MID(PEDIDO, 1, 2)       AS loja,
       S.no                    AS storeno,
       MID(PEDIDO, 4, 100) * 1 AS ordno,
       PEDIDO                  AS PEDIDO,
       SUM(VALOR / 100)        AS VALOR,
       PARCELAS                AS PARCELAS,
       NOMEAUTORIZADORA        AS AUTORIZADORA,
       AUTORIZACAO             AS AUTORIZACAO,
       NSUHOST                 AS NSUHOST,
       cast(CONCAT(MID(DATACRIACAO, 7, 4), MID(DATACRIACAO, 4, 2), MID(DATACRIACAO, 1, 2)) *
	    1 AS DATE)         AS DATACRIACAO
FROM sqldados.engecopi_tef_bruto AS B
  INNER JOIN sqldados.store      AS S
	       ON S.sname = MID(PEDIDO, 1, 2)
WHERE nsu > date_format(:data, '%y%m%d') * 1000000000
  AND STATUS = 'CON'
  AND (S.no = :storeno OR :storeno = 0)
GROUP BY PEDIDO;

SELECT P.storeno                                             AS loja,
       P.ordno                                               AS numPedido,
       IF(P.date = 0, NULL, cast(P.date AS DATE))            AS dataPedido,
       SEC_TO_TIME(P.l4)                                     AS horaPedido,
       P.paymno                                              AS metodo,
       ifnull(cast(IFNULL(N.nfno, F.nfno) AS CHAR), '')      AS nfnoNota,
       IFNULL(IFNULL(N.nfse, F.nfse), '')                    AS nfseNota,
       if(IFNULL(N.issuedate, F.issuedate) = 0, NULL,
	  cast(IFNULL(N.issuedate, F.issuedate) AS DATE))    AS dataNota,
       sec_to_time(IFNULL(N2.auxLong4, F2.auxLong4))         AS horaNota,
       IFNULL(U.name, '')                                    AS username,
       cast(if(P.l15 = 0, NULL, P.l15) AS DATE)              AS dataLink,
       IF(P.l16 = 0, cast(NULL AS TIME), sec_to_time(P.l16)) AS horaLink,
       note                                                  AS nota,
       frete                                                 AS valorFrete,
       IF(frete IS NULL, TPED.amount, total)                 AS total,
       IFNULL(T.VALOR, 0.00)                                 AS valorLink,
       cartao                                                AS cartao,
       cast(WHATSAPP AS CHAR)                                AS whatsapp,
       CLIENTE                                               AS cliente,
       VENDEDOR                                              AS vendedor,
       P.status                                              AS status,
       IF(T.PEDIDO IS NULL, 'N', 'S')                        AS confirmado,
       IFNULL(senha, '')                                     AS senhaVendedor,
       P.c1                                                  AS marca,
       PARCELAS                                              AS parcelas,
       AUTORIZADORA                                          AS autorizadora,
       AUTORIZACAO                                           AS autorizacao,
       NSUHOST                                               AS nsuHost,
       DATACRIACAO                                           AS dataTef
FROM sqldados.eord          AS P
  LEFT JOIN  sqldados.TTEF  AS T
	       USING (storeno, ordno)
  LEFT JOIN  sqlpdv.pxa     AS PX
	       ON (P.storeno = PX.storeno AND P.ordno = PX.eordno)
  INNER JOIN sqldados.custp AS C
	       ON C.no = P.custno
  LEFT JOIN  sqldados.nf    AS N
	       ON N.storeno = P.storeno AND N.nfno = P.nfno AND N.nfse = P.nfse
  LEFT JOIN  sqldados.nf2   AS N2
	       ON N.storeno = N2.storeno AND N.pdvno = N2.pdvno AND N.xano = N2.xano
  LEFT JOIN  sqldados.nf    AS F
	       ON F.storeno = P.storeno AND F.nfno = P.nfno_futura AND F.nfse = P.nfse_futura
  LEFT JOIN  sqldados.nf2   AS F2
	       ON F.storeno = F2.storeno AND F.pdvno = F2.pdvno AND F.xano = F2.xano
  INNER JOIN sqldados.users AS U
	       ON U.no = P.userno
  INNER JOIN sqldados.paym  AS PM
	       ON PM.no = P.paymno
  INNER JOIN sqldados.TPED
	       ON TPED.storeno = P.storeno AND TPED.ordno = P.ordno
WHERE PM.sname LIKE '%LINK%'
  AND P.date >= :data
  AND P.status <> 5
  AND (P.storeno = :storeno OR :storeno = 0)
GROUP BY P.ordno, P.storeno

