package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.tabGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.view.layout.PedidoLinkLayout
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFaturado
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroGeral
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPendente
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import br.com.astrosoft.pedidoLink.viewmodel.PedidoLinkViewModel
import com.github.mvysny.karibudsl.v10.tabSheet
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(layout = PedidoLinkLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoLinkView: ViewLayout<PedidoLinkViewModel>(), IPedidoLinkView {
  private lateinit var gridMovimentoGeral: PainelGridGeral
  private lateinit var gridMovimentoPendente: PainelGridPendente
  private lateinit var gridMovimentoFaturado: PainelGridFaturado
  override val viewModel: PedidoLinkViewModel = PedidoLinkViewModel(this)
  
  override fun isAccept() = true
  
  init {
    gridMovimentoGeral = PainelGridGeral {viewModel.updateGridGeral()}
    gridMovimentoPendente = PainelGridPendente {viewModel.updateGridPendente()}
    gridMovimentoFaturado = PainelGridFaturado {viewModel.updateGridFaturado()}
    tabSheet {
      setSizeFull()
      tabGrid(TAB_MOVIMENTO_GERAL, gridMovimentoGeral)
      tabGrid(TAB_MOVIMENTO_PENDENTE, gridMovimentoPendente)
      tabGrid(TAB_MOVIMENTO_FATURADO, gridMovimentoFaturado)
    }
    viewModel.updateGridGeral()
  }
  
  override fun updateGridGeral(itens: List<PedidoLink>) {
    gridMovimentoGeral.updateGrid(itens)
  }
  
  override fun updateGridPendente(itens: List<PedidoLink>) {
    gridMovimentoPendente.updateGrid(itens)
  }
  
  override fun updateGridFaturado(itens: List<PedidoLink>) {
    gridMovimentoFaturado.updateGrid(itens)
  }
  
  override val filtroGeral: IFiltroGeral
    get() = gridMovimentoGeral.filterBar as IFiltroGeral
  override val filtroPendente: IFiltroPendente
    get() = gridMovimentoPendente.filterBar as IFiltroPendente
  override val filtroFaturado: IFiltroFaturado
    get() = gridMovimentoFaturado.filterBar as IFiltroFaturado
  
  companion object {
    const val TAB_MOVIMENTO_GERAL: String = "Inserido"
    const val TAB_MOVIMENTO_PENDENTE: String = "Pendente"
    const val TAB_MOVIMENTO_FATURADO: String = "Faturado"
  }
}

