package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFinalizar
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant.LUMO_COLUMN_BORDERS
import com.vaadin.flow.component.grid.GridVariant.LUMO_COMPACT
import com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridFinalizar(blockUpdate: () -> Unit): PainelGrid<PedidoLink>(blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    addThemeVariants(LUMO_COMPACT, LUMO_COLUMN_BORDERS, LUMO_ROW_STRIPES)
    colLoja()
    colnumPedido()
    colDataPedido()
    colHoraPedido()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    colAutorizadora()
    colParcelas()
    colAutorizacao()
    colNsuHost()
    colDataTef()
    colVendedor()
  }
  
  override fun filterBar() = FilterBarFinalizado()
  
  inner class FilterBarFinalizado: FilterBar(), IFiltroFinalizar {
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
    override fun data(): LocalDate? = edtData.value
  }
}

