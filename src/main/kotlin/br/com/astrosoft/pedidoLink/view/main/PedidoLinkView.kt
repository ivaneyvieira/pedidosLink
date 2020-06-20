package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.view.layout.PedidoLinkLayout
import br.com.astrosoft.pedidoLink.viewmodel.ICaixaMovimentacaoView
import br.com.astrosoft.pedidoLink.viewmodel.CaixaMovimentacaoViewModel
import com.github.mvysny.karibudsl.v10.tabSheet
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@Route(layout = PedidoLinkLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoLinkView: ViewLayout<CaixaMovimentacaoViewModel>(), ICaixaMovimentacaoView {
  private lateinit var gridMovimentoGeral: PainelGridGeral
  private lateinit var gridMovimentoPendente: PainelGridPendente
  private lateinit var gridMovimentoFaturado: PainelGridFaturado
  override val viewModel: CaixaMovimentacaoViewModel = CaixaMovimentacaoViewModel(this)
  
  override fun isAccept() = true
  
  init {
    tabSheet {
      setSizeFull()
      tab {
        gridMovimentoGeral = PainelGridGeral()
        gridMovimentoGeral
      }.apply {
        val button = Button(TAB_MOVIMENTO_GERAL) {
          viewModel.updateGridGeral()
        }
        button.addThemeVariants(ButtonVariant.LUMO_SMALL)
        this.addComponentAsFirst(button)
      }
      
      tab {
        gridMovimentoPendente = PainelGridPendente()
        gridMovimentoPendente
      }.apply {
        val button = Button(TAB_MOVIMENTO_PENDENTE) {
          viewModel.updateGridPendente()
        }
        button.addThemeVariants(ButtonVariant.LUMO_SMALL)
        this.addComponentAsFirst(button)
      }
      
      tab {
        gridMovimentoFaturado = PainelGridFaturado()
        gridMovimentoFaturado
      }.apply {
        val button = Button(TAB_MOVIMENTO_FATURADO) {
          viewModel.updateGridFaturado()
        }
        button.addThemeVariants(ButtonVariant.LUMO_SMALL)
        this.addComponentAsFirst(button)
      }
    }
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
  
  companion object {
    const val TAB_MOVIMENTO_GERAL: String = "Inserido"
    const val TAB_MOVIMENTO_PENDENTE: String = "Pendente"
    const val TAB_MOVIMENTO_FATURADO: String = "Fatura"
  }
}

