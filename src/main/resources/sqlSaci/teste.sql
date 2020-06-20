DROP TABLE IF EXISTS T;
CREATE TEMPORARY TABLE T
SELECT R.nossoNumero,
       nome,
       R.documento                 AS documento,
       R.chave                     AS usoEmpresa,
       R.banco,
       R.agencia,
       R.carteira,
       cast(cast(
	   CONCAT('20', MID(dtOcorrencia, 5, 2), MID(dtOcorrencia, 3, 2), MID(dtOcorrencia, 1, 2)) *
	   1 AS UNSIGNED) AS DATE) AS dtOcorrencia,
       cast(cast(
	   CONCAT('20', MID(R.vencimento, 5, 2), MID(R.vencimento, 3, 2), MID(R.vencimento, 1, 2)) *
	   1 AS UNSIGNED) AS DATE) AS vencimento,
       valor / 100                 AS valorBoleto,
       (taxa + principal) / 100    AS valorPago,
       taxa / 100                  AS taxa,
       R.juros * 1 / 100           AS juros,
       desconto / 100              AS desconto
FROM bi.retornoBanco AS R
WHERE ocorrencia = '06';


SELECT nossoNumero                    AS Nosso_Numero,
       nome                           AS Nome,
       documento                      AS Documento,
       usoEmpresa                     AS Uso_Empresa,
       banco                          AS Banco,
       agencia                        AS Angencia,
       carteira                       AS Carteira,
       cast(dtOcorrencia AS UNSIGNED) AS Dt_Ocorrencia,
       cast(vencimento AS UNSIGNED)   AS Vencimento,
       truncate(valorBoleto * 100, 0) AS Valor_Boleto,
       truncate(valorPago * 100, 0)   AS Valor_Pago,
       truncate(taxa * 100, 0)        AS Taxa,
       truncate(juros * 100, 0)       AS Juros,
       truncate(desconto * 100, 0)    AS Desconto,
       CASE
	 WHEN nossoNumero BETWEEN 10000001 AND 19999999
	   THEN 'Boleto Cliente'
	 WHEN nossoNumero BETWEEN 80000001 AND 89999999
	   THEN 'Emprestimo Pincred'
	 WHEN nossoNumero BETWEEN 90000001 AND 99999999
	   THEN 'Plano Saude'
	 ELSE ''
       END                            AS tipo
FROM T
WHERE dtOcorrencia >= 20200505;