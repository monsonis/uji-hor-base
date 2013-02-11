StartTest(function(t) {

	t.diag("Dates curs academic carga ok");

	t.waitForCQ('treepanel[alias=widget.navigationtree]', function(navigationtreelist) {

		var steps = [{ action : 'click', target :function() {
					var navigationtree = navigationtreelist[0];
					var treeview = navigationtree.getView();
					var root = navigationtree.getRootNode();
					var menuItem = root.findChild('id','HOR.view.semestres.PanelSemestres');
					return treeview.getNode(menuItem);
					} 
				}, 	
				{
					waitFor : 'componentQuery',
					args : 'grid'
				},
				function(next) {
					var grid = Ext.ComponentQuery.query('grid')[0];
					t.ok(grid.getView().getNodes().length > 0,
							'Muestra alguna fila');
					t.is(grid.store.getCount(),
							grid.getView().getNodes().length,
							'Muestra todo el contenido del store');
				} ];

		t.chain(steps);

	});

});