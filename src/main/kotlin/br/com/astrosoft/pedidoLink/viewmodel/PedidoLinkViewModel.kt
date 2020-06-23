package br.com.astrosoft.pedidoLink.viewmodel

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.framework.viewmodel.fail
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalTime

class PedidoLinkViewModel(view: IPedidoLinkView): ViewModel<IPedidoLinkView>(view) {
  fun updateGridGeral() {
    view.updateGridGeral(listGeral())
  }
  
  private fun listGeral(): List<PedidoLink> {
    val filtro = view.filtroGeral
    return PedidoLink.listaGeral()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null) && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun updateGridPendente() {
    view.updateGridPendente(listPendente())
  }
  
  private fun listPendente(): List<PedidoLink> {
    val filtro = view.filtroPendente
    return PedidoLink.listaPendente()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null) && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun updateGridFinalizado() {
    view.updateGridFinalizado(listFinalizado())
  }
  
  private fun listFinalizado(): List<PedidoLink> {
    val filtro = view.filtroFinalizado
    return PedidoLink.listaFinalizado()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null) && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun updateGridFaturado() {
    view.updateGridFaturado(listFaturado())
  }
  
  private fun listFaturado(): List<PedidoLink> {
    val filtro = view.filtroFaturado
    return PedidoLink.listaFaturado()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null) && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun marcaPedido(pedido: PedidoLink?) = exec {
    pedido?.marcaHorario(LocalDate.now(), LocalTime.now())
    updateGridGeral()
  }
  
  fun desmarcaPedido() = exec {
    val itens =
      view.itensSelecionadoPendente()
        .ifEmpty {fail("Nenhum item selecionado")}
    itens.forEach {pedidoLink: PedidoLink ->
      pedidoLink.marcaHorario(null, null)
    }
    updateGridPendente()
  }
  
  fun uploadFile(inputStream: InputStream) {
    PedidoLink.uploadFile(inputStream)
    updateGridPendente()
  }
}

interface IFiltroGeral {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IFiltroPendente {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IFiltroFinalizado {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IFiltroFaturado {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IPedidoLinkView: IView {
  fun updateGridGeral(itens: List<PedidoLink>)
  fun updateGridPendente(itens: List<PedidoLink>)
  fun updateGridFinalizado(itens: List<PedidoLink>)
  fun updateGridFaturado(itens: List<PedidoLink>)
  
  fun itensSelecionadoGeral(): List<PedidoLink>
  fun itensSelecionadoPendente(): List<PedidoLink>
  
  val filtroGeral: IFiltroGeral
  val filtroPendente: IFiltroPendente
  val filtroFinalizado: IFiltroFinalizado
  val filtroFaturado: IFiltroFaturado
}

data class CardLink(var data: LocalDate?, var hora: LocalTime?)