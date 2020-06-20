package br.com.astrosoft.pedidoLink.viewmodel

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink

class CaixaMovimentacaoViewModel(view: ICaixaMovimentacaoView): ViewModel<ICaixaMovimentacaoView>(view) {
  fun updateGridGeral() {
    view.updateGridGeral(listGeral())
  }
  
  private fun listGeral(): List<PedidoLink> {
    return PedidoLink.listaCaixaMovimentoGeral()
  }
  
  fun updateGridPendente() {
    view.updateGridPendente(listPendente())
  }
  
  private fun listPendente(): List<PedidoLink> {
    return PedidoLink.listaCaixaMovimentoPendente()
  }
  
  fun updateGridFaturado() {
    view.updateGridFaturado(listFaturado())
  }
  
  private fun listFaturado(): List<PedidoLink> {
    return PedidoLink.listaCaixaMovimentoFaturado()
  }
}

interface ICaixaMovimentacaoView: IView {
  fun updateGridGeral(itens: List<PedidoLink>)
  fun updateGridPendente(itens: List<PedidoLink>)
  fun updateGridFaturado(itens: List<PedidoLink>)
}