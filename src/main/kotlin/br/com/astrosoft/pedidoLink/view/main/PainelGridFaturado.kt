package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFaturado
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant.LUMO_COLUMN_BORDERS
import com.vaadin.flow.component.grid.GridVariant.LUMO_COMPACT
import com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridFaturado(view : IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(view, blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    colLoja()
    colnumPedido()
    colDataPedido()
    colHoraPedido()
    colMetodo()
    colStatusTef()
    colNotaFiscal()
    colDataNota()
    colHoraNota()
    colDataLink()
    colHoraLink()
    colUsername()
    colObs()
  }
  
  override fun filterBar() = FilterBarFaturar()
  
  inner class FilterBarFaturar(): FilterBar(), IFiltroFaturado {
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

