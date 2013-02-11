StartTest(function(t) {
    t.diag("Asignación aulas");

	
	t.waitForCQ('treepanel[alias=widget.navigationtree]', function(navigationtreelist) {
		
		var centro_seleccionado = 2;
		var estudio_seleccionado = 1;
   	 	var grid_aulasasign;
   	 	var selected_aula_id;
   	 	var combo_estudio;
   	 	var estudio_seleccionado;
   	 	var semestre_seleccionado;
   	 	var aula_a_insertar = 4909;
   	 	var aulaAsignada;
		
			
		var steps = [{ action : 'click', target :function() {
						var navigationtree = navigationtreelist[0];
						var treeview = navigationtree.getView();
						var root = navigationtree.getRootNode();
						var menuItem = root.findChild('id','HOR.view.aulas.PanelAulas');
						return treeview.getNode(menuItem);
						} 
					}, 	
		             { waitFor : 'componentQuery', args : '>>combobox[alias=widget.comboCentros]'},		           
		             { action : 'click', target :'>>combobox[alias=widget.comboCentros]' }, 		             
		             function (next) {	
		            	 grid_aulasasign = Ext.ComponentQuery.query('>>panel[alias=widget.gridAulas]')[0];

		            	 var combo_centros = Ext.ComponentQuery.query('>>combobox[alias=widget.comboCentros]')[0];			            	 
		            	 t.click(combo_centros.getPicker().getNode(centro_seleccionado),next);		            	
		             },
		             { action : 'click', target :'>>combobox[alias=widget.comboEstudios]' }, 		             
		             function (next) {		            	 
		            	 combo_estudio = Ext.ComponentQuery.query('>>combobox[alias=widget.comboEstudios]')[0];
		            	 t.click(combo_estudio.getPicker().getNode(estudio_seleccionado), next);
		             },
		             function (next){
		            	 // Añadimos una asignación que después borraremos
		            	 var assign_store = grid_aulasasign.getStore();
		            	 
		            	 var combo_semestre = Ext.ComponentQuery.query('>>combobox[alias=widget.comboEstudios]')[0];
		            	 estudio_seleccionado = combo_estudio.getValue(); 
		            	 semestre_seleccionado = combo_semestre.getValue();

		            	 aulaAsignada = Ext.ModelManager.create(
		            	            {
		            	                nombre : "Asignacion de Test",
		            	                estudioId : estudio_seleccionado,
		            	                aulaId : aula_a_insertar,
		            	                semestreId : semestre_seleccionado,
		            	            }, "HOR.model.AulaPlanificacion");
		            	 
		            	 assign_store.add(aulaAsignada);
		            	 assign_store.sync();
		            	 
		            	 grid_aulasasign.getSelectionModel().select(aulaAsignada);
		            	 
		            	 var encontrado = grid_aulasasign.getStore().find('aulaId',aula_a_insertar);
		            	 var has_been_added = encontrado >= 0;
		            	 t.ok(has_been_added,'El aula está');

		            	 
		            	 next();

		             },		             
		             { action : 'click', target :'>>panel[alias=widget.gridAulas] button' },  
		             function (next){	            	 
		            	 var encontrado = grid_aulasasign.getStore().find('aulaId',aula_a_insertar);
		            			            	 
		            	 var has_been_removed = encontrado==-1;
		            	 
		            	 t.ok(has_been_removed,'El aula ha sido eliminada');
		            	 
		            	 next();
		             }

		              			
		         ];
		
		t.chain(steps);
		
	});

});		             