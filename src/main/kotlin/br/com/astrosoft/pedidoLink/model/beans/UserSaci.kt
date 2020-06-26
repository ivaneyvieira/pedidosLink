package br.com.astrosoft.pedidoLink.model.beans

import br.com.astrosoft.framework.spring.IUser
import br.com.astrosoft.pedidoLink.model.saci
import kotlin.math.pow

class UserSaci: IUser {
  var no: Int = 0
  var name: String = ""
  override var login: String = ""
  override var senha: String = ""
  override fun roles(): List<String> {
    val roles = if(admin) listOf("ADMIN") else listOf("USER")
    val rolePedido = if(acl_pedido) listOf("PEDIDO") else listOf()
    val roleLink = if(acl_link) listOf("LINK") else listOf()
    val rolePendente = if(acl_pendente) listOf("PENDENTE") else listOf()
    val roleFinalizar = if(acl_finalizar) listOf("FINALIZAR") else listOf()
    val roleFaturado = if(acl_faturado) listOf("FATURADO") else listOf()
    return roles + rolePedido + roleLink + rolePendente + roleFinalizar + roleFaturado
  }
  
  var bitAcesso: Int = 0
  var prntno: Int = 0
  var impressora: String = ""
  var storeno: Int = 0
  
  //Otiros campos
  var ativo
    get() = (bitAcesso and BIT_ATIVO) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_ATIVO
      else bitAcesso and BIT_ATIVO.inv()
    }
  val admin
    get() = login == "ADM"
  var acl_pedido
    get() = (bitAcesso and BIT_PEDIDO) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_PEDIDO
      else bitAcesso and BIT_PEDIDO.inv()
    }
  var acl_link
    get() = (bitAcesso and BIT_LINK) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_LINK
      else bitAcesso and BIT_LINK.inv()
    }
  var acl_pendente
    get() = (bitAcesso and BIT_PENDENTE) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_PENDENTE
      else bitAcesso and BIT_PENDENTE.inv()
    }
  var acl_finalizar
    get() = (bitAcesso and BIT_FINALIZAR) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_FINALIZAR
      else bitAcesso and BIT_FINALIZAR.inv()
    }
  var acl_faturado
    get() = (bitAcesso and BIT_FATURADO) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_FATURADO
      else bitAcesso and BIT_FATURADO.inv()
    }
  
  companion object {
    private val BIT_ATIVO = 2.pow(9)
    private val BIT_PEDIDO = 2.pow(0)
    private val BIT_LINK = 2.pow(1)
    private val BIT_PENDENTE = 2.pow(2)
    private val BIT_FINALIZAR = 2.pow(3)
    private val BIT_FATURADO = 2.pow(4)
    
    fun findAll(): List<UserSaci>? {
      return saci.findAllUser()
        .filter {it.ativo}
    }
    
    fun updateUser(user: UserSaci) {
      saci.updateUser(user)
    }
    
    fun findUser(login: String?): UserSaci? {
      return saci.findUser(login)
        .firstOrNull()
    }
  }
}

fun Int.pow(e: Int): Int = this.toDouble()
  .pow(e)
  .toInt()
