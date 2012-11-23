// also supports: startTest(function(t) {
StartTest(function(t) {
	
	t.requireOk([ 'HOR.model.SemestreDetalle',
			'HOR.store.StoreSemestreDetalles',
			'HOR.view.semestres.PanelSemestres'
	// 'widget.GridSemestresDetalles'
	], function() {
		var grid = Ext.create('HOR.view.semestres.GridSemestres', {
			renderTo : Ext.getBody(),
			store : new HOR.store.StoreSemestreDetalles(),
			width : 600
		});

		t.waitForRowsVisible(grid, function() {
			t.ok(grid.getView().getNodes().length > 0, 'Muestra alguna fila');
			t.is(grid.store.getCount(), grid.getView().getNodes().length,
					'Muestra todo el contenido del store');

		});
	});
	
	

});