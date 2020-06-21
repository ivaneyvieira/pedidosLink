package br.com.astrosoft.pedidoLink.model.beans

import br.com.astrosoft.AppConfig
import br.com.astrosoft.pedidoLink.model.saci
import java.time.LocalDate
import java.time.LocalTime

data class PedidoLink(val loja: Int,
                      val numPedido: Int,
                      val dataPedido: LocalDate?,
                      val horaPedido: LocalTime?,
                      val metodo: Int,
                      val nfnoNota: String,
                      val nfseNota: String,
                      val dataNota: LocalDate?,
                      val horaNota: LocalTime,
                      val obs: String,
                      val username: String,
                      val dataLink: LocalDate?,
                      val horaLink: LocalTime?) {
  val notaFiscal: String
    get() = numeroNota(nfnoNota, nfseNota)
  
  private fun numeroNota(nfno: String, nfse: String): String {
    return when {
      nfno == "" -> ""
      nfse == "" -> nfno
      else       -> "$nfno/$nfse"
    }
  }
  
  fun marcaHorario(data: LocalDate?, hora : LocalTime?) {
    saci.marcaLink(loja, numPedido, data, hora)
  }
  
  companion object {
    val storeno: Int by lazy {
      UserSaci.findUser(AppConfig.userSaci?.login)?.storeno ?: 0
    }
  
    fun listaCaixaMovimentoGeral(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.notaFiscal == "" && it.dataLink == null
      }
    }
  
    fun listaCaixaMovimentoPendente(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.dataLink != null
      }
    }
    fun listaCaixaMovimentoFaturado(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.notaFiscal != ""
      }
    }
  }
}