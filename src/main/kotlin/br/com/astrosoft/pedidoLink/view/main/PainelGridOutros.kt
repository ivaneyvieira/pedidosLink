package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroOutros
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridOutros(view : IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(view, blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    colLoja()
    colnumPedido()
    colDataPedido()
    colHoraPedido()
    colMetodo()
    statusPedido()
    colNotaFiscal()
    colDataNota()
    colHoraNota()
    colDataLink()
    colHoraLink()
    colUsername()
    colObs()
  }
  
  override fun filterBar() = FilterBarOutros()
  
  inner class FilterBarOutros(): FilterBar(), IFiltroOutros {
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
