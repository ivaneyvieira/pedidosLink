package br.com.astrosoft.pedidoLink.model.beans

import br.com.astrosoft.pedidoLink.model.saci
import br.com.astrosoft.framework.spring.IUser
import kotlin.math.pow

class UserSaci : IUser {
  var no: Int = 0
  var name: String = ""
  override var login: String = ""
  override var senha: String = ""
  override fun roles(): List<String> {
    return if(admin) listOf("ADMIN") else listOf("USER")
  }
  
  var bitAcesso: Int = 0
  var prntno: Int = 0
  var impressora: String = ""
  var storeno : Int = 0
  
  //Otiros campos
  var ativo: Boolean = true
  val admin
    get() = login == "ADM"
  
  fun initVars(): UserSaci {
    val bits = bitAcesso
    ativo = (bits and 2.pow(9)) != 0 || admin
    return this
  }
  
  fun bitAcesso(): Int {
    val ativoSum = if(ativo) 2.pow(9) else 0
    val bits = saci.findUser(login)?.bitAcesso ?: 0
    return ativoSum or bits
  }
  
  companion object {
    fun findAll(): List<UserSaci>? {
      return saci.findAllUser()
        .filter {it.ativo}
    }
    
    fun updateUser(user: UserSaci) {
      saci.updateUser(user)
    }
    
    fun findUser(login: String?): UserSaci? {
      return saci.findUser(login)
    }
  }
  
  fun Int.pow(e: Int): Int = this.toDouble()
    .pow(e)
    .toInt()
}


