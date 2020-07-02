package br.com.astrosoft.pedidoLink.view.main

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnLocalTime
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.pedidoLink.model.beans.PedidoLink
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.addColumnFor
import com.github.mvysny.karibudsl.v10.datePicker
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.value.ValueChangeMode

fun Grid<PedidoLink>.colLoja() = addColumnInt(PedidoLink::loja) {
  setHeader("Lj")
  width = "4em"
}

fun Grid<PedidoLink>.colnumPedido() = addColumnInt(PedidoLink::numPedido) {
  setHeader("Pedido")
  width = "7em"
}

fun Grid<PedidoLink>.colDataPedido() = addColumnLocalDate(PedidoLink::dataPedido) {
  setHeader("Data")
}

fun Grid<PedidoLink>.colHoraPedido() = addColumnLocalTime(PedidoLink::horaPedido) {
  setHeader("Hora")
}

fun Grid<PedidoLink>.colMetodo() = addColumnInt(PedidoLink::metodo) {
  setHeader("Mét")
  width = "5em"
}

fun Grid<PedidoLink>.colNotaFiscal() = addColumnString(PedidoLink::notaFiscal) {
  setHeader("NF")
}

fun Grid<PedidoLink>.colDataNota() = addColumnLocalDate(PedidoLink::dataNota) {
  setHeader("Data NF")
}

fun Grid<PedidoLink>.colHoraNota() = addColumnLocalTime(PedidoLink::horaNota) {
  setHeader("Hr NF")
}

fun Grid<PedidoLink>.colDataLink() = addColumnLocalDate(PedidoLink::dataLink) {
  setHeader("Data Link")
}

fun Grid<PedidoLink>.colHoraLink() = addColumnLocalTime(PedidoLink::horaLink) {
  setHeader("Hr Link")
}

fun Grid<PedidoLink>.colUsername() = addColumnString(PedidoLink::username) {
  setHeader("Usuário")
}

fun Grid<PedidoLink>.colObs() = addColumnString(PedidoLink::obs) {
  setHeader("Obs")
}

fun Grid<PedidoLink>.colNota() = addColumnString(PedidoLink::nota) {
  setHeader("LJ")
}

fun Grid<PedidoLink>.statusPedido() = addColumnFor(PedidoLink::statusPedido) {
  setHeader("Status")
}

fun Grid<PedidoLink>.colValorFrete() = addColumnDouble(PedidoLink::valorFrete) {
  setHeader("Frete")
}

fun Grid<PedidoLink>.colTotal() = addColumnDouble(PedidoLink::total) {
  setHeader("Total")
}

fun Grid<PedidoLink>.colValorLink() = addColumnDouble(PedidoLink::valorLink) {
  setHeader("Valor TEF")
}

fun Grid<PedidoLink>.colCartao() = addColumnString(PedidoLink::cartao) {
  setHeader("Cartão")
}

fun Grid<PedidoLink>.colWhatsapp() = addColumnString(PedidoLink::whatsapp) {
  setHeader("Whatsapp")
}

fun Grid<PedidoLink>.colCliente() = addColumnString(PedidoLink::cliente) {
  setHeader("Cliente")
}

fun Grid<PedidoLink>.colEmpno() = addColumnInt(PedidoLink::empno) {
  setHeader("Nº Vend")
}

fun Grid<PedidoLink>.colVendedor() = addColumnString(PedidoLink::vendedor) {
  setHeader("Vendedor")
}

fun Grid<PedidoLink>.colParcelas() = addColumnInt(PedidoLink::parcelas) {
  setHeader("Parc")
  width = "4em"
}

fun Grid<PedidoLink>.colAutorizadora() = addColumnString(PedidoLink::autorizadora) {
  setHeader("Autoriz")
}

fun Grid<PedidoLink>.colAutorizacao() = addColumnString(PedidoLink::autorizacao) {
  setHeader("Autorização")
}

fun Grid<PedidoLink>.colNsuHost() = addColumnString(PedidoLink::nsuHost) {
  setHeader("Nsu Host")
}

fun Grid<PedidoLink>.colDataTef() = addColumnLocalDate(PedidoLink::dataTef) {
  setHeader("Data Tef")
}

//Campos de filtro
fun (@VaadinDsl HasComponents).edtPedido(block: (@VaadinDsl IntegerField).() -> Unit = {}) = integerField("Pedido") {
  this.valueChangeMode = ValueChangeMode.TIMEOUT
  this.isAutofocus = true
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  block()
}

fun (@VaadinDsl HasComponents).edtVendedor(block: (@VaadinDsl TextField).() -> Unit = {}) = textField("Vendedor") {
  this.valueChangeMode = ValueChangeMode.TIMEOUT
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  block()
}

fun (@VaadinDsl HasComponents).edtDataPedido(block: (@VaadinDsl DatePicker).() -> Unit = {}) = datePicker("Data") {
  localePtBr()
  isClearButtonVisible = true
  element.setAttribute("theme", "small")
  block()
}

