package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroGeral
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import org.vaadin.olli.ClipboardHelper
import java.time.LocalDate

class PainelGridGeral(val marcaPedido: (PedidoLink) -> Unit, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    //setSelectionMode(SelectionMode.MULTI)
    addComponentColumn {pedido->
      val button = Button().apply {
        icon = VaadinIcon.CHECK.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {marcaPedido(pedido)}
      }
      ClipboardHelper("${pedido.nota}: ${pedido.numPedido}", button)
    }
    colLoja()
    colnumPedido()
    colDataPedido()
    colHoraPedido()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    colWhatsapp()
    colUsername()
    colCliente()
    colVendedor()
    //colNotaFiscal()
    //colDataNota()
    //colHoraNota()
    //colDataLink()
    //colHoraLink()
    //    colObs()
  }
  
  override fun filterBar() = FilterBarGeral()
  
  inner class FilterBarGeral: FilterBar(), IFiltroGeral {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    
    override fun FilterBar.contentBlock() {
      // button("Marca Link") {
      //   icon = VaadinIcon.CHECK.create()
      //   addThemeVariants(LUMO_SMALL)
      //   onLeftClick {marcaPedido()}
      //  }
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
