package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPedido
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridPedido(val event: IEventGridPedido, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    addColumnButton(VaadinIcon.ARROW_FORWARD, event::marcaVendedor)
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
  
  inner class FilterBarPedido: FilterBar(), IFiltroPedido {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    
    override fun FilterBar.contentBlock() {
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

interface IEventGridPedido {
  fun marcaVendedor(pedidoLink: PedidoLink)
}