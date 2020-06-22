UPDATE sqldados.eord
SET eord.l15 = IFNULL(cast(:data as date) * 1, 0),
    eord.l16 = IFNULL(time_to_sec(:hora), 0)
WHERE ordno = :ordno
  AND storeno = :storeno