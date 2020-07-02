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
                      val empno: Int?,
                      val vendedor: String?,
                      val status: Int,
                      val confirmado: String,
                      val senhaVendedor: String,
                      val marca: String,
                      val parcelas: Int?,
                      val autorizadora: String?,
                      val autorizacao: String?,
                      val nsuHost: String?,
                      val dataTef: LocalDate?,
                      val statusTef: String?) {
  val notaFiscal: String
    get() = numeroNota(nfnoNota, nfseNota)
  val statusPedido
    get() = StatusPedido.values()
      .toList()
      .firstOrNull {it.numero == status}
  
  private fun numeroNota(nfno: String, nfse: String): String {
    return when {
      nfno == "" -> ""
      nfse == "" -> nfno
      else       -> "$nfno/$nfse"
    }
  }
  
  val noteClipBoard
    get() = "$nota $numPedido:"
  
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
    private val statusValidosPedido = listOf(1, 2, 8)
    
    fun listaPedido(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      //val statusList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
      return list.filter {
        it.notaFiscal == "" && it.dataLink == null && statusValidosPedido.contains(it.status) && it.marca == ""
      }
    }
    
    fun listaLink(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.notaFiscal == "" && it.dataLink == null && statusValidosPedido.contains(it.status) && it.marca != ""
      }
    }
    
    fun listaPendente(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.dataLink != null && it.notaFiscal == "" && it.confirmado == "N"
      }
    }
    
    fun listaFinalizar(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.dataLink != null && it.notaFiscal == "" && it.confirmado == "S"
      }
    }
    
    fun listaFaturar(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        it.notaFiscal != ""
      }
    }
    
    fun listaOutros(): List<PedidoLink> {
      val list = saci.listaPedidoLink(storeno)
      return list.filter {
        !statusValidosPedido.contains(it.status)
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

enum class StatusPedido(val numero: Int, val descricao: String) {
  INCLUIDO(0, "Incluído"),
  ORCADO(1, "Orçado"),
  RESERVADO(2, "Reservado"),
  VENDIDO(3, "Vendido"),
  EXPIRADO(4, "Expirado"),
  CANCELADO(5, "Cancelado"),
  RESERVADO_B(6, "Reserva B"),
  TRANSITO(7, "Trânsito"),
  FUTURA(8, "Futura");
  
  override fun toString() = descricao
}