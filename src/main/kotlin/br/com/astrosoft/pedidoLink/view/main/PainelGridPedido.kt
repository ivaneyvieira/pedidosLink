package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.framework.view.addColumnButtonClipBoard
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPedido
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant.LUMO_COLUMN_BORDERS
import com.vaadin.flow.component.grid.GridVariant.LUMO_COMPACT
import com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

class PainelGridPedido(view : IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(view, blockUpdate) {

  override fun Grid<PedidoLink>.gridConfig() {
    addColumnButtonClipBoard(VaadinIcon.ARROW_FORWARD, view::marcaVendedor, {noteClipBoard})
    colLoja()
    colnumPedido()
    colDataPedido()
    //colHoraPedido()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    colWhatsapp()
    colVendedor()
    colCliente()
    colUsername()
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

