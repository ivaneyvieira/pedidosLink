package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroFinalizar
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridFinalizado(view: IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(view, blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    colLoja()
    colnumPedido()
    colDataPedido()
    colValorFrete()
    colTotal()
    colValorLink()
    colMetodo()
    colStatusTef()
    colUserLink()
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

