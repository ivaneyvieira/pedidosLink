package br.com.astrosoft.pedidoLink.viewmodel

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.framework.viewmodel.fail
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalTime

class PedidoLinkViewModel(view: IPedidoLinkView): ViewModel<IPedidoLinkView>(view) {
  fun updateGridPedido() {
    view.updateGridPedido(listPedido())
  }
  
  private fun listPedido(): List<PedidoLink> {
    val filtro = view.filtroPedido
    return PedidoLink.listaPedido()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null) && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun updateGridLink() {
    view.updateGridLink(listLink())
  }
  
  private fun listLink(): List<PedidoLink> {
    val filtro = view.filtroLink
    return PedidoLink.listaLink()
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
    updateGridLink()
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
  
  fun marcaVendedor(pedido: PedidoLink, senha: String) = exec {
    if(pedido.senhaVendedor == senha) pedido.marcaVendedor("S")
    else fail("Senha incorreta")
    updateGridPedido()
  }
  
  fun desmarcaVendedor() {
    val itens =      view.itensSelecionadoLink()
        .ifEmpty {fail("Nenhum item selecionado")}
    itens.forEach {pedidoLink: PedidoLink ->
      pedidoLink.marcaVendedor("")
    }
    updateGridLink()
  }
}

interface IFiltroPedido {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IFiltroLink {
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
  fun updateGridPedido(itens: List<PedidoLink>)
  fun updateGridLink(itens: List<PedidoLink>)
  fun updateGridPendente(itens: List<PedidoLink>)
  fun updateGridFinalizado(itens: List<PedidoLink>)
  fun updateGridFaturado(itens: List<PedidoLink>)
  
  fun itensSelecionadoPedido(): List<PedidoLink>
  fun itensSelecionadoLink(): List<PedidoLink>
  fun itensSelecionadoPendente(): List<PedidoLink>
  
  val filtroPedido: IFiltroPedido
  val filtroLink: IFiltroLink
  val filtroPendente: IFiltroPendente
  val filtroFinalizado: IFiltroFinalizado
  val filtroFaturado: IFiltroFaturado
}

data class SenhaVendendor(var nome: String, var senha: String?)