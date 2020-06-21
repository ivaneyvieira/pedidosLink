package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnLocalTime
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.datePicker
import com.github.mvysny.karibudsl.v10.integerField
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.value.ValueChangeMode

fun Grid<PedidoLink>.colLoja() = addColumnInt(PedidoLink::loja) {
  setHeader("Lj")
  width = "10%"
}

fun Grid<PedidoLink>.colnumPedido() = addColumnInt(PedidoLink::numPedido) {
  setHeader("Pedido")
  width = "10%"
}

fun Grid<PedidoLink>.colDataPedido() = addColumnLocalDate(PedidoLink::dataPedido) {
  setHeader("Data")
  width = "10%"
}

fun Grid<PedidoLink>.colHoraPedido() = addColumnLocalTime(PedidoLink::horaPedido) {
  setHeader("Hora")
  width = "10%"
}

fun Grid<PedidoLink>.colMetodo() = addColumnInt(PedidoLink::metodo) {
  setHeader("Método")
  width = "10%"
}

fun Grid<PedidoLink>.colNotaFiscal() = addColumnString(PedidoLink::notaFiscal) {
  setHeader("NF")
  width = "10%"
}

fun Grid<PedidoLink>.colDataNota() = addColumnLocalDate(PedidoLink::dataNota) {
  setHeader("Data NF")
  width = "10%"
}

fun Grid<PedidoLink>.colHoraNota() = addColumnLocalTime(PedidoLink::horaNota) {
  setHeader("Hora NF")
  width = "10%"
}

fun Grid<PedidoLink>.colDataLink() = addColumnLocalDate(PedidoLink::dataLink) {
  setHeader("Data Link")
  width = "10%"
}

fun Grid<PedidoLink>.colHoraLink() = addColumnLocalTime(PedidoLink::horaLink) {
  setHeader("Hora Link")
  width = "10%"
}

fun Grid<PedidoLink>.colUsername() = addColumnString(PedidoLink::username) {
  setHeader("Usuário")
  width = "10%"
}

fun Grid<PedidoLink>.colObs() = addColumnString(PedidoLink::obs) {
  setHeader("Obs")
  width = "10%"
}

//Campos de filtro
fun (@VaadinDsl HasComponents).edtPedido(block: (@VaadinDsl IntegerField).() -> Unit = {}) = integerField("Pedido") {
  this.valueChangeMode = ValueChangeMode.TIMEOUT
  this.isAutofocus = true
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  block()
}

fun (@VaadinDsl HasComponents).edtDataPedido(block: (@VaadinDsl DatePicker).() -> Unit = {}) = datePicker("Data") {
  localePtBr()
  isClearButtonVisible = true
  element.setAttribute("theme", "small")
  block()
}

