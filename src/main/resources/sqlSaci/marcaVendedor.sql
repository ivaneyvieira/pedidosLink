UPDATE sqldados.eord
SET eord.c1 = :marca
WHERE ordno = :ordno
  AND storeno = :storeno