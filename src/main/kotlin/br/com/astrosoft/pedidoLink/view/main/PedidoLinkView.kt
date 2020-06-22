package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.framework.view.tabGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.view.layout.PedidoLinkLayout
import br.com.astrosoft.pedidoLink.viewmodel.CardLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFaturado
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
import java.time.LocalDate
import java.time.LocalTime

@Route(layout = PedidoLinkLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoLinkView: ViewLayout<PedidoLinkViewModel>(), IPedidoLinkView {
  private lateinit var formLink: FormLink
  private val gridGeral: PainelGridGeral
  private val gridPendente: PainelGridPendente
  private val gridFaturado: PainelGridFaturado
  override val viewModel: PedidoLinkViewModel = PedidoLinkViewModel(this)
  
  override fun isAccept() = true
  
  init {
    gridGeral = PainelGridGeral(::marcaPedido) {viewModel.updateGridGeral()}
    gridPendente = PainelGridPendente {viewModel.updateGridPendente()}
    gridFaturado = PainelGridFaturado {viewModel.updateGridFaturado()}
    tabSheet {
      setSizeFull()
      tabGrid(TAB_GERAL, gridGeral)
      tabGrid(TAB_PENDENTE, gridPendente)
      tabGrid(TAB_FATURADO, gridFaturado)
    }
    viewModel.updateGridGeral()
  }
  
  private fun marcaPedido() {
    formLink = FormLink()
    formLink.binder.bean = CardLink(LocalDate.now(), LocalTime.now())
    if(itensSelecionadoGeral().isEmpty()) showError("Nenhum pedido foi selecionado")
    else showForm("Dados do Link", formLink) {
      val bean = formLink.binder.bean
      viewModel.marcaPedido(bean)
    }
  }
  
  override fun itensSelecionadoGeral(): List<PedidoLink> {
    return gridGeral.selectedItems()
  }
  
  private fun confirmMarca() {
    TODO("Not yet implemented")
  }
  
  override fun updateGridGeral(itens: List<PedidoLink>) {
    gridGeral.updateGrid(itens)
  }
  
  override fun updateGridPendente(itens: List<PedidoLink>) {
    gridPendente.updateGrid(itens)
  }
  
  override fun updateGridFaturado(itens: List<PedidoLink>) {
    gridFaturado.updateGrid(itens)
  }
  
  override val filtroGeral: IFiltroGeral
    get() = gridGeral.filterBar as IFiltroGeral
  override val filtroPendente: IFiltroPendente
    get() = gridPendente.filterBar as IFiltroPendente
  override val filtroFaturado: IFiltroFaturado
    get() = gridFaturado.filterBar as IFiltroFaturado
  
  companion object {
    const val TAB_GERAL: String = "Inserido"
    const val TAB_PENDENTE: String = "Pendente"
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

