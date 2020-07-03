package br.com.astrosoft.pedidoLink.model

import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.framework.util.DB
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.model.beans.UserSaci
import java.time.LocalDate
import java.time.LocalTime

class QuerySaci: QueryDB(driver, url, username, password) {
  fun findUser(login: String?): List<UserSaci> {
    login ?: return emptyList()
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", login)
    }
  }
  
  fun findAllUser(): List<UserSaci> {
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", "TODOS")
    }
  }
  
  fun updateUser(user: UserSaci) {
    val sql = "/sqlSaci/updateUser.sql"
    script(sql) {
      addOptionalParameter("login", user.login)
      addOptionalParameter("bitAcesso", user.bitAcesso)
      addOptionalParameter("storeno", user.storeno)
    }
  }
  
  fun listaPedidoLink(storeno: Int): List<PedidoLink> {
    val sql = "/sqlSaci/pedidoLink.sql"
    return query(sql, PedidoLink::class) {
      addOptionalParameter("storeno", storeno)
      addOptionalParameter("data", 20200608)
    }
  }
  
  fun marcaLink(loja: Int, numPedido: Int, data: LocalDate?, hora: LocalTime?) {
    val sql = "/sqlSaci/marcaLink.sql"
    script(sql) {
      addParameter("storeno", loja)
      addParameter("ordno", numPedido)
      addParameter("data", data)
      addParameter("hora", hora)
    }
  }
  
  fun insertFile(records: List<List<String>>) {
    val header =
      records.firstOrNull()
        .orEmpty()
    val linhas = records.drop(1)
    val sql = "/sqlSaci/insertTef.sql"
    linhas.forEach {linha ->
      script(sql) {
        header.forEachIndexed {index, coluna ->
          val value = linha.getOrNull(index) ?: ""
          addOptionalParameter(coluna, value)
        }
      }
    }
  }
  
  fun marcaVendedor(loja: Int, numPedido: Int, marcaNova: String) {
    val sql = "/sqlSaci/marcaVendedor.sql"
    script(sql) {
      addParameter("storeno", loja)
      addParameter("ordno", numPedido)
      addParameter("marca", marcaNova)
    }
  }
  
  fun marcaOutros(loja: Int, numPedido: Int, marcaNova: String) {
    val sql = "/sqlSaci/marcaOutros.sql"
    script(sql) {
      addParameter("storeno", loja)
      addParameter("ordno", numPedido)
      addParameter("marca", marcaNova)
    }
  }
  
  companion object {
    private val db = DB("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
    internal val test = db.test
    val ipServer =
      url.split("/")
        .getOrNull(2)
  }
}

val saci = QuerySaci()