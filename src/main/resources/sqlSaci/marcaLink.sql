UPDATE sqldados.eord
SET eord.l15 = cast(:data as date) * 1,
    eord.l16 = time_to_sec(:hora)
WHERE ordno = :ordno
  AND storeno = :storeno