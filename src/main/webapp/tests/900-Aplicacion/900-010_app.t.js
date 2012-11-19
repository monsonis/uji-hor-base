StartTest(function(t) {
	t.waitForCQ('splitbutton', function(splitbutton) {
			
		var steps = [
		             { action : 'click', target: '>>splitbutton' }, 
		             { action : 'click', target :'>>menuitem[action="curso-academico"]' }, // el item 'Dates del curs acadèmic'
		             {  waitFor : 'componentQuery', args : 'grid' },
		             function (next) {
		            	 var grid = Ext.ComponentQuery.query('grid')[0];		            	 
		            	 t.ok(grid.getView().getNodes().length > 0, 'Muestra alguna fila');
		     			 t.is(grid.store.getCount(), grid.getView().getNodes().length,
		     					'Muestra todo el contenido del store');
		             }
		         ];
		
		t.chain(steps);
		
	});

});