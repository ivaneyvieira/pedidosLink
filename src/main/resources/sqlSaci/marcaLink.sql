UPDATE sqldados.eord
SET eord.l15 = :data *1 ,
    eord.l16 = time_to_sec(:time)
WHERE ordno = :ordno
  AND storeno = :storeno