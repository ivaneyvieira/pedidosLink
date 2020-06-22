package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPendente
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridPendente(val desmarcaPedido: () -> Unit, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    setSelectionMode(SelectionMode.MULTI)
    colLoja()
    colnumPedido()
    colDataPedido()
    colHoraPedido()
    colMetodo()
    //colNotaFiscal()
    //colDataNota()
    //colHoraNota()
    colDataLink()
    colHoraLink()
    colUsername()
    colObs()
  }
  
  override fun filterBar() = FilterBarPendente()
  
  inner class FilterBarPendente: FilterBar(), IFiltroPendente {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    
    override fun FilterBar.contentBlock() {
      button("Desmarca Link") {
        icon = VaadinIcon.CHECK.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {desmarcaPedido()}
      }
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

