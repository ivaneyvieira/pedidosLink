package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.framework.view.addColumnButtonClipBoard
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroLink
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import java.time.LocalDate

class PainelGridLink(view: IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(view, blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    addColumnButtonClipBoard(VaadinIcon.ARROW_FORWARD, view::marcaLink, {noteClipBoard})
    colLoja()
    colnumPedido()
    colDataPedido()
    //colHoraPedido()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    statusPedido()
    colWhatsapp()
    colEmpno()
    colVendedor()
    colCliente()
    colUsername()
  }
  
  override fun filterBar() = FilterBarPedido()
  
  inner class FilterBarPedido: FilterBar(), IFiltroLink {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    lateinit var edtVendedor: TextField
    
    override fun FilterBar.contentBlock() {
      button("Desmarca Link") {
        icon = VaadinIcon.CHECK_CIRCLE_O.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {view.desmarcaPedidoLink()}
      }
      edtPedido = edtPedido() {
        addValueChangeListener {blockUpdate()}
      }
      edtVendedor = edtVendedor() {
        addValueChangeListener {blockUpdate()}
      }
      edtData = edtDataPedido() {
        addValueChangeListener {blockUpdate()}
      }
    }
    
    override fun numPedido(): Int = edtPedido.value ?: 0
    override fun vendedor(): String = edtVendedor.value ?: ""
    override fun data(): LocalDate? = edtData.value
  }
}
