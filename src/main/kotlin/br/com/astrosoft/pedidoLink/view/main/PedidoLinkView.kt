package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.framework.view.tabGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.view.layout.PedidoLinkLayout
import br.com.astrosoft.pedidoLink.viewmodel.CardLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFaturado
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFinalizado
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroGeral
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPendente
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import br.com.astrosoft.pedidoLink.viewmodel.PedidoLinkViewModel
import com.github.mvysny.karibudsl.v10.bind
import com.github.mvysny.karibudsl.v10.datePicker
import com.github.mvysny.karibudsl.v10.tabSheet
import com.github.mvysny.karibudsl.v10.timePicker
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalTime

@Route(layout = PedidoLinkLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoLinkView: ViewLayout<PedidoLinkViewModel>(), IPedidoLinkView {
  private val gridGeral: PainelGridGeral
  private val gridPendente: PainelGridPendente
  private val gridFinalizado: PainelGridFinalizado
  private val gridFaturado: PainelGridFaturado
  override val viewModel: PedidoLinkViewModel = PedidoLinkViewModel(this)
  
  override fun isAccept() = true
  
  init {
    gridGeral = PainelGridGeral(::marcaPedido) {viewModel.updateGridGeral()}
    gridPendente = PainelGridPendente(::desmarcaPedido, ::uploadFile) {viewModel.updateGridPendente()}
    gridFinalizado = PainelGridFinalizado() {viewModel.updateGridFinalizado()}
    gridFaturado = PainelGridFaturado {viewModel.updateGridFaturado()}
    tabSheet {
      setSizeFull()
      tabGrid(TAB_GERAL, gridGeral)
      tabGrid(TAB_PENDENTE, gridPendente)
      tabGrid(TAB_FINALIZADO, gridFinalizado)
      tabGrid(TAB_FATURADO, gridFaturado)
    }
    viewModel.updateGridGeral()
  }
  
  private fun uploadFile(inputStream: InputStream) {
    viewModel.uploadFile(inputStream)
  }
  
  private fun desmarcaPedido() {
    if(itensSelecionadoPendente().isEmpty()) showError("Nenhum pedido foi selecionado")
    viewModel.desmarcaPedido()
  }
  
  private fun marcaPedido(pedido: PedidoLink) {
    viewModel.marcaPedido(pedido)
  }
  
  override fun itensSelecionadoGeral(): List<PedidoLink> {
    return gridGeral.selectedItems()
  }
  
  override fun itensSelecionadoPendente(): List<PedidoLink> {
    return gridPendente.selectedItems()
  }
  
  override fun updateGridGeral(itens: List<PedidoLink>) {
    gridGeral.updateGrid(itens)
  }
  
  override fun updateGridPendente(itens: List<PedidoLink>) {
    gridPendente.updateGrid(itens)
  }
  
  override fun updateGridFinalizado(itens: List<PedidoLink>) {
    gridFinalizado.updateGrid(itens)
  }
  
  override fun updateGridFaturado(itens: List<PedidoLink>) {
    gridFaturado.updateGrid(itens)
  }
  
  override val filtroGeral: IFiltroGeral
    get() = gridGeral.filterBar as IFiltroGeral
  override val filtroPendente: IFiltroPendente
    get() = gridPendente.filterBar as IFiltroPendente
  override val filtroFinalizado: IFiltroFinalizado
    get() = gridFinalizado.filterBar as IFiltroFinalizado
  override val filtroFaturado: IFiltroFaturado
    get() = gridFaturado.filterBar as IFiltroFaturado
  
  companion object {
    const val TAB_GERAL: String = "Inserido"
    const val TAB_PENDENTE: String = "Pendente"
    const val TAB_FINALIZADO: String = "Finalziado"
    const val TAB_FATURADO: String = "Faturado"
  }
}

class FormLink: FormLayout() {
  val binder = Binder<CardLink>(CardLink::class.java)
  
  init {
    datePicker("Data") {
      localePtBr()
      isClearButtonVisible = true
      element.setAttribute("theme", "small")
      bind(binder).bind(CardLink::data)
    }
    
    timePicker("Hora") {
      bind(binder).bind(CardLink::hora)
    }
  }
}

