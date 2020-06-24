package br.com.astrosoft.pedidoLink.model.beans

import br.com.astrosoft.AppConfig
import br.com.astrosoft.pedidoLink.model.saci
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
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
                      val horaLink: LocalTime?,
                      val nota: String,
                      val valorFrete: Double?,
                      val total: Double?,
                      val valorLink: Double,
                      val cartao: String?,
                      val whatsapp: String?,
                      val cliente: String?,
                      val vendedor: String?,
                      val status: Int,
                      val confirmado: String,
                      val senhaVendedor: String,
                      val marca: String) {
  val notaFiscal: String
    get() = numeroNota(nfnoNota, nfseNota)
  
  private fun numeroNota(nfno: String, nfse: String): String {
    return when {
      nfno == "" -> ""
      nfse == "" -> nfno
      else       -> "$nfno/$nfse"
    }
  }
  
  val noteClipBoard
    get() = "${nota}: $numPedido"
  
  fun marcaHorario(data: LocalDate?, hora: LocalTime?) {
    saci.marcaLink(loja, numPedido, data, hora)
  }
  
  fun marcaVendedor(marcaNova: String) {
    saci.marcaVendedor(loja, numPedido, marcaNova)
  }
  
  companion object {
    val storeno: Int by lazy {
      UserSaci.findUser(AppConfig.userSaci?.login)?.storeno ?: 0
    }
    
    fun listaPedido(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      val statusList = listOf(1, 8)
      //val statusList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
      return list.filter {
        it.notaFiscal == "" && it.dataLink == null && statusList.contains(it.status) && it.marca == ""
      }
    }
    
    fun listaLink(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.notaFiscal == "" && it.dataLink == null && listOf(1, 8).contains(it.status) && it.marca != ""
      }
    }
    
    fun listaPendente(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.dataLink != null && it.notaFiscal == "" && it.confirmado == "N"
      }
    }
    
    fun listaFinalizado(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.dataLink != null && it.notaFiscal == "" && it.confirmado == "S"
      }
    }
    
    fun listaFaturado(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.notaFiscal != ""
      }
    }
    
    fun uploadFile(inputStream: InputStream) {
      val buffer = BufferedReader(InputStreamReader(inputStream))
      val records =
        buffer.lineSequence()
          .map {line ->
            line.split(';')
              .toList()
          }
      saci.insertFile(records.toList())
    }
  }
}