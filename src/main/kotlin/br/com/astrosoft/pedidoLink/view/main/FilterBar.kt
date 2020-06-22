package br.com.astrosoft.pedidoLink.view.main

import com.github.mvysny.karibudsl.v10.content
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

abstract class FilterBar: HorizontalLayout() {
  init {
    isMargin = false
    isPadding = false
    isSpacing = true
    content { align(left, baseline) }
    width = "100%"
    addComponents()
  }
  
  private fun addComponents() {
    contentBlock()
  }
  
  protected abstract fun FilterBar.contentBlock()
}


