package br.com.astrosoft.pedidoLink.viewmodel

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import java.time.LocalDate

class PedidoLinkViewModel(view: IPedidoLinkView): ViewModel<IPedidoLinkView>(view) {
  fun updateGridGeral() {
    view.updateGridGeral(listGeral())
  }
  
  private fun listGeral(): List<PedidoLink> {
    val filtro = view.filtroGeral
    return PedidoLink.listaCaixaMovimentoGeral()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null) && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun updateGridPendente() {
    view.updateGridPendente(listPendente())
  }
  
  private fun listPendente(): List<PedidoLink> {
    val filtro = view.filtroPendente
    return PedidoLink.listaCaixaMovimentoPendente()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null) && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun updateGridFaturado() {
    view.updateGridFaturado(listFaturado())
  }
  
  private fun listFaturado(): List<PedidoLink> {
    val filtro = view.filtroFaturado
    return PedidoLink.listaCaixaMovimentoFaturado()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null) && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
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

interface IFiltroFaturado {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IPedidoLinkView: IView {
  fun updateGridGeral(itens: List<PedidoLink>)
  fun updateGridPendente(itens: List<PedidoLink>)
  fun updateGridFaturado(itens: List<PedidoLink>)
  
  val filtroGeral: IFiltroGeral
  val filtroPendente: IFiltroPendente
  val filtroFaturado: IFiltroFaturado
}