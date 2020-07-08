UPDATE sqldados.eord
SET eord.s16 = :userLink
WHERE ordno = :ordno
  AND storeno = :storeno