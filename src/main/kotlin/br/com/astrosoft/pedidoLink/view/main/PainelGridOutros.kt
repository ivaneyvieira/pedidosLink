package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroOutros
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode.MULTI
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridOutros(view : IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(view, blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    //setSelectionMode(MULTI)
    colLoja()
    colnumPedido()
    colDataPedido()
    colHoraLink()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    colStatusTef()
    colWhatsapp()
    colVendedor()
    colEmpno()
    colCliente()
    colUsername()
  }
  
  override fun filterBar() = FilterBarOutros()
  
  inner class FilterBarOutros(): FilterBar(), IFiltroOutros {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    
    override fun FilterBar.contentBlock() {
      /*
      button("Remover") {
        icon = VaadinIcon.ERASER.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {view.desmarcaOutros()}
      }
       */
      edtPedido = edtPedido() {
        addValueChangeListener {blockUpdate()}
      }
      edtData = edtDataPedido() {
        addValueChangeListener {blockUpdate()}
      }
    }
    
    override fun numPedido(): Int = edtPedido.value ?: 0
    override fun data(): LocalDate? = edtData.value
  }
}

