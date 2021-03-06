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
                      val userLink: Int,
                      val parcelas: Int?,
                      val autorizadora: String?,
                      val autorizacao: String?,
                      val nsuHost: String?,
                      val dataTef: LocalDate?,
                      val statusTef: String) {
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
  
  fun marcaUserLink(userLink: Int) {
    saci.marcaUserLink(loja, numPedido, userLink)
  }
  
  companion object {
    private val storeno
      get()= (AppConfig.userSaci as? UserSaci)?.storeno ?: 0
    
    private val statusValidosPedido = listOf(1, 2, 8)
    private val statusTefOutros = listOf("NOV", "NEG", "INV", "EST", "EXP", "ABA", "CAN")
    private val statusTefConfirmado = listOf("CON")
    private val cartoesFiltro = listOf("VISA", "ELO", "MASTER", "HIPER", "DINER", "AMEX")
    private val list = mutableListOf<PedidoLink>().apply {
      addAll(saci.listaPedidoLink(storeno))
    }
    private var time = LocalTime.now()
    
    @Synchronized
    private fun updateList(loja : Int): List<PedidoLink> {
      return saci.listaPedidoLink(loja);
    }
    
    fun listaPedido(): List<PedidoLink> {
      return updateList(storeno).filter {
        it.notaFiscal == "" && it.dataLink == null && statusValidosPedido.contains(it.status)
        && it.marca == "" && it.cartao in cartoesFiltro
      }
    }
    
    fun listaGerarLink(): List<PedidoLink> {
      return updateList(0).filter {
        it.notaFiscal == "" && it.dataLink == null && statusValidosPedido.contains(it.status) && it.marca != "" &&
        it.userLink == 0
      }
    }
    
    fun listaLink(): List<PedidoLink> {
      val userSaci = AppConfig.userSaci as UserSaci
      return updateList(0).filter {
        it.notaFiscal == "" && it.dataLink == null && statusValidosPedido.contains(it.status) && it.marca != "" &&
        (it.userLink == userSaci.no || (userSaci.admin && it.userLink != 0))
      }
    }
    
    fun listaPendente(): List<PedidoLink> {
      return updateList(0).filter {
        it.dataLink != null && it.notaFiscal == "" && it.confirmado == "N" && it.statusTef in listOf("AGU", "")
      }
    }
    
    fun listaFinalizar(): List<PedidoLink> {
      return updateList(storeno).filter {
        it.dataLink != null && it.notaFiscal == "" && it.confirmado == "S"
        && it.statusTef in statusTefConfirmado
      }
    }
    
    fun listaFaturado(): List<PedidoLink> {
      return updateList(storeno).filter {
        it.notaFiscal != ""
      }
    }
    
    fun listaOutros(): List<PedidoLink> {
      return updateList(storeno).filter {
        it.statusTef in statusTefOutros;
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