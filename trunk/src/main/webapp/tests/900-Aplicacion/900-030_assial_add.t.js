StartTest(function(t) {
    t.diag("Asignación aulas");

	
	t.waitForCQ('splitbutton', function(splitbutton) {
		
		var centro_seleccionado = 2;
		var estudio_seleccionado = 1;
		var edificio_seleccionado = 1;
		var tipo_seleccionado = 0;
		var aula_seleccionado = 0;		
		
		var tree_aulas;
   	 	var grid_aulasasign;
   	 	var selected_aula_id;
		
			
		var steps = [
		             { action : 'click', target: '>>splitbutton' }, 
		             { action : 'click', target :'>>menuitem[action="asignacion-aulas]' },
		             { waitFor : 'componentQuery', args : '>>combobox[alias=widget.comboCentros]'},
		             function (next) {
		            	tree_aulas = Ext.ComponentQuery.query('>>panel[alias=widget.treePanelAulas]')[0];		
		            	 grid_aulasasign = Ext.ComponentQuery.query('>>panel[alias=widget.gridAulas]')[0];
		            	
		            	 t.ok(tree_aulas.disabled, 'El árbol de aulas disponibles está deshabilitado');
		            	 t.ok(grid_aulasasign.disabled, 'El aula de aulas asignadas está deshabilitado');
		            	 
		            	 next();		            	
		             },
		             { action : 'click', target :'>>combobox[alias=widget.comboCentros]' }, 		             
		             function (next) {
		            	 t.ok(tree_aulas.disabled, 'El árbol de aulas disponibles sigue deshabilitado');
		            	 t.ok(grid_aulasasign.disabled, 'El grid de aulas asignadas sigue deshabilitado');
		            	 
		            	 
		            	 var combo_centros = Ext.ComponentQuery.query('>>combobox[alias=widget.comboCentros]')[0];			            	 
		            	 t.click(combo_centros.getPicker().getNode(centro_seleccionado),next);		            	
		             },
		             { action : 'click', target :'>>combobox[alias=widget.comboEstudios]' }, 		             
		             function (next) {		            	 
		            	 var combo_estudio = Ext.ComponentQuery.query('>>combobox[alias=widget.comboEstudios]')[0];
		            	 t.click(combo_estudio.getPicker().getNode(estudio_seleccionado), next);
		             },
		             function (next){
		            	 t.ok(!tree_aulas.disabled, 'El árbol de aulas disponibles NO está deshabilitado');
		            	 t.ok(tree_aulas.getRootNode().hasChildNodes(),'El árbol tiene contenido');
		            			
		            	 
		            	 var node_edificio = tree_aulas.getRootNode().getChildAt(edificio_seleccionado);		            	 
		            	 node_edificio.expand();
		            	 t.ok(node_edificio.hasChildNodes(),'La carpeta edificio tiene contenido');
		            	 
		            	 var tipo_area = node_edificio.getChildAt(tipo_seleccionado);
		            	 tipo_area.expand();
		            	 t.ok(tipo_area.hasChildNodes(),'La carpeta tipo tiene contenido');
		            	 
		            	 var node_planta = tipo_area.getChildAt(0);
		            	 node_planta.expand();
		            	 t.ok(node_planta.hasChildNodes(),'La carpeta planta tiene contenido');
		            	 
		            	 var node_aula = node_planta.getChildAt(aula_seleccionado);
		            	 selected_aula_id = node_aula.get("id");
		            	 console.log(selected_aula_id);
		            	 tree_aulas.getSelectionModel().select(node_aula);
		            	 
		            	 next();

		             },
		             { action : 'click', target :'>>panel[alias=widget.treePanelAulas] button' }, 
		             // Esperar a que se recarge
		             function (next){	            	 
		            	 var encontrado = grid_aulasasign.getStore().find('aulaId',selected_aula_id);
		            			            	 
		            	 var has_been_inserted = encontrado>=0;
		            	 
		            	 t.ok(has_been_inserted,'El aula ha sido asignada');
		            	 
		            	 next();
		             }, function (next){	            	 
		            	 //  clean
		            	 var indice_nuevo = grid_aulasasign.getStore().find('aulaId',selected_aula_id);
		            	  grid_aulasasign.getStore().removeAt(indice_nuevo);
		            	  grid_aulasasign.getStore().sync();
		             }

		              			
		         ];
		
		t.chain(steps);
		
	});

});		             