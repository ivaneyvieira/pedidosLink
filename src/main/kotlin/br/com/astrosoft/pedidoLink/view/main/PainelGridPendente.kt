package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.framework.view.addColumnButtonClipBoard
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPendente
import br.com.astrosoft.pedidoLink.viewmodel.IPedidoLinkView
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode.MULTI
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.receivers.MemoryBuffer
import java.time.LocalDate


class PainelGridPendente(view : IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoLink>(view, blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    //setSelectionMode(MULTI)
    addColumnButtonClipBoard(VaadinIcon.CLIPBOARD, textToClipBoard = {noteClipBoard})
    colLoja()
    colnumPedido()
    colDataPedido()
    colHoraLink()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    colStatusTef()
    colUserLink()
    colWhatsapp()
    colVendedor()
    colEmpno()
    colCliente()
    colUsername()
  }
  
  override fun filterBar() = FilterBarPendente()
  
  inner class FilterBarPendente: FilterBar(), IFiltroPendente {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    lateinit var edtVendedor: TextField
  
    override fun FilterBar.contentBlock() {
      button("Desmarca Link") {
        icon = VaadinIcon.CHECK_CIRCLE_O.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {view.desmarcaPedido()}
      }
      edtPedido = edtPedido() {
        addValueChangeListener {blockUpdate()}
      }
      edtVendedor = edtVendedor() {
        addValueChangeListener {blockUpdate()}
      }
      edtData = edtDataPedido() {
        addValueChangeListener {blockUpdate()}
      }
      val buffer = MemoryBuffer()
      val upload = Upload(buffer)
      val uploadButton = Button("Arquivo TEF")
      upload.uploadButton = uploadButton
      upload.isDropAllowed = false
      add(upload)
      upload.addSucceededListener {
        view.uploadFile(buffer.inputStream)
      }
    }
    
    override fun numPedido(): Int = edtPedido.value ?: 0
    override fun vendedor(): String = edtVendedor.value ?: ""
    override fun data(): LocalDate? = edtData.value
  }
}

