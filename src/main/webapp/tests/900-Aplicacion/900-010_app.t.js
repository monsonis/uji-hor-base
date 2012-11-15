StartTest(function(t) {
	t.waitForCQ('splitbutton', function(splitbutton) {
		
		// var down_arrow = document.getElementById('splitbutton-1025-btnWrap');
		
		var steps = [
		             { action : 'click', target :[90,90] }, // no sé cómo seleccionar la flechita del splitbutton
		             { action : 'click', target :'#menuitem-1028' }, // el item 'Dates del curs acadèmic'
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