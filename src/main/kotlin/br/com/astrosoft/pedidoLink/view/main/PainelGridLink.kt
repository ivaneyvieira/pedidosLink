package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroLink
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant.LUMO_COLUMN_BORDERS
import com.vaadin.flow.component.grid.GridVariant.LUMO_COMPACT
import com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import org.vaadin.olli.ClipboardHelper
import java.time.LocalDate

class PainelGridLink(val event: IEventGridLink, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    addThemeVariants(LUMO_COMPACT, LUMO_COLUMN_BORDERS, LUMO_ROW_STRIPES)
    addColumnButton(VaadinIcon.ARROW_FORWARD, event::marcaLink, {
      ClipboardHelper(it.nota, this)
    })
    colLoja()
    colnumPedido()
    colDataPedido()
    //colHoraPedido()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    colWhatsapp()
    colUsername()
    colCliente()
    colVendedor()
  }
  
  override fun filterBar() = FilterBarPedido()
  
  inner class FilterBarPedido: FilterBar(), IFiltroLink {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    
    override fun FilterBar.contentBlock() {
      button("Desmarca Link") {
        icon = VaadinIcon.CHECK_CIRCLE_O.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {event.desmarcaPedidoLink()}
      }
      edtPedido = edtPedido() {
        addValueChangeListener {blockUpdate()}
      }
      edtData = edtDataPedido() {
        addValueChangeListener {blockUpdate()}
      }
    }
    
    override fun numPedido(): Int = edtPedido.value ?: 0
    override fun data(): LocalDate? {
      return edtData.value
    }
  }
}

interface IEventGridLink {
  fun marcaLink(pedido: PedidoLink)
  fun desmarcaPedidoLink()
}