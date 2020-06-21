package br.com.astrosoft.pedidoLink.view.main

import com.vaadin.flow.component.orderedlayout.HorizontalLayout

abstract class FilterBar(): HorizontalLayout() {
  init {
    isMargin = false
    isPadding = false
    width = "100%"
    addComponents()
  }
  
  private fun addComponents() {
    contentBlock()
  }
  
  protected abstract fun FilterBar.contentBlock()
}


