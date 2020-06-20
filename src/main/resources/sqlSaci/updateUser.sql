UPDATE sqldados.abastecimentoLoc AS L INNER JOIN sqldados.users AS U USING (no)
SET bits2    = :bitAcesso,
    auxLong1 = :storeno
WHERE login = :login