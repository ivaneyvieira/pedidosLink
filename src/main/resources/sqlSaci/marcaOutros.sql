UPDATE sqldados.eord
SET eord.c2 = :marca
WHERE ordno = :ordno
  AND storeno = :storeno