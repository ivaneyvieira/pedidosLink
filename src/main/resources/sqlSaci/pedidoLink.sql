SELECT P.storeno                                            AS loja,
       P.ordno                                              AS numPedido,
       IF(P.date = 0, NULL, cast(P.date AS DATE))           AS dataPedido,
       SEC_TO_TIME(P.l4)                                    AS horaPedido,

       P.paymno                                             AS metodo,

       IFNULL(cast(N.nfno AS CHAR), '')                     AS nfnoNota,
       IFNULL(N.nfse, '')                                   AS nfseNota,
       if(N.issuedate = 0, NULL, cast(N.issuedate AS DATE)) AS dataNota,
       sec_to_time(N2.auxLong4)                             AS horaNota,
       IFNULL(U.name, '')                                   AS username,
       cast(if(P.l15 = 0, NULL, P.l15) AS DATE)             AS dataLink,
       cast(if(P.l16 = 0, NULL, P.l16) AS TIME)             AS horaLink
FROM sqldados.eord          AS P
  LEFT JOIN  sqlpdv.pxa     AS PX
	       ON (P.storeno = PX.storeno AND P.ordno = PX.eordno)
  INNER JOIN sqldados.custp AS C
	       ON C.no = P.custno
  LEFT JOIN  sqldados.nf    AS N
	       ON N.storeno = P.storeno AND N.nfno = P.nfno AND N.nfse = P.nfse
  LEFT JOIN  sqldados.nf2   AS N2
	       ON N.storeno = N2.storeno AND N.pdvno = N2.pdvno AND N.xano = N2.xano
  INNER JOIN sqldados.users AS U
	       ON U.no = P.userno
  INNER JOIN sqldados.paym  AS PM
	       ON PM.no = P.paymno
WHERE PM.sname LIKE '%LINK%'
  AND P.date >= 20200608
  AND P.status <> 5
  AND (P.storeno = :storeno OR :storeno = 0)
GROUP BY P.ordno, P.storeno

