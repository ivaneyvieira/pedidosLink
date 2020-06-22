package br.com.astrosoft

import br.com.astrosoft.pedidoLink.model.saci
import br.com.astrosoft.framework.spring.IUser
import br.com.astrosoft.framework.spring.SecurityUtils
import br.com.astrosoft.framework.view.ViewUtil

object AppConfig {
  val version = ViewUtil.versao
  const val commpany = "Engecopi"
  const val title = "Pedidos Link"
  const val shortName = title
  const val iconPath = "icons/logo.png"
  
  val test : Boolean?
    get() = false
  
  val userDetails
    get() = SecurityUtils.userDetails
  val userSaci
    get() = userDetails?.user
  
  fun findUser(username : String?) : IUser? = saci.findUser(username).firstOrNull()
}
