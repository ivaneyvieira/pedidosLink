package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import br.com.astrosoft.pedidoLink.viewmodel.IFiltroPendente
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.receivers.MemoryBuffer
import org.vaadin.olli.ClipboardHelper
import java.io.InputStream
import java.time.LocalDate

class PainelGridPendente(val desmarcaPedido: () -> Unit,
                         val uploadFile: (InputStream) -> Unit,
                         blockUpdate: () -> Unit): PainelGrid<PedidoLink>(blockUpdate) {
  override fun Grid<PedidoLink>.gridConfig() {
    setSelectionMode(SelectionMode.MULTI)
    addComponentColumn {pedido->
      val button = Button().apply {
        icon = VaadinIcon.COPY_O.create()
        addThemeVariants(LUMO_SMALL)
      }
      ClipboardHelper("${pedido.nota}: ${pedido.numPedido}", button)
    }.apply {
      width = "20px"
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
  }
  
  override fun filterBar() = FilterBarPendente()
  
  inner class FilterBarPendente: FilterBar(), IFiltroPendente {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    
    override fun FilterBar.contentBlock() {
      button("Desmarca Link") {
        icon = VaadinIcon.CHECK_CIRCLE_O.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {desmarcaPedido()}
      }
      edtPedido = edtPedido() {
        addValueChangeListener {blockUpdate()}
      }
      edtData = edtDataPedido() {
        addValueChangeListener {blockUpdate()}
      }
      val buffer = MemoryBuffer()
      val upload = Upload(buffer)
      add(upload)
      //upload.setAcceptedFileTypes("text/csv")
      //upload.maxFiles = 1
      upload.addSucceededListener {event ->
        uploadFile(buffer.inputStream)
      }
    }
    
    override fun numPedido(): Int = edtPedido.value ?: 0
    override fun data(): LocalDate? = edtData.value
  }
}

