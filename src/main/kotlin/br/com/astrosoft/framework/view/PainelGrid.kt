package br.com.astrosoft.framework.view

import com.github.mvysny.karibudsl.v10.grid
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.provider.ListDataProvider

abstract class PainelGrid<T>(): VerticalLayout() {
  private var grid: Grid<T>
  private val dataProvider = ListDataProvider<T>(mutableListOf())
  
  init {
    this.setSizeFull()
    isMargin = false
    isPadding = false
    
    grid = this.grid(dataProvider = dataProvider) {
      this.gridConfig()
    }
  }
  
  fun updateGrid(itens: List<T>) {
    grid.deselectAll()
    dataProvider.updateItens(itens)
  }
  
  protected abstract fun Grid<T>.gridConfig()
}

