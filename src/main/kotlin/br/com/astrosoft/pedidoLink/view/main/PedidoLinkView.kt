package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.tabGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.model.beans.UserSaci
import br.com.astrosoft.pedidoLink.view.layout.PedidoLinkLayout
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFaturado
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFinalizar
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroGerarLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroOutros
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPedido
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPendente
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import br.com.astrosoft.pedidoLink.viewmodel.PedidoLinkViewModel
import br.com.astrosoft.pedidoLink.viewmodel.SenhaUsuario
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
class PedidoLinkView: ViewLayout<PedidoLinkViewModel>(), IPedidoLinkView {
  private val gridPedido = PainelGridPedido(this) {viewModel.updateGridPedido()}
  private val gridLink = PainelGridLink(this) {viewModel.updateGridLink()}
  private val gridGerarLink = PainelGridGerarLink(this) {viewModel.updateGridGerarLink()}
  private val gridPendente = PainelGridPendente(this) {viewModel.updateGridPendente()}
  private val gridFinalizar = PainelGridFinalizado(this) {viewModel.updateGridFinalizar()}
  private val gridFaturado = PainelGridFaturado(this) {viewModel.updateGridFaturado()}
  private val gridOutros = PainelGridOutros(this) {viewModel.updateGridOutros()}
  override val viewModel: PedidoLinkViewModel = PedidoLinkViewModel(this)
  
  override fun isAccept() = true
  
  init {
    val user = AppConfig.userSaci as UserSaci
    tabSheet {
      setSizeFull()
      if(user.acl_pedido) tabGrid(TAB_PEDIDO, gridPedido)
      if(user.acl_link) tabGrid(TAB_GERAR_LINK, gridGerarLink)
      if(user.acl_link) tabGrid(TAB_LINK, gridLink)
      if(user.acl_pendente) tabGrid(TAB_PENDENTE, gridPendente)
      if(user.acl_finalizar) tabGrid(TAB_FINALIZAR, gridFinalizar)
      if(user.acl_faturado) tabGrid(TAB_FATURADO, gridFaturado)
      if(user.acl_outros) tabGrid(TAB_OUTROS, gridOutros)
    }
    when {
      user.acl_pedido    -> viewModel.updateGridPedido()
      user.acl_link      -> viewModel.updateGridGerarLink()
      user.acl_pendente  -> viewModel.updateGridPendente()
      user.acl_finalizar -> viewModel.updateGridFinalizar()
      user.acl_faturado  -> viewModel.updateGridFaturado()
      user.acl_outros    -> viewModel.updateGridOutros()
    }
  }
  
  override fun marcaUserLink(pedidoLink: PedidoLink) {
    val userSaci = AppConfig.userSaci as UserSaci
    val form = FormUsuario()
    val usuario = SenhaUsuario(userSaci.login, "")
    form.binder.bean = usuario
    showForm("Senha do Usuário", form) {
      val senha = form.binder.bean.senha ?: "#######"
      viewModel.marcaUserLink(pedidoLink, senha)
    }
  }
  
  override fun desmarcaUserLink() {
    viewModel.desmarcaUserLink()
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
  
  override fun marcaVendedor(pedidoLink: PedidoLink) {
    val form = FormVendedor()
    val vendendor = SenhaVendendor(pedidoLink.vendedor ?: "Não encontrado", "")
    form.binder.bean = vendendor
    showForm("Senha do vendedor", form) {
      val senha = form.binder.bean.senha ?: "#######"
      viewModel.marcaVendedor(pedidoLink, senha)
    }
  }
  
  override fun itensSelecionadoPedido(): List<PedidoLink> {
    return gridPedido.selectedItems()
  }
  
  override fun itensSelecionadoGerarLink(): List<PedidoLink> {
    return gridGerarLink.selectedItems()
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
  
  override fun updateGridGerarLink(itens: List<PedidoLink>) {
    gridGerarLink.updateGrid(itens)
  }
  
  override fun updateGridPendente(itens: List<PedidoLink>) {
    gridPendente.updateGrid(itens)
  }
  
  override fun updateGridFinalizar(itens: List<PedidoLink>) {
    gridFinalizar.updateGrid(itens)
  }
  
  override fun updateGridFaturado(itens: List<PedidoLink>) {
    gridFaturado.updateGrid(itens)
  }
  
  override fun updateGridOutros(itens: List<PedidoLink>) {
    gridOutros.updateGrid(itens)
  }
  
  override val filtroPedido: IFiltroPedido
    get() = gridPedido.filterBar as IFiltroPedido
  override val filtroLink: IFiltroLink
    get() = gridLink.filterBar as IFiltroLink
  override val filtroGerarLink: IFiltroGerarLink
    get() = gridGerarLink.filterBar as IFiltroGerarLink
  override val filtroPendente: IFiltroPendente
    get() = gridPendente.filterBar as IFiltroPendente
  override val filtroFinalizar: IFiltroFinalizar
    get() = gridFinalizar.filterBar as IFiltroFinalizar
  override val filtroFaturado: IFiltroFaturado
    get() = gridFaturado.filterBar as IFiltroFaturado
  override val filtroOutros: IFiltroOutros
    get() = gridOutros.filterBar as IFiltroOutros
  
  companion object {
    const val TAB_PEDIDO: String = "Pedido"
    const val TAB_GERAR_LINK: String = "Gerar Link"
    const val TAB_LINK: String = "Link"
    const val TAB_PENDENTE: String = "Pendente"
    const val TAB_FINALIZAR: String = "Finalzar"
    const val TAB_FATURADO: String = "Faturado"
    const val TAB_OUTROS: String = "Outros status"
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
      this.isAutofocus = true
    }
  }
}

class FormUsuario: FormLayout() {
  val binder = Binder<SenhaUsuario>(SenhaUsuario::class.java)
  
  init {
    textField("Nome") {
      isEnabled = false
      addThemeVariants(LUMO_SMALL)
      bind(binder).bind(SenhaUsuario::nome)
    }
    
    passwordField("Senha") {
      addThemeVariants(LUMO_SMALL)
      bind(binder).bind(SenhaUsuario::senha)
      this.isAutofocus = true
    }
  }
}

