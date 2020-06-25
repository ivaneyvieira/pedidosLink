package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.tabGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.view.layout.PedidoLinkLayout
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFaturar
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFinalizar
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPedido
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPendente
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import br.com.astrosoft.pedidoLink.viewmodel.PedidoLinkViewModel
import br.com.astrosoft.pedidoLink.viewmodel.SenhaVendendor
import com.github.mvysny.karibudsl.v10.bind
import com.github.mvysny.karibudsl.v10.passwordField
import com.github.mvysny.karibudsl.v10.tabSheet
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.textfield.TextFieldVariant.LUMO_SMALL
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import java.io.InputStream

@Route(layout = PedidoLinkLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoLinkView: ViewLayout<PedidoLinkViewModel>(), IPedidoLinkView, IEventGridPedido, IEventGridLink,
                      IEventGridPendente {
  private val gridPedido = PainelGridPedido(this) {viewModel.updateGridPedido()}
  private val gridLink = PainelGridLink(this) {viewModel.updateGridLink()}
  private val gridPendente = PainelGridPendente(this) {viewModel.updateGridPendente()}
  private val gridFinalizar = PainelGridFinalizar() {viewModel.updateGridFinalizado()}
  private val gridFaturar = PainelGridFaturado {viewModel.updateGridFaturar()}
  override val viewModel: PedidoLinkViewModel = PedidoLinkViewModel(this)
  
  override fun isAccept() = true
  
  init {
    tabSheet {
      setSizeFull()
      tabGrid(TAB_PEDIDO, gridPedido)
      tabGrid(TAB_LINK, gridLink)
      tabGrid(TAB_PENDENTE, gridPendente)
      tabGrid(TAB_FINALIZADO, gridFinalizar)
      tabGrid(TAB_FATURADO, gridFaturar)
    }
    viewModel.updateGridPedido()
  }
  
  override fun desmarcaPedidoLink() {
    viewModel.desmarcaVendedor()
  }
  
  override fun marcaLink(pedidoLink: PedidoLink) {
    viewModel.marcaPedido(pedidoLink)
  }
  
  override fun uploadFile(inputStream: InputStream) {
    viewModel.uploadFile(inputStream)
  }
  
  override fun desmarcaPedido() {
    if(itensSelecionadoPendente().isEmpty()) showError("Nenhum pedido foi selecionado")
    viewModel.desmarcaPedido()
  }
  
  override fun marcaVendedor(pedido: PedidoLink) {
    val form = FormVendedor()
    val vendendor = SenhaVendendor(pedido.vendedor ?: "Não encontrado", "")
    form.binder.bean = vendendor
    showForm("Senha do vendedor", form) {
      val senha = form.binder.bean.senha ?: "#######"
      viewModel.marcaVendedor(pedido, senha)
    }
  }
  
  override fun itensSelecionadoPedido(): List<PedidoLink> {
    return gridPedido.selectedItems()
  }
  
  override fun itensSelecionadoLink(): List<PedidoLink> {
    return gridLink.selectedItems()
  }
  
  override fun itensSelecionadoPendente(): List<PedidoLink> {
    return gridPendente.selectedItems()
  }
  
  override fun updateGridPedido(itens: List<PedidoLink>) {
    gridPedido.updateGrid(itens)
  }
  
  override fun updateGridLink(itens: List<PedidoLink>) {
    gridLink.updateGrid(itens)
  }
  
  override fun updateGridPendente(itens: List<PedidoLink>) {
    gridPendente.updateGrid(itens)
  }
  
  override fun updateGridFinalizar(itens: List<PedidoLink>) {
    gridFinalizar.updateGrid(itens)
  }
  
  override fun updateGridFaturar(itens: List<PedidoLink>) {
    gridFaturar.updateGrid(itens)
  }
  
  override val filtroPedido: IFiltroPedido
    get() = gridPedido.filterBar as IFiltroPedido
  override val filtroLink: IFiltroLink
    get() = gridLink.filterBar as IFiltroLink
  override val filtroPendente: IFiltroPendente
    get() = gridPendente.filterBar as IFiltroPendente
  override val filtroFinalizar: IFiltroFinalizar
    get() = gridFinalizar.filterBar as IFiltroFinalizar
  override val filtroFaturar: IFiltroFaturar
    get() = gridFaturar.filterBar as IFiltroFaturar
  
  companion object {
    const val TAB_PEDIDO: String = "Pedido"
    const val TAB_LINK: String = "Link"
    const val TAB_PENDENTE: String = "Pendente"
    const val TAB_FINALIZADO: String = "Finalzar"
    const val TAB_FATURADO: String = "Faturar"
  }
}

class FormVendedor: FormLayout() {
  val binder = Binder<SenhaVendendor>(SenhaVendendor::class.java)
  
  init {
    textField("Nome") {
      isEnabled = false
      addThemeVariants(LUMO_SMALL)
      bind(binder).bind(SenhaVendendor::nome)
    }
  
    passwordField("Senha") {
      addThemeVariants(LUMO_SMALL)
      bind(binder).bind(SenhaVendendor::senha)
    }
  }
}

